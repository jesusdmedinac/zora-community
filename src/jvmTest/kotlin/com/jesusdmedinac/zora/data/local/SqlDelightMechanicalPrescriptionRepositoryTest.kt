package com.jesusdmedinac.zora.data.local

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.jesusdmedinac.zora.data.local.adapter.PrescriptionSyncStateAdapter
import com.jesusdmedinac.zora.data.local.adapter.VehicleImpactQuadrantListAdapter
import com.jesusdmedinac.zora.domain.model.prescription.*
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class SqlDelightMechanicalPrescriptionRepositoryTest {

    private lateinit var database: MechanicalPrescriptionVaultDatabase
    private lateinit var repository: SqlDelightMechanicalPrescriptionRepository

    @BeforeTest
    fun setup() = runTest {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        MechanicalPrescriptionVaultDatabase.Schema.create(driver).await()

        database = MechanicalPrescriptionVaultDatabase(
            driver = driver,
            mechanical_prescription_vaultAdapter = Mechanical_prescription_vault.Adapter(
                sync_stateAdapter = PrescriptionSyncStateAdapter,
                excluded_impact_zonesAdapter = VehicleImpactQuadrantListAdapter
            )
        )
        repository = SqlDelightMechanicalPrescriptionRepository(database)
    }

    @Test
    fun testSqlDelightCRUDAndOutboxOperations() = runTest {
        val entity = MechanicalPrescriptionEntity(
            symptomId = "sym_test_01",
            vehicleId = "veh_01",
            vinTechnicalPrefix = "2HGFC2F59JH",
            rawSymptomDescription = "Test clicking on right turn",
            localPreliminaryHypothesis = "Probable CV Axle failure",
            syncState = PrescriptionSyncState.LOCAL_TRIAGE_ONLY,
            createdAtMs = 1000L,
            updatedAtMs = 1000L
        )

        // 1. Insert local triage
        val insertResult = repository.insertLocalTriage(entity)
        assertTrue(insertResult.isSuccess)

        val retrieved = repository.selectById("sym_test_01")
        assertNotNull(retrieved)
        assertEquals(PrescriptionSyncState.LOCAL_TRIAGE_ONLY, retrieved.syncState)

        // 2. Mark opt-in pending sync (Outbox)
        repository.markOptInPending("sym_test_01", optInTimestampMs = 2000L, updatedAtMs = 2000L)
        val pendingList = repository.selectPendingCloudSync()
        assertEquals(1, pendingList.size)
        assertEquals(PrescriptionSyncState.OPT_IN_PENDING_NETWORK, pendingList.first().syncState)

        // 3. Update with cloud prescription
        val prescription = MechanicalPrescription(
            prescriptionId = "rx_test_01",
            localSymptomId = "sym_test_01",
            oemPart = OemPartSpec("44305-TBA-A01", "Front Right CV Axle Assembly"),
            compatibleDonors = listOf(
                DonorCompatibilitySpec("Honda", "Civic", "2016-2020", "2.0L", "Direct Fit")
            ),
            excludedDonorImpactZones = listOf(
                VehicleImpactQuadrant.FRONT_RIGHT,
                VehicleImpactQuadrant.FLOOD_OR_FIRE
            ),
            priceRange = RegionalPriceRange("$1,200 - $1,800", "$8,900"),
            communityStats = CommunityResolutionStats(86),
            probableRootCause = "CV Axle Wear"
        )
        repository.updateWithCloudPrescription("sym_test_01", prescription, updatedAtMs = 3000L)

        val verified = repository.selectById("sym_test_01")
        assertNotNull(verified)
        assertEquals(PrescriptionSyncState.SYNCED_CLOUD_VERIFIED, verified.syncState)
        assertEquals("44305-TBA-A01", verified.oemPartNumber)
        assertTrue(verified.excludedImpactZones.contains(VehicleImpactQuadrant.FRONT_RIGHT))
        assertTrue(verified.excludedImpactZones.contains(VehicleImpactQuadrant.FLOOD_OR_FIRE))
        assertEquals(0, repository.selectPendingCloudSync().size)

        // 4. Revoke consent
        repository.revokeCloudConsent("sym_test_01", 4000L)
        val revoked = repository.selectById("sym_test_01")
        assertEquals(PrescriptionSyncState.REVOKED_BY_USER, revoked?.syncState)

        // 5. Purge by id
        repository.purgeById("sym_test_01")
        assertNull(repository.selectById("sym_test_01"))
    }
}
