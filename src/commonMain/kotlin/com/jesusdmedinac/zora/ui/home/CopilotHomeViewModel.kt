package com.jesusdmedinac.zora.ui.home

import androidx.lifecycle.ViewModel
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeIntent
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeSideEffect
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeUiState
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.viewmodel.orbitContainer

/**
 * ViewModel managing the state, intents, and side effects for Copilot Home via Orbit MVI.
 */
class CopilotHomeViewModel :
    ViewModel(),
    OrbitContainerHost<CopilotHomeUiState, CopilotHomeUiState, CopilotHomeSideEffect> {

    override val container = orbitContainer<CopilotHomeUiState, CopilotHomeSideEffect>(CopilotHomeUiState())

    val uiState: kotlinx.coroutines.flow.StateFlow<CopilotHomeUiState>
        get() = container.stateFlow

    val sideEffect: kotlinx.coroutines.flow.Flow<CopilotHomeSideEffect>
        get() = container.sideEffectFlow

    fun processIntent(intent: CopilotHomeIntent) {
        when (intent) {
            is CopilotHomeIntent.UpdatePromptText -> updatePromptText(intent.text)
            is CopilotHomeIntent.ClearPromptText -> clearPromptText()
            is CopilotHomeIntent.SetDrawerOpen -> setDrawerOpen(intent.isOpen)
            is CopilotHomeIntent.SendPrompt -> sendPrompt(intent.text)
            is CopilotHomeIntent.SelectSuggestion -> selectSuggestion(intent.symptom)
            is CopilotHomeIntent.OpenNewChat -> openNewChat()
            is CopilotHomeIntent.SelectRecentChat -> selectRecentChat(intent.question)
            is CopilotHomeIntent.OpenGarage -> openGarage()
            is CopilotHomeIntent.RefreshTelemetry -> refreshTelemetry()
        }
    }

    fun updatePromptText(text: String) = intent {
        reduce { state.copy(promptText = text) }
    }

    fun clearPromptText() = intent {
        reduce { state.copy(promptText = "") }
    }

    fun setDrawerOpen(isOpen: Boolean) = intent {
        reduce { state.copy(isDrawerOpen = isOpen) }
    }

    fun sendPrompt(text: String) = intent {
        val trimmed = text.trim()
        reduce { state.copy(promptText = "") }
        postSideEffect(CopilotHomeSideEffect.NavigateToChat(trimmed.ifEmpty { null }))
    }

    fun selectSuggestion(symptom: String) = intent {
        postSideEffect(CopilotHomeSideEffect.NavigateToChat(symptom))
    }

    fun openNewChat() = intent {
        reduce { state.copy(isDrawerOpen = false) }
        postSideEffect(CopilotHomeSideEffect.NavigateToChat(null))
    }

    fun selectRecentChat(question: String) = intent {
        reduce { state.copy(isDrawerOpen = false) }
        postSideEffect(CopilotHomeSideEffect.NavigateToChat(question))
    }

    fun openGarage() = intent {
        reduce { state.copy(isDrawerOpen = false) }
        postSideEffect(CopilotHomeSideEffect.NavigateToGarage)
    }

    fun refreshTelemetry() = intent {
        reduce {
            state.copy(
                batteryVoltage = "12.6 V",
                dtcCount = "0",
                prescriptionStatus = "Vault OK",
                isCanBusConnected = true,
            )
        }
    }
}
