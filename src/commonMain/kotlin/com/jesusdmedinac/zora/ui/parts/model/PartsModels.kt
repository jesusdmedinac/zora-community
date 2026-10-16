package com.jesusdmedinac.zora.ui.parts.model

enum class PartCategory(val displayName: String) {
    ALL("All"),
    BRAKES("Brakes"),
    FLUIDS_AND_FILTERS("Fluids & Filters"),
    SUSPENSION("Suspension"),
    JUNKYARD_NETWORK("Junkyard Network"),
}

data class PartItem(
    val id: String,
    val title: String,
    val category: PartCategory,
    val badge: String,
    val oemInterchange: String? = null,
    val price: String,
    val savingsCalculation: String? = null,
    val donorVinAndMileage: String? = null,
    val distanceAndGrade: String? = null,
    val isSalvage: Boolean = false,
)

data class VisualBlueprintState(
    val componentName: String = "DENSO ALTERNATOR",
    val componentTag: String = "DENSO ALTERNATOR · Engine Bay (Front-Right Offset)",
    val isGenerated: Boolean = false,
    val quotaCount: Int = 1,
    val maxQuota: Int = 3,
    val disclaimer: String = "Note: AI-generated visual reference for illustration and component location only.",
) {
    val quotaText: String
        get() = "$quotaCount of $maxQuota Used"
}

data class CommunityInsightsState(
    val isOptedIn: Boolean = false,
    val sessionStatus: String = "Private Local Session Active",
    val explanation: String = "Telemetry exchange requires explicit opt-in to protect privacy.",
    val ctaText: String = "Review Cloud AI Opt-In Settings",
)

fun defaultPartsCatalog(): List<PartItem> = listOf(
    PartItem(
        id = "part_01",
        title = "Akebono Ultra-Premium Ceramic Front Brake Pads",
        category = PartCategory.BRAKES,
        badge = "Direct Fit Guarantee",
        oemInterchange = "#45022-TBA-A01",
        price = "$54.99",
    ),
    PartItem(
        id = "part_02",
        title = "Honda Genuine HCF-2 Transmission Fluid (1 Qt)",
        category = PartCategory.FLUIDS_AND_FILTERS,
        badge = "Direct Fit Guarantee",
        oemInterchange = "#08200-HCF2",
        price = "$14.50",
    ),
    PartItem(
        id = "part_03",
        title = "Front Lower Control Arm Assembly with Bushings",
        category = PartCategory.SUSPENSION,
        badge = "Direct Fit Guarantee",
        oemInterchange = "#51360-TBA-A01",
        price = "$89.00",
    ),
    PartItem(
        id = "part_04",
        title = "Denso 12V 135A Alternator OEM Salvage",
        category = PartCategory.JUNKYARD_NETWORK,
        badge = "Verified Salvage · Yonke Network",
        price = "$85.00",
        savingsCalculation = "Save 65% vs $240 New",
        donorVinAndMileage = "Donor VIN: 1HGFC2...7391 (41k mi)",
        distanceAndGrade = "9.2 mi away · Grade A Condition",
        isSalvage = true,
    ),
)
