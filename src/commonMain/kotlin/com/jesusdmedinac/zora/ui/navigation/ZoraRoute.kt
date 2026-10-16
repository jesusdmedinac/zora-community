package com.jesusdmedinac.zora.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Type-safe Navigation 3 routes for Zora Automotive Intelligence.
 * Conforms to `androidx.navigation3.runtime.NavKey` and serializable architecture.
 */
@Serializable
sealed interface ZoraRoute : NavKey {
    /**
     * Boot ignition telemetry sequence.
     */
    @Serializable
    data object Splash : ZoraRoute

    /**
     * Primary copilot screen with floating top app bar and shifted modal drawer.
     */
    @Serializable
    data object CopilotHome : ZoraRoute

    /**
     * Diagnostic and vehicle onboarding conversation with engine configuration cards.
     */
    @Serializable
    data class OnboardingChat(
        val initialSymptom: String? = null,
        val vehicleModel: String = "Honda Civic 2018",
    ) : ZoraRoute

    /**
     * Virtual Garage memory vault and cryptographic mechanical prescriptions.
     */
    @Serializable
    data object GarageVault : ZoraRoute

    /**
     * Contextual parts catalog, yonke salvage network, and 3D visual component blueprints.
     */
    @Serializable
    data object PartsFeed : ZoraRoute

    /**
     * Autonomous multi-agent network and subscription paywall.
     */
    @Serializable
    data class ProPaywall(val sourceReason: String? = null) : ZoraRoute
}
