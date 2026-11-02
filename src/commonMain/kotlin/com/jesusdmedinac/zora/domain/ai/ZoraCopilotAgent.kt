package com.jesusdmedinac.zora.domain.ai

/**
 * Domain contract for the Zora Driver Copilot.
 * Focuses on informative guidance, conversational memory proposals for the virtual garage,
 * and routing towards external specialists (mechanic shop, salvage yard, gas station).
 */
interface ZoraCopilotAgent {
    suspend fun interact(
        driverPrompt: String,
        garageContext: GarageVehicleContext,
    ): CopilotReply
}

data class GarageVehicleContext(
    val make: String,
    val model: String,
    val year: Int,
    val learnedMemories: List<String> = emptyList(), // Max 5 dynamic memories in the free community tier
    val vehicleDisplayName: String = "$year $make $model",
)

data class CopilotReply(
    val message: String,
    val memoryToConfirm: String? = null, // Proposed fact for the driver to confirm before storing in garage
    val suggestedSpecialist: SpecialistType? = null,
    val isLocalFallback: Boolean = false,
)

enum class SpecialistType {
    MECHANIC_SHOP,
    SALVAGE_YARD, // Yonke
    GAS_STATION,
}
