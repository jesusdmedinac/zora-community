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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import zora_community.generated.resources.badge_coming_soon
import zora_community.generated.resources.drawer_menu_gas_stations
import zora_community.generated.resources.drawer_menu_mechanic
import zora_community.generated.resources.drawer_menu_parts
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Navigation Drawer with Shifted Screen Scrim Card transition.
 * Synchronized with nodes VeQvH & RktHH from `zora-community.pen` v2.21.
 */
@Composable
fun ZoraShiftedDrawerLayout(
    drawerOpen: Boolean,
    onCloseDrawer: () -> Unit,
    onNewChatClick: () -> Unit,
    onYonkeClick: () -> Unit,
    onRecentQuestionClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
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
        modifier = Modifier
            .fillMaxSize()
            .background(ZoraTokens.Canvas)
    ) {
        // --- 1. Drawer Menu Content (280.dp) ---
        Column(
            modifier = Modifier
                .width(drawerWidth)
                .fillMaxHeight()
                .background(ZoraTokens.Canvas)
                .padding(vertical = ZoraTokens.SpacingMd),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ZoraTokens.SpacingMd)
            ) {
                // Header: Brand & Close Button
                DrawerHeader(onCloseDrawer = onCloseDrawer)

                Spacer(modifier = Modifier.height(16.dp))

                // New Chat Button
                NewChatButton(onClick = onNewChatClick)

                Spacer(modifier = Modifier.height(20.dp))

                // Menu items
                DrawerMenuItem(
                    icon = "📦",
                    label = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.drawer_menu_parts),
                    onClick = onYonkeClick,
                )

                Spacer(modifier = Modifier.height(6.dp))

                DrawerMenuItem(
                    icon = "🔧",
                    label = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.drawer_menu_mechanic),
                    badge = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.badge_coming_soon),
                    enabled = false,
                )

                Spacer(modifier = Modifier.height(6.dp))

                DrawerMenuItem(
                    icon = "⛽",
                    label = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.drawer_menu_gas_stations),
                    badge = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.badge_coming_soon),
                    enabled = false,
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Recent Chats Section
                DrawerRecentChatsList(
                    onRecentQuestionClick = onRecentQuestionClick,
                )
            }

            // Pinned Footer (User Account & Settings)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ZoraTokens.SpacingMd)
            ) {
                DrawerUserFooter(
                    onSettingsClick = onSettingsClick,
                )
            }
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

@Preview
@Composable
private fun ZoraShiftedDrawerLayoutPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        ZoraShiftedDrawerLayout(
            drawerOpen = true,
            onCloseDrawer = {},
            onNewChatClick = {},
            onYonkeClick = {},
            onRecentQuestionClick = {},
            onSettingsClick = {},
        ) {
            androidx.compose.material3.Text("Main Content")
        }
    }
}

