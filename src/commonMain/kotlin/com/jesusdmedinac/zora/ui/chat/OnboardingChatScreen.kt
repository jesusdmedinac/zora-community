package com.jesusdmedinac.zora.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jesusdmedinac.zora.ui.chat.component.AssistantChatBubble
import com.jesusdmedinac.zora.ui.chat.component.ChatTopBar
import com.jesusdmedinac.zora.ui.chat.component.UserChatBubble
import com.jesusdmedinac.zora.ui.chat.component.ZoraAssistantActionBar
import com.jesusdmedinac.zora.ui.chat.component.ZoraCarePrioritiesCard
import com.jesusdmedinac.zora.ui.chat.component.ZoraEngineSelectionCard
import com.jesusdmedinac.zora.ui.chat.component.ZoraVisualReferenceProposalCard
import com.jesusdmedinac.zora.ui.home.component.ZoraPromptComposer
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import com.jesusdmedinac.zora.ui.theme.zoraEdgePadding
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Chat message model hierarchy for dynamic onboarding stream.
 */
sealed interface OnboardingChatMessage {
    val id: String

    data class User(
        val text: String,
        override val id: String = "user_${text.hashCode()}_${RandomSource.next()}",
    ) : OnboardingChatMessage

    data class Assistant(
        val text: String,
        val hasEngineCard: Boolean = false,
        val hasCarePriorities: Boolean = false,
        val hasVisualProposal: Boolean = false,
        override val id: String = "asst_${text.hashCode()}_${RandomSource.next()}",
    ) : OnboardingChatMessage
}

typealias ChatMessage = OnboardingChatMessage

private object RandomSource {
    private var counter = 0
    fun next(): Int = ++counter
}

/**
 * Screen / M3 - 03 Onboarding Chat & HIG - 03.
 * Atomized guided diagnostic conversation with AI mechanic.
 */
@Composable
fun OnboardingChatScreen(
    initialSymptom: String?,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    vehicleModel: String = "Honda Civic 2018",
) {
    var selectedEngine by remember { mutableStateOf("2.0L_NA") }
    var inputMessage by remember { mutableStateOf("") }

    val messages = remember {
        mutableStateListOf<OnboardingChatMessage>().apply {
            if (initialSymptom != null) {
                add(OnboardingChatMessage.User(initialSymptom))
            }
            add(
                OnboardingChatMessage.Assistant(
                    text = "He detectado tu vehículo como $vehicleModel mediante CAN-Bus. Para calibrar el diagnóstico y la prescripción mecánica precisa, por favor confirma la variante del motor:",
                    hasEngineCard = true,
                )
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ZoraTokens.Canvas)
            .zoraEdgePadding(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        // 1. Top Header with Back Button
        ChatTopBar(
            vehicleModel = vehicleModel,
            onBack = onBack,
        )

        // 2. Chat Stream Content
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ZoraTokens.SpacingMd, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            messages.forEach { msg ->
                when (msg) {
                    is OnboardingChatMessage.User -> {
                        UserChatBubble(text = msg.text)
                    }

                    is OnboardingChatMessage.Assistant -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            AssistantChatBubble(text = msg.text)

                            if (msg.hasEngineCard) {
                                ZoraEngineSelectionCard(
                                    selectedEngineId = selectedEngine,
                                    onSelectEngine = { selectedEngine = it },
                                    onConfirm = { engineId ->
                                        messages.add(
                                            OnboardingChatMessage.Assistant(
                                                text = "✓ Motor confirmado: $engineId. Diagnóstico local sincronizado con CAN-Bus y registrado en la bóveda mecánica.",
                                                hasEngineCard = false,
                                            )
                                        )
                                        messages.add(
                                            OnboardingChatMessage.Assistant(
                                                text = "Evaluando necesidades de mantenimiento y partes para tu vehículo:",
                                                hasCarePriorities = true,
                                            )
                                        )
                                        messages.add(
                                            OnboardingChatMessage.Assistant(
                                                text = "He preparado una propuesta de referencia visual para tu Garage Slot #01:",
                                                hasVisualProposal = true,
                                            )
                                        )
                                    },
                                )
                            }

                            if (msg.hasCarePriorities) {
                                ZoraCarePrioritiesCard()
                            }

                            if (msg.hasVisualProposal) {
                                ZoraVisualReferenceProposalCard(
                                    onGenerate = {
                                        messages.add(
                                            OnboardingChatMessage.Assistant(
                                                text = "✓ Referencia visual generada para Garage Slot #01. Archivada en tu bóveda local.",
                                            )
                                        )
                                    },
                                    onSkip = {
                                        messages.add(
                                            OnboardingChatMessage.Assistant(
                                                text = "Generación visual omitida. Tu perfil de vehículo sigue guardado con normalidad.",
                                            )
                                        )
                                    },
                                )
                            }

                            ZoraAssistantActionBar()
                        }
                    }
                }
            }
        }

        // 3. Bottom Composer
        ZoraPromptComposer(
            value = inputMessage,
            onValueChange = { inputMessage = it },
            onSend = { text ->
                if (text.isNotBlank()) {
                    messages.add(OnboardingChatMessage.User(text))
                    inputMessage = ""
                    // Assistant response simulation
                    messages.add(
                        OnboardingChatMessage.Assistant(
                            text = "Consultando el manual de taller local y telemetría DTC para \"$text\"...",
                            hasEngineCard = false,
                        )
                    )
                }
            },
            modifier = Modifier.padding(horizontal = ZoraTokens.SpacingMd, vertical = 4.dp),
        )
    }
}

@Preview
@Composable
private fun OnboardingChatScreenPreview() {
    ZoraTheme {
        OnboardingChatScreen(
            initialSymptom = "¿Por qué hace ruido el motor al acelerar?",
            onBack = {},
        )
    }
}

