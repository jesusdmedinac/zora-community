package com.jesusdmedinac.zora.ui.home

import com.jesusdmedinac.zora.ui.home.model.CopilotChatMessage
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
        assertEquals(emptyList(), state.messages)
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
    fun `SendPrompt intent appends messages to chat stream and posts ScrollToBottom side effect`() = runTest {
        var idCount = 0
        val viewModel = CopilotHomeViewModel(messageIdGenerator = { prefix -> "${prefix}_${++idCount}" })
        val prompt = "¿Por qué vibra el volante?"
        viewModel.testWithInternalState(this, initialState = CopilotHomeUiState(promptText = prompt)) {
            containerHost.processIntent(CopilotHomeIntent.SendPrompt(prompt))
            expectInternalState {
                copy(
                    promptText = "",
                    messages = listOf(
                        CopilotChatMessage.User(text = prompt, id = "user_1"),
                        CopilotChatMessage.Assistant(
                            text = CopilotHomeViewModel.resolveAssistantReply(prompt, "Honda Civic EX 2.0L"),
                            id = "asst_2",
                        ),
                    ),
                )
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)
        }
    }

    @Test
    fun `SelectSuggestion intent adds messages to chat stream and posts ScrollToBottom side effect`() = runTest {
        var idCount = 0
        val viewModel = CopilotHomeViewModel(messageIdGenerator = { prefix -> "${prefix}_${++idCount}" })
        val symptom = "Humo azul"
        viewModel.testWithInternalState(this) {
            containerHost.processIntent(CopilotHomeIntent.SelectSuggestion(symptom))
            expectInternalState {
                copy(
                    messages = listOf(
                        CopilotChatMessage.User(text = symptom, id = "user_1"),
                        CopilotChatMessage.Assistant(
                            text = CopilotHomeViewModel.resolveAssistantReply(symptom, "Honda Civic EX 2.0L"),
                            id = "asst_2",
                        ),
                    ),
                )
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)
        }
    }

    @Test
    fun `OpenNewChat intent clears chat messages and closes drawer`() = runTest {
        val viewModel = CopilotHomeViewModel()
        val existingMessages = listOf(
            CopilotChatMessage.User("Consulta previa"),
            CopilotChatMessage.Assistant("Respuesta previa"),
        )
        viewModel.testWithInternalState(
            this,
            initialState = CopilotHomeUiState(isDrawerOpen = true, messages = existingMessages),
        ) {
            containerHost.processIntent(CopilotHomeIntent.OpenNewChat)
            expectInternalState { copy(isDrawerOpen = false, messages = emptyList(), promptText = "") }
        }
    }

    @Test
    fun `ClearChat intent empties message list`() = runTest {
        val viewModel = CopilotHomeViewModel()
        val existingMessages = listOf(CopilotChatMessage.User("Consulta"))
        viewModel.testWithInternalState(
            this,
            initialState = CopilotHomeUiState(messages = existingMessages),
        ) {
            containerHost.processIntent(CopilotHomeIntent.ClearChat)
            expectInternalState { copy(messages = emptyList()) }
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
    fun `OpenPaywall intent from mechanic closes drawer and posts NavigateToPaywall side effect`() = runTest {
        val viewModel = CopilotHomeViewModel()
        viewModel.testWithInternalState(this, initialState = CopilotHomeUiState(isDrawerOpen = true)) {
            containerHost.processIntent(CopilotHomeIntent.OpenPaywall(sourceReason = "drawer_menu_mechanic"))
            expectInternalState { copy(isDrawerOpen = false) }
            expectSideEffect(CopilotHomeSideEffect.NavigateToPaywall(sourceReason = "drawer_menu_mechanic"))
        }
    }

    @Test
    fun `OpenPaywall intent from gas stations closes drawer and posts NavigateToPaywall side effect`() = runTest {
        val viewModel = CopilotHomeViewModel()
        viewModel.testWithInternalState(this, initialState = CopilotHomeUiState(isDrawerOpen = true)) {
            containerHost.processIntent(CopilotHomeIntent.OpenPaywall(sourceReason = "drawer_menu_gas_stations"))
            expectInternalState { copy(isDrawerOpen = false) }
            expectSideEffect(CopilotHomeSideEffect.NavigateToPaywall(sourceReason = "drawer_menu_gas_stations"))
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
