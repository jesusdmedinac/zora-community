package com.jesusdmedinac.zora.domain.model.prescription

import kotlinx.serialization.Serializable

@Serializable
data class OemPartSpec(
    val oemNumber: String,
    val partName: String,
    val category: String = "DRIVETRAIN"
)

@Serializable
data class DonorCompatibilitySpec(
    val make: String,
    val model: String,
    val compatibleYears: String,
    val engineSpec: String,
    val interchangeNote: String
)

@Serializable
data class RegionalPriceRange(
    val otaySalvagePriceMxn: String,
    val dealerPriceMxn: String,
    val aftermarketPriceMxn: String? = null
)

@Serializable
data class CommunityResolutionStats(
    val successRatePercentage: Int,
    val sampleSize: Int = 100
)

@Serializable
data class MechanicalPrescription(
    val prescriptionId: String,
    val localSymptomId: String,
    val oemPart: OemPartSpec,
    val compatibleDonors: List<DonorCompatibilitySpec>,
    val excludedDonorImpactZones: List<VehicleImpactQuadrant>,
    val priceRange: RegionalPriceRange,
    val communityStats: CommunityResolutionStats,
    val probableRootCause: String,
    val confidenceScore: Float = 0.86f,
    /**
     * Tier 2 ($149 Basic Starter Kit) hook:
     * Contains declarative schema rendered dynamically by `json-to-compose`.
     */
    val generativeUiSchemaJson: String? = null
)

@Serializable
data class DiagnosticOptInPayload(
    val symptomId: String,
    val technicalVinPrefix: String,
    val corridor: String = "Tijuana/Otay",
    val conditionPreference: String = "OEM_USED",
    val reportedSymptom: String,
    val preliminaryHypothesis: String,
    val timestampMs: Long
)

@Serializable
data class RetainedDemandSummary(
    val corridor: String,
    val oemPartNumber: String,
    val unlockedCount: Int,
    val totalRetainedDemandMxn: String,
    val targetDonorMake: String,
    val targetDonorModel: String,
    val targetDonorYears: String,
    val excludedDamageQuadrants: List<VehicleImpactQuadrant>,
    val matchingSalvageLotsAlertEnabled: Boolean
)
