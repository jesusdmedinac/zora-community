package com.jesusdmedinac.zora.ui.home

import com.jesusdmedinac.zora.ui.home.model.CopilotHomeIntent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CopilotHomeViewModelTest {

    @Test
    fun `initial uiState has correct defaults`() {
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
    fun `UpdatePromptText intent updates prompt text in state`() {
        val viewModel = CopilotHomeViewModel()

        viewModel.processIntent(CopilotHomeIntent.UpdatePromptText("Revisar presión de llantas"))

        assertEquals("Revisar presión de llantas", viewModel.uiState.value.promptText)
    }

    @Test
    fun `ClearPromptText intent resets prompt text to empty`() {
        val viewModel = CopilotHomeViewModel()
        viewModel.processIntent(CopilotHomeIntent.UpdatePromptText("Texto de prueba"))

        viewModel.processIntent(CopilotHomeIntent.ClearPromptText)

        assertEquals("", viewModel.uiState.value.promptText)
    }

    @Test
    fun `SetDrawerOpen intent controls drawer visibility`() {
        val viewModel = CopilotHomeViewModel()

        viewModel.processIntent(CopilotHomeIntent.SetDrawerOpen(true))
        assertTrue(viewModel.uiState.value.isDrawerOpen)

        viewModel.processIntent(CopilotHomeIntent.SetDrawerOpen(false))
        assertFalse(viewModel.uiState.value.isDrawerOpen)
    }

    @Test
    fun `SendPrompt intent clears prompt text after dispatch`() {
        val viewModel = CopilotHomeViewModel()
        viewModel.processIntent(CopilotHomeIntent.UpdatePromptText("¿Por qué vibra el volante?"))

        viewModel.processIntent(CopilotHomeIntent.SendPrompt("¿Por qué vibra el volante?"))

        assertEquals("", viewModel.uiState.value.promptText)
    }
}
