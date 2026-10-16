package com.jesusdmedinac.zora.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.home.component.ZoraPromptComposer
import com.jesusdmedinac.zora.ui.home.component.ZoraShiftedDrawerLayout
import com.jesusdmedinac.zora.ui.home.component.ZoraSuggestionsRow
import com.jesusdmedinac.zora.ui.home.component.ZoraTopAppBar
import com.jesusdmedinac.zora.ui.theme.ZoraTokens

/**
 * Screen / M3 - 01 Copilot Home and HIG - 01 Copilot Home.
 * Main hub for automotive telemetry, vehicle status, and local-first diagnostics.
 */
@Composable
fun CopilotHomeScreen(
    onNavigateToChat: (symptom: String?) -> Unit,
    onNavigateToGarage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var drawerOpen by remember { mutableStateOf(false) }
    var promptText by remember { mutableStateOf("") }

    ZoraShiftedDrawerLayout(
        drawerOpen = drawerOpen,
        onCloseDrawer = { drawerOpen = false },
        onNewChatClick = {
            drawerOpen = false
            onNavigateToChat(null)
        },
        onYonkeClick = {
            drawerOpen = false
            onNavigateToGarage()
        },
        onRecentQuestionClick = { question ->
            drawerOpen = false
            onNavigateToChat(question)
        },
        onSettingsClick = {
            drawerOpen = false
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(ZoraTokens.Canvas)
                .padding(vertical = ZoraTokens.SpacingSm),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            // 1. Top App Bar
            ZoraTopAppBar(
                onOpenDrawer = { drawerOpen = true },
                onGarageClick = onNavigateToGarage,
                vehicleTitle = "HONDA CIVIC 2018",
                statusBadge = "LOCAL-FIRST",
            )

            // 2. Central Automotive Telemetry Card & Status
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = ZoraTokens.SpacingMd, vertical = ZoraTokens.SpacingMd),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                // Vehicle Status Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ZoraTokens.ShapeLg)
                        .background(ZoraTokens.SurfaceContainer)
                        .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeLg)
                        .padding(20.dp),
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column {
                                Text(
                                    text = "CAN-BUS CONECTADO",
                                    fontFamily = ZoraTokens.FontMono,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ZoraTokens.Primary,
                                    letterSpacing = 1.sp,
                                )
                                Text(
                                    text = "Honda Civic EX 2.0L",
                                    fontFamily = ZoraTokens.FontHeadline,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ZoraTokens.OnSurface,
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(ZoraTokens.StatusSuccess.copy(alpha = 0.15f))
                                    .border(1.dp, ZoraTokens.StatusSuccess, CircleShape),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(text = "✓", color = ZoraTokens.StatusSuccess, fontSize = 16.sp)
                            }
                        }

                        // Telemetry Grid
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            TelemetryMetric(label = "Batería", value = "12.6 V", status = "Normal")
                            TelemetryMetric(label = "DTC Activos", value = "0", status = "Limpio")
                            TelemetryMetric(label = "Prescripción", value = "Vault OK", status = "Seguro")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Diagnostic Prompt Invitation
                Text(
                    text = "¿Cómo te ayudo hoy con tu auto?",
                    fontFamily = ZoraTokens.FontHeadline,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ZoraTokens.OnSurface,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Respuestas mecánicas y diagnóstico local en tu dispositivo.",
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 13.sp,
                    color = ZoraTokens.OnSurfaceVariant,
                )
            }

            // 3. Quick Suggestions Row
            ZoraSuggestionsRow(
                onSuggestionClick = { symptom ->
                    onNavigateToChat(symptom)
                },
                modifier = Modifier.padding(bottom = 12.dp),
            )

            // 4. Bottom Prompt Composer
            ZoraPromptComposer(
                value = promptText,
                onValueChange = { promptText = it },
                onSend = { text ->
                    val symptom = text.takeIf { it.isNotBlank() }
                    promptText = ""
                    onNavigateToChat(symptom)
                },
                modifier = Modifier.padding(horizontal = ZoraTokens.SpacingMd, vertical = 4.dp),
            )
        }
    }
}

@Composable
private fun TelemetryMetric(
    label: String,
    value: String,
    status: String,
) {
    Column(
        modifier = Modifier
            .clip(ZoraTokens.ShapeMd)
            .background(ZoraTokens.SurfaceContainerHigh)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = label,
            fontFamily = ZoraTokens.FontBody,
            fontSize = 10.sp,
            color = ZoraTokens.OnSurfaceVariant,
        )
        Text(
            text = value,
            fontFamily = ZoraTokens.FontMono,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = ZoraTokens.OnSurface,
        )
        Text(
            text = status,
            fontFamily = ZoraTokens.FontBody,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = ZoraTokens.StatusSuccess,
        )
    }
}
