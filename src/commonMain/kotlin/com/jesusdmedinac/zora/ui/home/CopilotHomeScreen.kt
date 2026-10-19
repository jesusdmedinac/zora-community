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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jesusdmedinac.zora.ui.home.component.CopilotPromptHero
import com.jesusdmedinac.zora.ui.home.component.VehicleStatusTelemetryCard
import com.jesusdmedinac.zora.ui.home.component.ZoraPromptComposer
import com.jesusdmedinac.zora.ui.home.component.ZoraShiftedDrawerLayout
import com.jesusdmedinac.zora.ui.home.component.ZoraSuggestionsRow
import com.jesusdmedinac.zora.ui.home.component.ZoraTopAppBar
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeIntent
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeSideEffect
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeUiState
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import com.jesusdmedinac.zora.ui.theme.zoraEdgePadding
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import zora_community.generated.resources.Res
import zora_community.generated.resources.badge_local_first

/**
 * Screen / M3 - 01 Copilot Home and HIG - 01 Copilot Home.
 * Main hub for automotive telemetry, vehicle status, and local-first diagnostics.
 * Stateful container connecting to [CopilotHomeViewModel] via Orbit MVI.
 */
@Composable
fun CopilotHomeScreen(
    onNavigateToChat: (symptom: String?) -> Unit,
    onNavigateToGarage: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CopilotHomeViewModel = viewModel { CopilotHomeViewModel() },
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is CopilotHomeSideEffect.NavigateToChat -> onNavigateToChat(sideEffect.initialPrompt)
            is CopilotHomeSideEffect.NavigateToGarage -> onNavigateToGarage()
            is CopilotHomeSideEffect.ShowSnackbar -> { /* Display toast/snackbar if needed */ }
        }
    }

    CopilotHomeScreenContent(
        uiState = uiState,
        onIntent = viewModel::processIntent,
        modifier = modifier,
    )
}

/**
 * Stateless content composable for Copilot Home.
 */
@Composable
fun CopilotHomeScreenContent(
    uiState: CopilotHomeUiState,
    onIntent: (CopilotHomeIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    ZoraShiftedDrawerLayout(
        drawerOpen = uiState.isDrawerOpen,
        onCloseDrawer = { onIntent(CopilotHomeIntent.SetDrawerOpen(false)) },
        onNewChatClick = { onIntent(CopilotHomeIntent.OpenNewChat) },
        onYonkeClick = { onIntent(CopilotHomeIntent.OpenGarage) },
        onRecentQuestionClick = { question -> onIntent(CopilotHomeIntent.SelectRecentChat(question)) },
        onSettingsClick = { onIntent(CopilotHomeIntent.SetDrawerOpen(false)) },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(ZoraTokens.Canvas)
                .zoraEdgePadding(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            // 1. Top App Bar
            ZoraTopAppBar(
                onOpenDrawer = { onIntent(CopilotHomeIntent.SetDrawerOpen(true)) },
                onGarageClick = { onIntent(CopilotHomeIntent.OpenGarage) },
                statusBadge = stringResource(Res.string.badge_local_first),
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
                VehicleStatusTelemetryCard(
                    vehicleName = uiState.activeVehicleName,
                    batteryVoltage = uiState.batteryVoltage,
                    dtcCount = uiState.dtcCount,
                    prescriptionStatus = uiState.prescriptionStatus,
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Diagnostic Prompt Invitation Hero
                CopilotPromptHero()
            }

            // 3. Quick Suggestions Row
            ZoraSuggestionsRow(
                onSuggestionClick = { symptom ->
                    onIntent(CopilotHomeIntent.SelectSuggestion(symptom))
                },
                modifier = Modifier.padding(bottom = 12.dp),
            )

            // 4. Bottom Prompt Composer
            ZoraPromptComposer(
                value = uiState.promptText,
                onValueChange = { onIntent(CopilotHomeIntent.UpdatePromptText(it)) },
                onSend = { text ->
                    onIntent(CopilotHomeIntent.SendPrompt(text))
                },
                modifier = Modifier.padding(horizontal = ZoraTokens.SpacingMd, vertical = 4.dp),
            )
        }
    }
}

@Preview
@Composable
private fun CopilotHomeScreenPreview() {
    ZoraTheme {
        CopilotHomeScreenContent(
            uiState = CopilotHomeUiState(),
            onIntent = {},
        )
    }
}
