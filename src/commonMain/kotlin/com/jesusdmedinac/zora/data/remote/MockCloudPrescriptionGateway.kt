package com.jesusdmedinac.zora.data.remote

import com.jesusdmedinac.zora.domain.gateway.CloudPrescriptionGateway
import com.jesusdmedinac.zora.domain.model.prescription.*

class MockCloudPrescriptionGateway : CloudPrescriptionGateway {
    var networkAvailable: Boolean = true
    private var requestCount: Int = 0
    private var lastPayload: DiagnosticOptInPayload? = null
    private val corridorDemands = mutableListOf<DiagnosticOptInPayload>()

    override suspend fun requestPrescription(payload: DiagnosticOptInPayload): Result<MechanicalPrescription> {
        if (!networkAvailable) {
            return Result.failure(IllegalStateException("Network offline: unable to reach zora-system cloud"))
        }

        requestCount++
        lastPayload = payload
        corridorDemands.add(payload)

        val prescription = MechanicalPrescription(
            prescriptionId = "rx_${payload.symptomId}",
            localSymptomId = payload.symptomId,
            oemPart = OemPartSpec(
                oemNumber = "44305-TBA-A01",
                partName = "Front Right CV Axle Assembly",
                category = "DRIVETRAIN"
            ),
            compatibleDonors = listOf(
                DonorCompatibilitySpec(
                    make = "Honda",
                    model = "Civic",
                    compatibleYears = "2016 - 2020",
                    engineSpec = "2.0L K20C2",
                    interchangeNote = "Direct fitment on CVT transmission"
                )
            ),
            excludedDonorImpactZones = listOf(
                VehicleImpactQuadrant.FRONT_RIGHT,
                VehicleImpactQuadrant.FLOOD_OR_FIRE
            ),
            priceRange = RegionalPriceRange(
                otaySalvagePriceMxn = "$1,200 - $1,800",
                dealerPriceMxn = "$8,900"
            ),
            communityStats = CommunityResolutionStats(
                successRatePercentage = 86,
                sampleSize = 418
            ),
            probableRootCause = "Outer CV joint needle bearing wear",
            confidenceScore = 0.86f,
            generativeUiSchemaJson = """{"type":"MechanicalPrescriptionCard","version":"1.0","oem":"44305-TBA-A01"}"""
        )

        return Result.success(prescription)
    }

    override fun getRequestCount(): Int = requestCount

    override fun getLastSubmittedPayload(): DiagnosticOptInPayload? = lastPayload

    override fun getRetainedDemandSummary(corridor: String, oemPartNumber: String): RetainedDemandSummary {
        val matches = corridorDemands.filter {
            it.corridor.equals(corridor, ignoreCase = true)
        }
        val count = matches.size
        val totalDemandMxn = "$22,500 MXN"

        return RetainedDemandSummary(
            corridor = corridor,
            oemPartNumber = oemPartNumber,
            unlockedCount = count,
            totalRetainedDemandMxn = totalDemandMxn,
            targetDonorMake = "Honda",
            targetDonorModel = "Civic",
            targetDonorYears = "2016-2020",
            excludedDamageQuadrants = listOf(VehicleImpactQuadrant.FRONT_RIGHT),
            matchingSalvageLotsAlertEnabled = true
        )
    }

    override fun reset() {
        requestCount = 0
        lastPayload = null
        corridorDemands.clear()
        networkAvailable = true
    }
}
