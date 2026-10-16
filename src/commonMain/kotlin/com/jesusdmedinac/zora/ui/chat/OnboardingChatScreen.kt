package com.jesusdmedinac.zora.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.chat.component.CarePriorityItem
import com.jesusdmedinac.zora.ui.chat.component.ZoraAssistantActionBar
import com.jesusdmedinac.zora.ui.chat.component.ZoraCarePrioritiesCard
import com.jesusdmedinac.zora.ui.chat.component.ZoraEngineSelectionCard
import com.jesusdmedinac.zora.ui.chat.component.ZoraVisualReferenceProposalCard
import com.jesusdmedinac.zora.ui.chat.component.defaultCarePriorityItems
import com.jesusdmedinac.zora.ui.home.component.ZoraPromptComposer
import com.jesusdmedinac.zora.ui.theme.ZoraTokens

sealed interface ChatMessage {
    data class User(val text: String) : ChatMessage
    data class Assistant(
        val text: String,
        val hasEngineCard: Boolean = false,
        val hasCarePriorities: Boolean = false,
        val hasVisualProposal: Boolean = false,
    ) : ChatMessage
}

/**
 * Screen / M3 - 03 Onboarding Chat & HIG - 03.
 */
@Composable
fun OnboardingChatScreen(
    initialSymptom: String?,
    vehicleModel: String = "Honda Civic 2018",
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedEngine by remember { mutableStateOf("2.0L_NA") }
    var inputMessage by remember { mutableStateOf("") }

    val messages = remember {
        mutableStateListOf<ChatMessage>().apply {
            if (initialSymptom != null) {
                add(ChatMessage.User(initialSymptom))
            }
            add(
                ChatMessage.Assistant(
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
            .padding(vertical = ZoraTokens.SpacingSm),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        // 1. Top Header with Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = ZoraTokens.SpacingMd),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(ZoraTokens.SurfaceContainerHigh)
                    .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "←",
                    color = ZoraTokens.OnSurface,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = "DIAGNÓSTICO ZORA",
                    fontFamily = ZoraTokens.FontHeadline,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = ZoraTokens.OnSurface,
                    letterSpacing = 0.5.sp,
                )
                Text(
                    text = vehicleModel,
                    fontFamily = ZoraTokens.FontMono,
                    fontSize = 11.sp,
                    color = ZoraTokens.Primary,
                )
            }

            // Status Indicator
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(ZoraTokens.SurfaceContainerHigh),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(ZoraTokens.StatusSuccess)
                )
            }
        }

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
                    is ChatMessage.User -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 48.dp),
                            contentAlignment = Alignment.CenterEnd,
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(ZoraTokens.ShapeLg)
                                    .background(ZoraTokens.PrimaryContainer)
                                    .padding(14.dp),
                            ) {
                                Text(
                                    text = msg.text,
                                    fontFamily = ZoraTokens.FontBody,
                                    fontSize = 14.sp,
                                    color = ZoraTokens.OnPrimaryContainer,
                                )
                            }
                        }
                    }

                    is ChatMessage.Assistant -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(ZoraTokens.ShapeLg)
                                    .background(ZoraTokens.SurfaceContainer)
                                    .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeLg)
                                    .padding(14.dp),
                            ) {
                                Text(
                                    text = msg.text,
                                    fontFamily = ZoraTokens.FontBody,
                                    fontSize = 14.sp,
                                    color = ZoraTokens.OnSurface,
                                    lineHeight = 20.sp,
                                )
                            }

                            if (msg.hasEngineCard) {
                                ZoraEngineSelectionCard(
                                    selectedEngineId = selectedEngine,
                                    onSelectEngine = { selectedEngine = it },
                                    onConfirm = { engineId ->
                                        messages.add(
                                            ChatMessage.Assistant(
                                                text = "✓ Motor confirmado: $engineId. Diagnóstico local sincronizado con CAN-Bus y registrado en la bóveda mecánica.",
                                                hasEngineCard = false,
                                            )
                                        )
                                        messages.add(
                                            ChatMessage.Assistant(
                                                text = "Evaluando necesidades de mantenimiento y partes para tu vehículo:",
                                                hasCarePriorities = true,
                                            )
                                        )
                                        messages.add(
                                            ChatMessage.Assistant(
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
                                            ChatMessage.Assistant(
                                                text = "✓ Referencia visual generada para Garage Slot #01. Archivada en tu bóveda local.",
                                            )
                                        )
                                    },
                                    onSkip = {
                                        messages.add(
                                            ChatMessage.Assistant(
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
                    messages.add(ChatMessage.User(text))
                    inputMessage = ""
                    // Assistant response simulation
                    messages.add(
                        ChatMessage.Assistant(
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
