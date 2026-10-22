package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jesusdmedinac.zora.ui.theme.ZoraIcons
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import com.jesusdmedinac.zora.ui.theme.zoraEdgePadding
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.Res
import zora_community.generated.resources.badge_coming_soon
import zora_community.generated.resources.drawer_menu_gas_stations
import zora_community.generated.resources.drawer_menu_mechanic
import zora_community.generated.resources.drawer_menu_parts
import zora_community.generated.resources.tier_pro
import com.jesusdmedinac.zora.ui.home.ZoraShiftedDrawerViewModel
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerIntent
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerSideEffect
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

/**
 * Stateless content composable for [ZoraShiftedDrawerLayout].
 * Synchronized with nodes VeQvH & RktHH from `zora-community.pen`.
 */
@Composable
fun ZoraShiftedDrawerLayoutContent(
    drawerOpen: Boolean,
    onCloseDrawer: () -> Unit,
    onNewChatClick: () -> Unit,
    onYonkeClick: () -> Unit,
    onRecentQuestionClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    onMechanicClick: () -> Unit = {},
    onGasStationsClick: () -> Unit = {},
    selectedRecentQuestion: String? = null,
    userName: String = "Jesús Medina",
    accountTier: String = stringResource(Res.string.tier_pro),
    onProfileClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val drawerWidth = 280.dp
    val shiftOffset by animateDpAsState(
        targetValue = if (drawerOpen) drawerWidth else 0.dp,
        animationSpec = tween(durationMillis = 300),
        label = "DrawerShiftOffset",
    )
    val scrimAlpha by animateFloatAsState(
        targetValue = if (drawerOpen) 0.40f else 0f, // #00000066 is 40% alpha (0x66 = 102/255 ≈ 0.4)
        animationSpec = tween(durationMillis = 300),
        label = "DrawerScrimAlpha",
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ZoraTokens.Canvas)
    ) {
        // --- 1. Drawer Menu Content (280.dp) ---
        Column(
            modifier = Modifier
                .width(drawerWidth)
                .fillMaxHeight()
                .background(ZoraTokens.Canvas)
                .zoraEdgePadding(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ZoraTokens.SpacingMd)
            ) {
                // Header: Brand & Close Button (node Q0RPQ)
                ZoraDrawerHeader(onCloseDrawer = onCloseDrawer)

                Spacer(modifier = Modifier.height(16.dp))

                // New Chat Button (node ay6yS)
                ZoraNewChatButton(onClick = onNewChatClick)

                Spacer(modifier = Modifier.height(20.dp))

                // Menu items (nodes m2wOA, kzDef, HeQx3)
                ZoraDrawerMenuItem(
                    icon = ZoraIcons.packageIcon,
                    label = stringResource(Res.string.drawer_menu_parts),
                    onClick = onYonkeClick,
                )

                Spacer(modifier = Modifier.height(6.dp))

                ZoraDrawerMenuItem(
                    icon = ZoraIcons.wrench,
                    label = stringResource(Res.string.drawer_menu_mechanic),
                    badge = stringResource(Res.string.badge_coming_soon),
                    onClick = onMechanicClick,
                )

                Spacer(modifier = Modifier.height(6.dp))

                ZoraDrawerMenuItem(
                    icon = ZoraIcons.fuel,
                    label = stringResource(Res.string.drawer_menu_gas_stations),
                    badge = stringResource(Res.string.badge_coming_soon),
                    onClick = onGasStationsClick,
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Recent Chats Section (node fnMOF)
                ZoraDrawerRecentChatsList(
                    onRecentQuestionClick = onRecentQuestionClick,
                    selectedQuestion = selectedRecentQuestion,
                )
            }

            // Pinned Footer with Shader Fade Strip (node cBBbL)
            ZoraDrawerUserFooter(
                userName = userName,
                accountTier = accountTier,
                onSettingsClick = onSettingsClick,
                onProfileClick = onProfileClick,
            )
        }

        // --- 2. Shifted Screen Scrim Card (Node QZYQv) ---
        val cardShape = if (drawerOpen) {
            RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp, topEnd = 0.dp, bottomEnd = 0.dp)
        } else {
            RoundedCornerShape(0.dp)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = shiftOffset)
                .then(
                    if (drawerOpen) {
                        Modifier
                            .shadow(
                                elevation = 24.dp,
                                shape = cardShape,
                                clip = true,
                                ambientColor = Color(0x80000000),
                                spotColor = Color(0x80000000),
                            )
                            .border(1.dp, ZoraTokens.OutlineVariant, cardShape)
                    } else Modifier
                )
                .clip(cardShape)
                .background(ZoraTokens.SurfaceContainerLow)
        ) {
            // Main content
            content()

            // Scrim overlay (#00000066) active when drawer is open
            if (drawerOpen) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = scrimAlpha))
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = onCloseDrawer,
                        )
                )
            }
        }
    }
}

/**
 * Stateful overload for [ZoraShiftedDrawerLayout] connecting directly to [ZoraShiftedDrawerViewModel].
 */
@Composable
fun ZoraShiftedDrawerLayout(
    modifier: Modifier = Modifier,
    viewModel: ZoraShiftedDrawerViewModel = viewModel { ZoraShiftedDrawerViewModel() },
    onNavigateToNewChat: () -> Unit = {},
    onNavigateToParts: () -> Unit = {},
    onNavigateToPaywall: (sourceReason: String) -> Unit = {},
    onLoadRecentChat: (question: String) -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ZoraDrawerSideEffect.NavigateToNewChat -> onNavigateToNewChat()
            is ZoraDrawerSideEffect.NavigateToPartsFeed -> onNavigateToParts()
            is ZoraDrawerSideEffect.NavigateToPaywall -> onNavigateToPaywall(sideEffect.sourceReason)
            is ZoraDrawerSideEffect.LoadRecentChat -> onLoadRecentChat(sideEffect.question)
            is ZoraDrawerSideEffect.NavigateToSettings -> onNavigateToSettings()
            is ZoraDrawerSideEffect.NavigateToProfile -> onNavigateToProfile()
            is ZoraDrawerSideEffect.CloseDrawer -> { /* Handled in state */ }
        }
    }

    ZoraShiftedDrawerLayoutContent(
        drawerOpen = uiState.isOpen,
        onCloseDrawer = { viewModel.processIntent(ZoraDrawerIntent.CloseDrawer) },
        onNewChatClick = { viewModel.processIntent(ZoraDrawerIntent.ClickNewChat) },
        onYonkeClick = { viewModel.processIntent(ZoraDrawerIntent.ClickParts) },
        onMechanicClick = { viewModel.processIntent(ZoraDrawerIntent.ClickMechanic) },
        onGasStationsClick = { viewModel.processIntent(ZoraDrawerIntent.ClickGasStations) },
        onRecentQuestionClick = { question ->
            viewModel.processIntent(ZoraDrawerIntent.SelectRecentQuestion(question))
        },
        onSettingsClick = { viewModel.processIntent(ZoraDrawerIntent.ClickSettings) },
        modifier = modifier,
        selectedRecentQuestion = uiState.selectedRecentQuestion,
        userName = uiState.userName,
        accountTier = uiState.accountTier,
        onProfileClick = { viewModel.processIntent(ZoraDrawerIntent.ClickProfile) },
        content = content,
    )
}

@Preview
@Composable
private fun ZoraShiftedDrawerLayoutPreview() {
    ZoraTheme {
        ZoraShiftedDrawerLayoutContent(
            drawerOpen = true,
            onCloseDrawer = {},
            onNewChatClick = {},
            onYonkeClick = {},
            onRecentQuestionClick = {},
            onSettingsClick = {},
        ) {
            Text("Main Content")
        }
    }
}

@Preview
@Composable
private fun ZoraShiftedDrawerLayoutStatefulPreview() {
    ZoraTheme {
        ZoraShiftedDrawerLayout {
            Text("Main Content with ViewModel")
        }
    }
}
