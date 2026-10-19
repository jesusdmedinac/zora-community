package com.jesusdmedinac.zora.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeIntent
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel managing the state and user interaction intents for Copilot Home.
 */
class CopilotHomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CopilotHomeUiState())
    val uiState: StateFlow<CopilotHomeUiState> = _uiState.asStateFlow()

    fun processIntent(intent: CopilotHomeIntent) {
        when (intent) {
            is CopilotHomeIntent.UpdatePromptText -> {
                _uiState.update { it.copy(promptText = intent.text) }
            }
            is CopilotHomeIntent.ClearPromptText -> {
                _uiState.update { it.copy(promptText = "") }
            }
            is CopilotHomeIntent.SetDrawerOpen -> {
                _uiState.update { it.copy(isDrawerOpen = intent.isOpen) }
            }
            is CopilotHomeIntent.SendPrompt -> {
                _uiState.update { it.copy(promptText = "") }
            }
            is CopilotHomeIntent.RefreshTelemetry -> {
                refreshTelemetry()
            }
        }
    }

    private fun refreshTelemetry() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    batteryVoltage = "12.6 V",
                    dtcCount = "0",
                    prescriptionStatus = "Vault OK",
                    isCanBusConnected = true,
                )
            }
        }
    }
}
