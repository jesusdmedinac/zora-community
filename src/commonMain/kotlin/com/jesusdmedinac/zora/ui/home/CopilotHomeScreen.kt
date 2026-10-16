package com.jesusdmedinac.zora.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jesusdmedinac.zora.ui.home.component.CopilotPromptHero
import com.jesusdmedinac.zora.ui.home.component.VehicleStatusTelemetryCard
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
                VehicleStatusTelemetryCard()

                Spacer(modifier = Modifier.height(24.dp))

                // Diagnostic Prompt Invitation Hero
                CopilotPromptHero()
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

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun CopilotHomeScreenPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        CopilotHomeScreen(
            onNavigateToChat = {},
            onNavigateToGarage = {},
        )
    }
}

