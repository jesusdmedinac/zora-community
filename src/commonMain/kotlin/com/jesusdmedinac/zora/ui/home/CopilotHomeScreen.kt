package com.jesusdmedinac.zora.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import com.jesusdmedinac.zora.ui.chat.component.ZoraToastHost
import com.jesusdmedinac.zora.ui.chat.component.ZoraToastState
import com.jesusdmedinac.zora.ui.chat.component.rememberZoraToastState
import com.jesusdmedinac.zora.ui.home.ZoraShiftedDrawerViewModel
import com.jesusdmedinac.zora.ui.home.component.CopilotChatList
import com.jesusdmedinac.zora.ui.home.component.CopilotPromptHero
import com.jesusdmedinac.zora.ui.home.component.ZoraPromptComposer
import com.jesusdmedinac.zora.ui.home.component.ZoraShiftedDrawerLayout
import com.jesusdmedinac.zora.ui.home.component.ZoraSuggestionsRow
import com.jesusdmedinac.zora.ui.home.component.ZoraTopAppBar
import com.jesusdmedinac.zora.ui.home.model.CopilotChatMessage
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeIntent
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeSideEffect
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeUiState
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerIntent
import com.jesusdmedinac.zora.ui.home.sdui.ZoraSduiTemplates
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import com.jesusdmedinac.zora.ui.theme.zoraEdgePadding
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import zora_community.generated.resources.Res
import zora_community.generated.resources.badge_local_first
import zora_community.generated.resources.toast_text_copied

/**
 * Screen / M3 - 01 Copilot Home and HIG - 01 Copilot Home.
 * Main hub for automotive telemetry, vehicle status, and local-first diagnostics.
 * Stateful container connecting to [CopilotHomeViewModel] via Orbit MVI.
 */
@Composable
fun CopilotHomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToChat: (symptom: String?) -> Unit = {},
    onNavigateToGarage: () -> Unit = {},
    onNavigateToPaywall: (sourceReason: String?) -> Unit = {},
    viewModel: CopilotHomeViewModel = koinViewModel(),
    drawerViewModel: ZoraShiftedDrawerViewModel = koinViewModel(),
) {
    val uiState by viewModel.collectAsState()
    val toastState = rememberZoraToastState()
    val coroutineScope = rememberCoroutineScope()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is CopilotHomeSideEffect.NavigateToChat -> onNavigateToChat(sideEffect.initialPrompt)
            is CopilotHomeSideEffect.NavigateToGarage -> onNavigateToGarage()
            is CopilotHomeSideEffect.NavigateToPaywall -> onNavigateToPaywall(sideEffect.sourceReason)
            is CopilotHomeSideEffect.ShowSnackbar -> {
                coroutineScope.launch {
                    toastState.showToast(sideEffect.message)
                }
            }
            is CopilotHomeSideEffect.ScrollToBottom -> { /* Handled reactively by CopilotChatList */ }
        }
    }

    LaunchedEffect(uiState.isDrawerOpen) {
        if (uiState.isDrawerOpen) {
            drawerViewModel.processIntent(ZoraDrawerIntent.OpenDrawer)
        }
    }

    ZoraShiftedDrawerLayout(
        modifier = modifier,
        viewModel = drawerViewModel,
        onNavigateToNewChat = { viewModel.processIntent(CopilotHomeIntent.OpenNewChat) },
        onNavigateToParts = onNavigateToGarage,
        onNavigateToPaywall = { sourceReason -> onNavigateToPaywall(sourceReason) },
        onLoadRecentChat = { question ->
            viewModel.processIntent(CopilotHomeIntent.SelectRecentChat(question))
        },
    ) {
        CopilotHomeScreenContent(
            uiState = uiState,
            onIntent = viewModel::processIntent,
            onOpenDrawer = { drawerViewModel.processIntent(ZoraDrawerIntent.OpenDrawer) },
            toastState = toastState,
        )
    }
}

/**
 * Stateless content composable for Copilot Home.
 */
@Composable
fun CopilotHomeScreenContent(
    uiState: CopilotHomeUiState,
    onIntent: (CopilotHomeIntent) -> Unit,
    modifier: Modifier = Modifier,
    onOpenDrawer: () -> Unit = { onIntent(CopilotHomeIntent.SetDrawerOpen(true)) },
    toastState: ZoraToastState = rememberZoraToastState(),
) {
    val coroutineScope = rememberCoroutineScope()
    val textCopiedMessage = stringResource(Res.string.toast_text_copied)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ZoraTokens.Canvas)
            .zoraEdgePadding(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            // 1. Top App Bar
            ZoraTopAppBar(
                onOpenDrawer = onOpenDrawer,
                onGarageClick = { onIntent(CopilotHomeIntent.OpenGarage) },
                statusBadge = stringResource(Res.string.badge_local_first),
            )

                // 2. Central Conversational Area: Welcome Hero vs Active Chat Stream
                if (uiState.messages.isEmpty()) {
                    // Centered Welcome Hero (Zero-State / New Chat)
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = ZoraTokens.SpacingMd, vertical = ZoraTokens.SpacingMd),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CopilotPromptHero()
                    }

                    // Quick Suggestions Row (Zero-State)
                    ZoraSuggestionsRow(
                        onSuggestionClick = { symptom ->
                            onIntent(CopilotHomeIntent.SelectSuggestion(symptom))
                        },
                        modifier = Modifier.padding(bottom = 12.dp),
                    )
                } else {
                    // Active Conversational Chat Stream
                    CopilotChatList(
                        messages = uiState.messages,
                        onCopyMessage = {
                            coroutineScope.launch {
                                toastState.showToast(textCopiedMessage)
                            }
                        },
                        onCustomAction = { action ->
                            when (action.customType) {
                                ZoraSduiTemplates.ACTION_OPEN_PAYWALL -> {
                                    val source = action.params["source"]?.jsonPrimitive?.content
                                    onIntent(CopilotHomeIntent.OpenPaywall(source))
                                }
                                ZoraSduiTemplates.ACTION_CONFIRM_MEMORY -> {
                                    val memory = action.params["memory"]?.jsonPrimitive?.content ?: ""
                                    onIntent(CopilotHomeIntent.ConfirmGarageMemory(memory))
                                }
                                ZoraSduiTemplates.ACTION_DISMISS_MEMORY -> {
                                    val memory = action.params["memory"]?.jsonPrimitive?.content ?: ""
                                    onIntent(CopilotHomeIntent.DismissGarageMemory(memory))
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 8.dp),
                    )
                }

                // 3. Bottom Prompt Composer
                ZoraPromptComposer(
                    value = uiState.promptText,
                    onValueChange = { onIntent(CopilotHomeIntent.UpdatePromptText(it)) },
                    onSend = { text ->
                        onIntent(CopilotHomeIntent.SendPrompt(text))
                    },
                    modifier = Modifier.padding(horizontal = ZoraTokens.SpacingMd, vertical = 4.dp),
                )
            }

            // 4. Floating Toast Notification appearing from bottom
            ZoraToastHost(
                state = toastState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 76.dp),
            )
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

@Preview
@Composable
private fun CopilotHomeScreenActiveChatPreview() {
    ZoraTheme {
        CopilotHomeScreenContent(
            uiState = CopilotHomeUiState(
                messages = listOf(
                    CopilotChatMessage.User(text = "¿Por qué vibra el volante a alta velocidad?"),
                    CopilotChatMessage.Assistant(
                        text = "Para Honda Civic EX 2.0L: la vibración al frenar suele deberse a alabeo en los discos delanteros. Te sugiero revisar el grosor de los rotores.",
                    ),
                ),
            ),
            onIntent = {},
        )
    }
}

@Preview
@Composable
private fun CopilotHomeScreenToastPreview() {
    ZoraTheme(platformVariant = ZoraPlatformVariant.MATERIAL3) {
        CopilotHomeScreenContent(
            uiState = CopilotHomeUiState(
                messages = listOf(
                    CopilotChatMessage.User(text = "¿Por qué vibra el volante a alta velocidad?"),
                    CopilotChatMessage.Assistant(
                        text = "Para Honda Civic EX 2.0L: la vibración al frenar suele deberse a alabeo en los discos delanteros.",
                    ),
                ),
            ),
            onIntent = {},
            toastState = rememberZoraToastState(initialMessage = "Text copied"),
        )
    }
}
