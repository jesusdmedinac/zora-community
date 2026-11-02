package com.jesusdmedinac.zora.ui.home

import com.jesusdmedinac.zora.data.local.InMemoryMechanicalPrescriptionRepository
import com.jesusdmedinac.zora.ui.home.model.CopilotChatMessage
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeIntent
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeSideEffect
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.orbitmvi.orbit.test.testWithInternalState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
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
        val viewModel = CopilotHomeViewModel(
            repository = InMemoryMechanicalPrescriptionRepository(),
            messageIdGenerator = { prefix -> "${prefix}_${++idCount}" },
            symptomIdGenerator = { "sym_test_1" },
        )
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
                    activeSymptomId = "sym_test_1",
                )
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)
        }
    }

    @Test
    fun `SelectSuggestion intent adds messages to chat stream and posts ScrollToBottom side effect`() = runTest {
        var idCount = 0
        val viewModel = CopilotHomeViewModel(
            repository = InMemoryMechanicalPrescriptionRepository(),
            messageIdGenerator = { prefix -> "${prefix}_${++idCount}" },
            symptomIdGenerator = { "sym_test_2" },
        )
        val symptom = "Humo azul"
        val resolution = CopilotHomeViewModel.resolveAssistantResolution(symptom, "Honda Civic EX 2.0L")
        viewModel.testWithInternalState(this) {
            containerHost.processIntent(CopilotHomeIntent.SelectSuggestion(symptom))
            expectInternalState {
                copy(
                    messages = listOf(
                        CopilotChatMessage.User(text = symptom, id = "user_1"),
                        CopilotChatMessage.Assistant(
                            text = resolution.text,
                            dynamicDocument = resolution.dynamicDocument,
                            id = "asst_2",
                        ),
                    ),
                    activeSymptomId = "sym_test_2",
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

    @Test
    fun `SelectRecentChat loads seeded hypothesis from repository`() = runTest {
        var idCount = 0
        val repo = InMemoryMechanicalPrescriptionRepository()
        val question = "¿Qué es la bujía?"
        val customHypothesis = "La bujía genera la chispa de ignición."
        val now = 1729000000000L
        repo.insertLocalTriage(
            com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescriptionEntity(
                symptomId = "seed_1",
                vehicleId = "honda_civic_2018",
                vinTechnicalPrefix = "2HGFC2F5",
                rawSymptomDescription = question,
                localPreliminaryHypothesis = customHypothesis,
                createdAtMs = now,
                updatedAtMs = now,
            )
        )

        val viewModel = CopilotHomeViewModel(
            repository = repo,
            messageIdGenerator = { prefix -> "${prefix}_${++idCount}" },
        )
        viewModel.testWithInternalState(this, initialState = CopilotHomeUiState(isDrawerOpen = true)) {
            containerHost.processIntent(CopilotHomeIntent.SelectRecentChat(question))
            expectInternalState {
                copy(
                    isDrawerOpen = false,
                    messages = listOf(
                        CopilotChatMessage.User(text = question, id = "user_1"),
                        CopilotChatMessage.Assistant(text = customHypothesis, id = "asst_2"),
                    ),
                    promptText = "",
                    activeSymptomId = "seed_1",
                )
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)
        }
    }

    @Test
    fun `SendPrompt persists new triage entity into repository`() = runTest {
        var idCount = 0
        val repo = InMemoryMechanicalPrescriptionRepository()
        val fixedTime = 1729500000000L
        val viewModel = CopilotHomeViewModel(
            repository = repo,
            timeProvider = { fixedTime },
            messageIdGenerator = { prefix -> "${prefix}_${++idCount}" },
            symptomIdGenerator = { "sym_prompt_1" },
        )
        val prompt = "¿Por qué vibra el volante?"
        viewModel.testWithInternalState(this) {
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
                    activeSymptomId = "sym_prompt_1",
                )
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)
        }

        val entities = repo.observeByVehicle("honda_civic_2018").first()
        val saved = entities.find { it.rawSymptomDescription == prompt }
        assertNotNull(saved)
        assertEquals(fixedTime, saved.createdAtMs)
        assertEquals("2HGFC2F5", saved.vinTechnicalPrefix)
        assertNotNull(saved.serializedPrescriptionJson)
    }

    @Test
    fun `SelectSuggestion persists new triage entity into repository`() = runTest {
        var idCount = 0
        val repo = InMemoryMechanicalPrescriptionRepository()
        val fixedTime = 1729600000000L
        val viewModel = CopilotHomeViewModel(
            repository = repo,
            timeProvider = { fixedTime },
            messageIdGenerator = { prefix -> "${prefix}_${++idCount}" },
            symptomIdGenerator = { "sym_suggestion_1" },
        )
        val symptom = "Humo azul"
        val resolution = CopilotHomeViewModel.resolveAssistantResolution(symptom, "Honda Civic EX 2.0L")
        viewModel.testWithInternalState(this) {
            containerHost.processIntent(CopilotHomeIntent.SelectSuggestion(symptom))
            expectInternalState {
                copy(
                    messages = listOf(
                        CopilotChatMessage.User(text = symptom, id = "user_1"),
                        CopilotChatMessage.Assistant(
                            text = resolution.text,
                            dynamicDocument = resolution.dynamicDocument,
                            id = "asst_2",
                        ),
                    ),
                    activeSymptomId = "sym_suggestion_1",
                )
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)
        }

        val entities = repo.observeByVehicle("honda_civic_2018").first()
        val saved = entities.find { it.rawSymptomDescription == symptom }
        assertNotNull(saved)
        assertEquals(fixedTime, saved.createdAtMs)
        assertNotNull(saved.serializedPrescriptionJson)
    }

    @Test
    fun `Continuing conversation appends messages to activeSymptomId without creating duplicate history entries`() = runTest {
        var idCount = 0
        val repo = InMemoryMechanicalPrescriptionRepository()
        var currentTime = 1729500000000L
        val viewModel = CopilotHomeViewModel(
            repository = repo,
            timeProvider = { currentTime },
            messageIdGenerator = { prefix -> "${prefix}_${++idCount}" },
            symptomIdGenerator = { "sym_conv_1" },
        )
        val firstPrompt = "¿Por qué vibra el volante?"
        val secondPrompt = "¿Y al frenar a 60 km/h?"

        viewModel.testWithInternalState(this) {
            // First turn (starts conversation)
            containerHost.processIntent(CopilotHomeIntent.SendPrompt(firstPrompt))
            expectInternalState {
                copy(
                    promptText = "",
                    messages = listOf(
                        CopilotChatMessage.User(text = firstPrompt, id = "user_1"),
                        CopilotChatMessage.Assistant(
                            text = CopilotHomeViewModel.resolveAssistantReply(firstPrompt, "Honda Civic EX 2.0L"),
                            id = "asst_2",
                        ),
                    ),
                    activeSymptomId = "sym_conv_1",
                )
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)

            // Second turn (continues conversation)
            currentTime += 5000L
            val secondResolution = CopilotHomeViewModel.resolveAssistantResolution(secondPrompt, "Honda Civic EX 2.0L")
            containerHost.processIntent(CopilotHomeIntent.SendPrompt(secondPrompt))
            expectInternalState {
                copy(
                    promptText = "",
                    messages = listOf(
                        CopilotChatMessage.User(text = firstPrompt, id = "user_1"),
                        CopilotChatMessage.Assistant(
                            text = CopilotHomeViewModel.resolveAssistantReply(firstPrompt, "Honda Civic EX 2.0L"),
                            id = "asst_2",
                        ),
                        CopilotChatMessage.User(text = secondPrompt, id = "user_3"),
                        CopilotChatMessage.Assistant(
                            text = secondResolution.text,
                            dynamicDocument = secondResolution.dynamicDocument,
                            id = "asst_4",
                        ),
                    ),
                    activeSymptomId = "sym_conv_1",
                )
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)
        }

        // Verify repository contains only ONE entity with the initial conversation title
        val entities = repo.observeByVehicle("honda_civic_2018").first()
        assertEquals(1, entities.size)
        val saved = entities.first()
        assertEquals("sym_conv_1", saved.symptomId)
        assertEquals(firstPrompt, saved.rawSymptomDescription)
        assertEquals(1729500000000L, saved.createdAtMs)
        assertEquals(1729500005000L, saved.updatedAtMs)
    }

    @Test
    fun `OpenNewChat clears activeSymptomId and messages allowing fresh conversation`() = runTest {
        val repo = InMemoryMechanicalPrescriptionRepository()
        val viewModel = CopilotHomeViewModel(repository = repo)
        val initialMessages = listOf(
            CopilotChatMessage.User("Mensaje previo"),
            CopilotChatMessage.Assistant("Respuesta previa"),
        )
        viewModel.testWithInternalState(
            this,
            initialState = CopilotHomeUiState(
                isDrawerOpen = true,
                messages = initialMessages,
                activeSymptomId = "sym_prev_1",
            ),
        ) {
            containerHost.processIntent(CopilotHomeIntent.OpenNewChat)
            expectInternalState {
                copy(
                    isDrawerOpen = false,
                    messages = emptyList(),
                    promptText = "",
                    activeSymptomId = null,
                )
            }
        }
    }

    @Test
    fun `SelectRecentChat restores activeSymptomId and deserializes previous messages from serializedPrescriptionJson`() = runTest {
        val repo = InMemoryMechanicalPrescriptionRepository()
        val question = "Ruido en transmisión"
        val serializedChat = kotlinx.serialization.json.Json.encodeToString(
            listOf(
                CopilotChatMessage.User(text = question, id = "u_stored_1"),
                CopilotChatMessage.Assistant(text = "Nivel bajo de fluido CVT", id = "a_stored_2"),
                CopilotChatMessage.User(text = "¿Qué aceite lleva?", id = "u_stored_3"),
                CopilotChatMessage.Assistant(text = "Honda HCF-2 para transmisión CVT", id = "a_stored_4"),
            )
        )
        val entity = com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescriptionEntity(
            symptomId = "sym_cvt_1",
            vehicleId = "honda_civic_2018",
            vinTechnicalPrefix = "2HGFC2F5",
            rawSymptomDescription = question,
            localPreliminaryHypothesis = "Nivel bajo de fluido CVT",
            serializedPrescriptionJson = serializedChat,
            createdAtMs = 1000L,
            updatedAtMs = 1000L,
        )
        repo.insertLocalTriage(entity)

        val viewModel = CopilotHomeViewModel(repository = repo)
        viewModel.testWithInternalState(this, initialState = CopilotHomeUiState(isDrawerOpen = true)) {
            containerHost.processIntent(CopilotHomeIntent.SelectRecentChat(question))
            expectInternalState {
                copy(
                    isDrawerOpen = false,
                    messages = listOf(
                        CopilotChatMessage.User(text = question, id = "u_stored_1"),
                        CopilotChatMessage.Assistant(text = "Nivel bajo de fluido CVT", id = "a_stored_2"),
                        CopilotChatMessage.User(text = "¿Qué aceite lleva?", id = "u_stored_3"),
                        CopilotChatMessage.Assistant(text = "Honda HCF-2 para transmisión CVT", id = "a_stored_4"),
                    ),
                    promptText = "",
                    activeSymptomId = "sym_cvt_1",
                )
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)
        }
    }
}
