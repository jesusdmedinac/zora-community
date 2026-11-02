package com.jesusdmedinac.zora.ui.home

import com.jesusdmedinac.jsontocompose.model.ComposeAction
import com.jesusdmedinac.jsontocompose.model.ComposeType
import com.jesusdmedinac.zora.data.local.InMemoryMechanicalPrescriptionRepository
import com.jesusdmedinac.zora.ui.home.model.CopilotChatMessage
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeIntent
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeSideEffect
import com.jesusdmedinac.zora.ui.home.sdui.ZoraSduiTemplates
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.jsonPrimitive
import org.orbitmvi.orbit.test.testWithInternalState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class CopilotDynamicSduiBddTest {

    @Test
    fun `prompt mentioning brakes generates specialist referral SDUI document`() = runTest {
        val repository = InMemoryMechanicalPrescriptionRepository()
        val viewModel = CopilotHomeViewModel(
            repository = repository,
            messageIdGenerator = { prefix -> "${prefix}_test" },
            symptomIdGenerator = { "sym_test" },
        )

        val prompt = "El pedal vibra al frenar en carretera"
        val resolution = CopilotHomeViewModel.resolveAssistantResolution(prompt, "Honda Civic EX 2.0L")
        assertNotNull(resolution.dynamicDocument)
        assertEquals(ComposeType.Card, resolution.dynamicDocument?.root?.type)

        val actionList = resolution.dynamicDocument?.actions?.values?.first()
        val customAction = actionList?.first() as ComposeAction.Custom
        assertEquals(ZoraSduiTemplates.ACTION_OPEN_PAYWALL, customAction.customType)
        assertEquals("mechanic_shop", customAction.params["source"]?.jsonPrimitive?.content)

        val expectedMessages = listOf(
            CopilotChatMessage.User(text = prompt, id = "user_test"),
            CopilotChatMessage.Assistant(
                text = resolution.text,
                dynamicDocument = resolution.dynamicDocument,
                id = "asst_test",
            ),
        )

        viewModel.testWithInternalState(this) {
            containerHost.processIntent(CopilotHomeIntent.SendPrompt(prompt))

            expectInternalState {
                copy(
                    promptText = "",
                    messages = expectedMessages,
                    activeSymptomId = "sym_test",
                )
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)
        }
    }

    @Test
    fun `prompt mentioning oil change generates confirm garage memory SDUI document`() = runTest {
        val repository = InMemoryMechanicalPrescriptionRepository()
        val viewModel = CopilotHomeViewModel(
            repository = repository,
            messageIdGenerator = { prefix -> "${prefix}_test" },
            symptomIdGenerator = { "sym_test" },
        )

        val prompt = "Ayer le cambié el aceite"
        val resolution = CopilotHomeViewModel.resolveAssistantResolution(prompt, "Honda Civic EX 2.0L")
        val doc = resolution.dynamicDocument
        assertNotNull(doc)
        assertEquals(ComposeType.Card, doc.root.type)
        assertEquals(2, doc.actions.size)

        val confirmAction = doc.actions["on_confirm_memory_event"]?.first() as ComposeAction.Custom
        assertEquals(ZoraSduiTemplates.ACTION_CONFIRM_MEMORY, confirmAction.customType)

        val expectedMessages = listOf(
            CopilotChatMessage.User(text = prompt, id = "user_test"),
            CopilotChatMessage.Assistant(
                text = resolution.text,
                dynamicDocument = doc,
                id = "asst_test",
            ),
        )

        viewModel.testWithInternalState(this) {
            containerHost.processIntent(CopilotHomeIntent.SendPrompt(prompt))

            expectInternalState {
                copy(
                    promptText = "",
                    messages = expectedMessages,
                    activeSymptomId = "sym_test",
                )
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)
        }
    }

    @Test
    fun `ConfirmGarageMemory intent updates chat stream with confirmation message`() = runTest {
        val repository = InMemoryMechanicalPrescriptionRepository()
        val viewModel = CopilotHomeViewModel(
            repository = repository,
            messageIdGenerator = { prefix -> "${prefix}_ack" },
            symptomIdGenerator = { "sym_test" },
        )

        val memory = "Aceite sintético 0W-20"
        val expectedMessage = CopilotChatMessage.Assistant(
            text = "Confirmado: He guardado \"$memory\" en la memoria de tu Garaje Virtual.",
            id = "asst_ack",
        )

        viewModel.testWithInternalState(this) {
            containerHost.processIntent(CopilotHomeIntent.ConfirmGarageMemory(memory))

            expectInternalState {
                copy(messages = listOf(expectedMessage))
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)
        }
    }

    @Test
    fun `DismissGarageMemory intent acknowledges dismissal without persisting`() = runTest {
        val repository = InMemoryMechanicalPrescriptionRepository()
        val viewModel = CopilotHomeViewModel(
            repository = repository,
            messageIdGenerator = { prefix -> "${prefix}_dismiss" },
            symptomIdGenerator = { "sym_test" },
        )

        val memory = "Detalle rechazado"
        val expectedMessage = CopilotChatMessage.Assistant(
            text = "Entendido, no he guardado \"$memory\" en tu Garaje.",
            id = "asst_dismiss",
        )

        viewModel.testWithInternalState(this) {
            containerHost.processIntent(CopilotHomeIntent.DismissGarageMemory(memory))

            expectInternalState {
                copy(messages = listOf(expectedMessage))
            }
            expectSideEffect(CopilotHomeSideEffect.ScrollToBottom)
        }
    }
}
