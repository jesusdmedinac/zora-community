package com.jesusdmedinac.zora.ui.splash.model

import org.jetbrains.compose.resources.StringResource
import zora_community.generated.resources.*

/**
 * State machine representing the automotive telemetry boot progression.
 */
enum class TelemetryBootStage(
    val tickerText: String,
    val textRes: StringResource,
    val thresholdProgress: Float
) {
    CAN_BUS_INIT(
        "INITIALIZING CAN BUS ISO 15765-4 • 500 KBPS",
        Res.string.splash_can_bus_progress,
        0.0f
    ),
    MEMORY_VAULT_INIT(
        "CALIBRATING LOCAL MEMORY VAULT...",
        Res.string.boot_stage_memory_vault,
        0.35f
    ),
    PRIVACY_GATE_ENGAGED(
        "ENGAGING OBD-II ZERO-TRUST PRIVACY GATE...",
        Res.string.boot_stage_privacy_gate,
        0.70f
    ),
    IGNITION_READY(
        "ZORA AUTONOMOUS COPILOT READY • ONLINE",
        Res.string.boot_stage_ignition_ready,
        1.0f
    );

    companion object {
        fun fromProgress(progress: Float): TelemetryBootStage {
            return when {
                progress >= 1.0f -> IGNITION_READY
                progress >= 0.70f -> PRIVACY_GATE_ENGAGED
                progress >= 0.35f -> MEMORY_VAULT_INIT
                else -> CAN_BUS_INIT
            }
        }
    }
}

data class TelemetryBootState(
    val progress: Float = 0f,
    val stage: TelemetryBootStage = TelemetryBootStage.CAN_BUS_INIT,
    val isComplete: Boolean = false
)
