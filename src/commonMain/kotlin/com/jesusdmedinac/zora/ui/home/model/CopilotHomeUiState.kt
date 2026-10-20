package com.jesusdmedinac.zora.ui.home.model

import androidx.compose.runtime.Immutable

/**
 * Immutable UI state for Copilot Home screen.
 */
@Immutable
data class CopilotHomeUiState(
    val promptText: String = "",
    val isDrawerOpen: Boolean = false,
    val activeVehicleName: String = "Honda Civic EX 2.0L",
    val batteryVoltage: String = "12.6 V",
    val dtcCount: String = "0",
    val prescriptionStatus: String = "Vault OK",
    val isCanBusConnected: Boolean = true,
    val messages: List<CopilotChatMessage> = emptyList(),
)
