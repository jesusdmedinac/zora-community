package com.jesusdmedinac.zora.feature07

import com.jesusdmedinac.zora.data.local.InMemoryMechanicalPrescriptionRepository
import com.jesusdmedinac.zora.data.remote.MockCloudPrescriptionGateway
import com.jesusdmedinac.zora.domain.model.prescription.PrescriptionSyncState
import com.jesusdmedinac.zora.domain.model.prescription.VehicleImpactQuadrant
import com.jesusdmedinac.zora.domain.model.vehicle.ActiveVehicle
import com.jesusdmedinac.zora.domain.model.vehicle.VinPrivacySanitizer
import com.jesusdmedinac.zora.domain.usecase.RequestMechanicalPrescriptionUseCase
import com.jesusdmedinac.zora.domain.usecase.UnlockResult
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class MechanicalPrescriptionBddTest {

    private lateinit var repository: InMemoryMechanicalPrescriptionRepository
    private lateinit var gateway: MockCloudPrescriptionGateway
    private lateinit var useCase: RequestMechanicalPrescriptionUseCase

    private val activeVehicle = ActiveVehicle(
        vehicleId = "veh_01",
        make = "Honda",
        model = "Civic",
        year = 2018,
        engine = "2.0L",
        fullVin = "2HGFC2F59JH123456",
        technicalVinPrefix = "2HGFC2F59JH",
        odometerKm = 114500L
    )

    @BeforeTest
    fun setup() {
        repository = InMemoryMechanicalPrescriptionRepository()
        gateway = MockCloudPrescriptionGateway()
        useCase = RequestMechanicalPrescriptionUseCase(repository, gateway)
    }

    /**
     * SCENARIO 1: On-device triage ($0 server CAC) without network traffic.
     */
    @Test
    fun testScenario1_OnDeviceTriageRecordsLocalWithoutNetworkTraffic() = runTest {
        // Given active vehicle and corridor
        val symptomText = "Heavy vibration on acceleration between 40 and 60 km/h with clicking noise during right turns"

        // When driver logs symptom from bottom capsule
        val result = useCase.recordLocalTriage(
            vehicleId = activeVehicle.vehicleId,
            fullVin = activeVehicle.fullVin,
            symptomDescription = symptomText,
            corridor = "Tijuana/Otay",
            condition = "OEM_USED",
            customSymptomId = "sym_001"
        )

        // Then SQLDelight persists record in LOCAL_TRIAGE_ONLY
        assertTrue(result.isSuccess, "Local triage recording should succeed")
        val savedEntity = repository.selectById("sym_001")
        assertNotNull(savedEntity)
        assertEquals(PrescriptionSyncState.LOCAL_TRIAGE_ONLY, savedEntity.syncState)
        assertEquals("2HGFC2F59JH", savedEntity.vinTechnicalPrefix)

        // And Ktor network client confirms exactly 0 requests dispatched
        assertEquals(0, gateway.getRequestCount(), "Zero network requests must be dispatched during local triage")

        // And preliminary hypothesis is generated
        assertEquals(
            "Probable CV Axle failure or Right Transmission Mount wear",
            savedEntity.localPreliminaryHypothesis
        )
    }

    /**
     * SCENARIO 2: Ethical Value Gate, VIN sanitization, and Mechanical Prescription with Copart/IAAI exclusions.
     */
    @Test
    fun testScenario2_UnlockPrescriptionSanitizesVinAndExcludesDamagedQuadrants() = runTest {
        // Given symptom sym_991 exists in LOCAL_TRIAGE_ONLY
        useCase.recordLocalTriage(
            vehicleId = activeVehicle.vehicleId,
            fullVin = activeVehicle.fullVin,
            symptomDescription = "Clicking noise on right turns",
            customSymptomId = "sym_991"
        )
        val initialRecord = repository.selectById("sym_991")
        assertEquals(PrescriptionSyncState.LOCAL_TRIAGE_ONLY, initialRecord?.syncState)

        // When driver authorizes Ethical Value Gate
        val unlockResult = useCase.unlockMechanicalPrescription(
            symptomId = "sym_991",
            fullVin = activeVehicle.fullVin
        )

        // Then verify only the 11-char technical prefix was transmitted (zero serial leakage)
        val submittedPayload = gateway.getLastSubmittedPayload()
        assertNotNull(submittedPayload)
        assertEquals("2HGFC2F59JH", submittedPayload.technicalVinPrefix)
        assertFalse(
            submittedPayload.technicalVinPrefix.contains("123456"),
            "Private serial number digits must NEVER leak into the network payload"
        )
        assertTrue(
            VinPrivacySanitizer.isSanitizedPrefix(submittedPayload.technicalVinPrefix),
            "Technical prefix must comply with 11-char Zero-Trust rule"
        )

        // And SQLDelight updates state to SYNCED_CLOUD_VERIFIED
        assertTrue(unlockResult is UnlockResult.Success)
        val verifiedRecord = repository.selectById("sym_991")
        assertEquals(PrescriptionSyncState.SYNCED_CLOUD_VERIFIED, verifiedRecord?.syncState)
        assertEquals("44305-TBA-A01", verifiedRecord?.oemPartNumber)

        // And prescription card details are verified
        val prescription = unlockResult.prescription
        assertEquals("44305-TBA-A01", prescription.oemPart.oemNumber)
        assertEquals("Front Right CV Axle Assembly", prescription.oemPart.partName)
        assertEquals("$1,200 - $1,800", prescription.priceRange.otaySalvagePriceMxn)
        assertEquals("$8,900", prescription.priceRange.dealerPriceMxn)
        assertEquals(86, prescription.communityStats.successRatePercentage)

        // And compatible donors are verified
        val donor = prescription.compatibleDonors.first()
        assertEquals("Honda", donor.make)
        assertEquals("Civic", donor.model)
        assertEquals("2016 - 2020", donor.compatibleYears)
        assertEquals("2.0L K20C2", donor.engineSpec)
        assertEquals("Direct fitment on CVT transmission", donor.interchangeNote)

        // And excluded auction collision quadrants match Copart/IAAI codes
        assertTrue(prescription.excludedDonorImpactZones.contains(VehicleImpactQuadrant.FRONT_RIGHT))
        assertTrue(prescription.excludedDonorImpactZones.contains(VehicleImpactQuadrant.FLOOD_OR_FIRE))
        assertEquals("FR", VehicleImpactQuadrant.FRONT_RIGHT.auctionDamageCode)
        assertEquals("WATER_BURN", VehicleImpactQuadrant.FLOOD_OR_FIRE.auctionDamageCode)
    }

    /**
     * SCENARIO 3: Offline outbox queuing and automatic background sync.
     */
    @Test
    fun testScenario3_OfflineOutboxQueuingAndReconnectionSync() = runTest {
        // Given device is offline
        gateway.networkAvailable = false

        // And symptom sym_992 exists in LOCAL_TRIAGE_ONLY
        useCase.recordLocalTriage(
            vehicleId = activeVehicle.vehicleId,
            fullVin = activeVehicle.fullVin,
            symptomDescription = "Vibration under load",
            customSymptomId = "sym_992"
        )

        // When driver approves Ethical Value Gate while offline
        val result = useCase.unlockMechanicalPrescription(
            symptomId = "sym_992",
            fullVin = activeVehicle.fullVin
        )

        // Then record is safely queued in Outbox with OPT_IN_PENDING_NETWORK
        assertTrue(result is UnlockResult.QueuedOffline)
        val queuedRecord = repository.selectById("sym_992")
        assertEquals(PrescriptionSyncState.OPT_IN_PENDING_NETWORK, queuedRecord?.syncState)
        assertEquals(1, repository.selectPendingCloudSync().size)

        // When network connectivity is restored
        gateway.networkAvailable = true
        val flushedCount = useCase.flushPendingOutboxQueue()

        // Then outbox queue dispatches payload and transitions to SYNCED_CLOUD_VERIFIED
        assertEquals(1, flushedCount)
        val syncedRecord = repository.selectById("sym_992")
        assertEquals(PrescriptionSyncState.SYNCED_CLOUD_VERIFIED, syncedRecord?.syncState)
        assertEquals("44305-TBA-A01", syncedRecord?.oemPartNumber)
        assertEquals(0, repository.selectPendingCloudSync().size)
    }

    /**
     * SCENARIO 4: B2B Yonke leverage and retained demand accumulation.
     */
    @Test
    fun testScenario4_B2BYonkeLeverageAggregatesVerifiedDemandInOtay() = runTest {
        // Given 15 drivers in corridor Tijuana/Otay unlock prescriptions for OEM 44305-TBA-A01
        for (i in 1..15) {
            val symptomId = "sym_batch_$i"
            useCase.recordLocalTriage(
                vehicleId = "veh_$i",
                fullVin = "2HGFC2F59JH" + (100000 + i),
                symptomDescription = "Right CV axle vibration #$i",
                corridor = "Tijuana/Otay",
                customSymptomId = symptomId
            )
            useCase.unlockMechanicalPrescription(
                symptomId = symptomId,
                fullVin = "2HGFC2F59JH" + (100000 + i)
            )
        }

        // When Ktor Layer B aggregates SYNCED_CLOUD_VERIFIED events
        val summary = gateway.getRetainedDemandSummary(
            corridor = "Tijuana/Otay",
            oemPartNumber = "44305-TBA-A01"
        )

        // Then verified retained demand is $22,500 MXN for Civic 2016-2020 donors with no FRONT_RIGHT damage
        assertEquals("Tijuana/Otay", summary.corridor)
        assertEquals("44305-TBA-A01", summary.oemPartNumber)
        assertEquals(15, summary.unlockedCount)
        assertEquals("$22,500 MXN", summary.totalRetainedDemandMxn)
        assertEquals("Honda", summary.targetDonorMake)
        assertEquals("Civic", summary.targetDonorModel)
        assertEquals("2016-2020", summary.targetDonorYears)
        assertTrue(summary.excludedDamageQuadrants.contains(VehicleImpactQuadrant.FRONT_RIGHT))
        assertTrue(summary.matchingSalvageLotsAlertEnabled)
    }
}
