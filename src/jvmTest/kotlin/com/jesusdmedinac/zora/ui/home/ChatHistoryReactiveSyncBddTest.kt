package com.jesusdmedinac.zora.ui.home

import com.jesusdmedinac.zora.data.local.InMemoryMechanicalPrescriptionRepository
import com.jesusdmedinac.zora.ui.home.model.CopilotChatMessage
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeIntent
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeSideEffect
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeUiState
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerIntent
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerSideEffect
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerUiState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.orbitmvi.orbit.test.testWithInternalState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * BDD test verifying end-to-end reactive synchronization of conversation history
 * across [CopilotHomeViewModel], [ZoraShiftedDrawerViewModel], and [InMemoryMechanicalPrescriptionRepository].
 *
 * Implements BDD Scenario:
 * "Synchronize chat history reactively between ZoraShiftedDrawerViewModel, MechanicalPrescriptionRepository, and CopilotHomeViewModel"
 */
class ChatHistoryReactiveSyncBddTest {

    @Test
    fun `starting conversation saves to history, syncs reactively to drawer, and allows restoration`() = runTest {
        // Given a shared MechanicalPrescriptionRepository
        val repository = InMemoryMechanicalPrescriptionRepository()
        var idCounter = 0
        val fixedTime = 1729000000000L
        val homeViewModel = CopilotHomeViewModel(
            repository = repository,
            timeProvider = { fixedTime },
            messageIdGenerator = { prefix -> "${prefix}_${++idCounter}" },
            symptomIdGenerator = { "symptom_first_chat" },
        )

        val firstConversationPrompt = "¿Por qué vibra el volante a alta velocidad?"
        val followUpPrompt = "¿Y al frenar a 60 km/h?"

        // When the driver starts a diagnostic conversation in CopilotHomeViewModel
        homeViewModel.testWithInternalState(this) {
            containerHost.processIntent(CopilotHomeIntent.SendPrompt(firstConversationPrompt))
            expectInternalState {
                copy(
                    promptText = "",
                    messages = listOf(
                        CopilotChatMessage.User(text = firstConversationPrompt, id = "user_1"),
                        CopilotChatMessage.Assistant(
                            text = CopilotHomeViewModel.resolveAssistantReply(firstConversationPrompt, "Honda Civic EX 2.0L"),
                            id = "asst_2",
                        ),
                    ),
                    activeSymptomId = "symptom_first_chat",
                )
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)

            // And continuing the conversation updates the active triage record without creating duplicate entries
            containerHost.processIntent(CopilotHomeIntent.SendPrompt(followUpPrompt))
            expectInternalState {
                copy(
                    promptText = "",
                    messages = listOf(
                        CopilotChatMessage.User(text = firstConversationPrompt, id = "user_1"),
                        CopilotChatMessage.Assistant(
                            text = CopilotHomeViewModel.resolveAssistantReply(firstConversationPrompt, "Honda Civic EX 2.0L"),
                            id = "asst_2",
                        ),
                        CopilotChatMessage.User(text = followUpPrompt, id = "user_3"),
                        CopilotChatMessage.Assistant(
                            text = CopilotHomeViewModel.resolveAssistantReply(followUpPrompt, "Honda Civic EX 2.0L"),
                            id = "asst_4",
                        ),
                    ),
                    activeSymptomId = "symptom_first_chat",
                )
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)

            // When driver starts a new chat, the active stream is cleared
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

        // Then the triage conversation is saved to the local prescription repository with single record
        val storedEntities = repository.observeByVehicle("honda_civic_2018").first()
        assertEquals(1, storedEntities.size)
        val firstRecord = storedEntities.first()
        assertEquals(firstConversationPrompt, firstRecord.rawSymptomDescription)
        assertEquals("symptom_first_chat", firstRecord.symptomId)
        assertNotNull(firstRecord.serializedPrescriptionJson)

        // And ZoraShiftedDrawerViewModel loads recentQuestions from repository
        val drawerViewModel = ZoraShiftedDrawerViewModel(
            repository = repository,
            vehicleId = "honda_civic_2018",
        )
        drawerViewModel.testWithInternalState(this) {
            containerHost.processIntent(ZoraDrawerIntent.LoadRecentQuestions)
            expectInternalState {
                copy(recentQuestions = listOf(firstConversationPrompt))
            }

            containerHost.processIntent(ZoraDrawerIntent.SetDrawerOpen(true))
            expectInternalState {
                copy(isOpen = true, recentQuestions = listOf(firstConversationPrompt))
            }

            containerHost.processIntent(ZoraDrawerIntent.SelectRecentQuestion(firstConversationPrompt))
            expectInternalState {
                copy(isOpen = false, selectedRecentQuestion = firstConversationPrompt)
            }
            expectSideEffect(ZoraDrawerSideEffect.LoadRecentChat(firstConversationPrompt))
        }

        // When the driver selects the first conversation from history into a new home session
        val restoreHomeViewModel = CopilotHomeViewModel(
            repository = repository,
            messageIdGenerator = { prefix -> "${prefix}_${++idCounter}" },
        )
        restoreHomeViewModel.testWithInternalState(this, initialState = CopilotHomeUiState(isDrawerOpen = true)) {
            containerHost.processIntent(CopilotHomeIntent.SelectRecentChat(firstConversationPrompt))
            expectInternalState {
                copy(
                    isDrawerOpen = false,
                    messages = listOf(
                        CopilotChatMessage.User(text = firstConversationPrompt, id = "user_1"),
                        CopilotChatMessage.Assistant(
                            text = CopilotHomeViewModel.resolveAssistantReply(firstConversationPrompt, "Honda Civic EX 2.0L"),
                            id = "asst_2",
                        ),
                        CopilotChatMessage.User(text = followUpPrompt, id = "user_3"),
                        CopilotChatMessage.Assistant(
                            text = CopilotHomeViewModel.resolveAssistantReply(followUpPrompt, "Honda Civic EX 2.0L"),
                            id = "asst_4",
                        ),
                    ),
                    promptText = "",
                    activeSymptomId = "symptom_first_chat",
                )
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)
        }

        // When driver deletes the conversation from history via drawer
        val deleteDrawerViewModel = ZoraShiftedDrawerViewModel(
            initialState = ZoraDrawerUiState(
                isOpen = true,
                recentQuestions = listOf(firstConversationPrompt),
            ),
            repository = repository,
            vehicleId = "honda_civic_2018",
        )
        deleteDrawerViewModel.testWithInternalState(
            this,
            initialState = ZoraDrawerUiState(
                isOpen = true,
                recentQuestions = listOf(firstConversationPrompt),
            ),
        ) {
            containerHost.processIntent(ZoraDrawerIntent.DeleteRecentQuestion(firstConversationPrompt))
            expectInternalState {
                copy(
                    activeBottomSheetQuestion = null,
                    recentQuestions = emptyList(),
                    pinnedQuestions = emptyList(),
                    selectedRecentQuestion = null,
                )
            }
        }

        // Then it is purged from the repository
        val remainingEntities = repository.observeByVehicle("honda_civic_2018").first()
        assertTrue(remainingEntities.isEmpty())
    }
}
