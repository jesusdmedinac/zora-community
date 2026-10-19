package com.jesusdmedinac.zora.ui.home

import com.jesusdmedinac.zora.ui.home.model.CopilotHomeIntent
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeSideEffect
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeUiState
import kotlinx.coroutines.test.runTest
import org.orbitmvi.orbit.test.testWithInternalState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CopilotHomeViewModelTest {

    @Test
    fun `initial uiState has correct defaults`() = runTest {
        val viewModel = CopilotHomeViewModel()
        val state = viewModel.uiState.value

        assertEquals("", state.promptText)
        assertFalse(state.isDrawerOpen)
        assertEquals("Honda Civic EX 2.0L", state.activeVehicleName)
        assertEquals("12.6 V", state.batteryVoltage)
        assertEquals("0", state.dtcCount)
        assertEquals("Vault OK", state.prescriptionStatus)
        assertTrue(state.isCanBusConnected)
    }

    @Test
    fun `UpdatePromptText intent updates prompt text in state`() = runTest {
        val viewModel = CopilotHomeViewModel()
        viewModel.testWithInternalState(this) {
            containerHost.processIntent(CopilotHomeIntent.UpdatePromptText("Revisar presión de llantas"))
            expectInternalState { copy(promptText = "Revisar presión de llantas") }
        }
    }

    @Test
    fun `ClearPromptText intent resets prompt text to empty`() = runTest {
        val viewModel = CopilotHomeViewModel()
        viewModel.testWithInternalState(this, initialState = CopilotHomeUiState(promptText = "Texto de prueba")) {
            containerHost.processIntent(CopilotHomeIntent.ClearPromptText)
            expectInternalState { copy(promptText = "") }
        }
    }

    @Test
    fun `SetDrawerOpen intent controls drawer visibility`() = runTest {
        val viewModel = CopilotHomeViewModel()
        viewModel.testWithInternalState(this) {
            containerHost.processIntent(CopilotHomeIntent.SetDrawerOpen(true))
            expectInternalState { copy(isDrawerOpen = true) }
            containerHost.processIntent(CopilotHomeIntent.SetDrawerOpen(false))
            expectInternalState { copy(isDrawerOpen = false) }
        }
    }

    @Test
    fun `SendPrompt intent clears prompt text and posts NavigateToChat side effect`() = runTest {
        val viewModel = CopilotHomeViewModel()
        viewModel.testWithInternalState(this, initialState = CopilotHomeUiState(promptText = "¿Por qué vibra el volante?")) {
            containerHost.processIntent(CopilotHomeIntent.SendPrompt("¿Por qué vibra el volante?"))
            expectInternalState { copy(promptText = "") }
            expectSideEffect(CopilotHomeSideEffect.NavigateToChat("¿Por qué vibra el volante?"))
        }
    }

    @Test
    fun `SelectSuggestion intent posts NavigateToChat side effect`() = runTest {
        val viewModel = CopilotHomeViewModel()
        viewModel.testWithInternalState(this) {
            containerHost.processIntent(CopilotHomeIntent.SelectSuggestion("Humo azul"))
            expectSideEffect(CopilotHomeSideEffect.NavigateToChat("Humo azul"))
        }
    }

    @Test
    fun `OpenGarage intent closes drawer and posts NavigateToGarage side effect`() = runTest {
        val viewModel = CopilotHomeViewModel()
        viewModel.testWithInternalState(this, initialState = CopilotHomeUiState(isDrawerOpen = true)) {
            containerHost.processIntent(CopilotHomeIntent.OpenGarage)
            expectInternalState { copy(isDrawerOpen = false) }
            expectSideEffect(CopilotHomeSideEffect.NavigateToGarage)
        }
    }

    @Test
    fun `RefreshTelemetry intent updates sensor data`() = runTest {
        val viewModel = CopilotHomeViewModel()
        viewModel.testWithInternalState(
            this,
            initialState = CopilotHomeUiState(batteryVoltage = "11.8 V", dtcCount = "2"),
        ) {
            containerHost.processIntent(CopilotHomeIntent.RefreshTelemetry)
            expectInternalState {
                copy(
                    batteryVoltage = "12.6 V",
                    dtcCount = "0",
                    prescriptionStatus = "Vault OK",
                    isCanBusConnected = true,
                )
            }
        }
    }
}
