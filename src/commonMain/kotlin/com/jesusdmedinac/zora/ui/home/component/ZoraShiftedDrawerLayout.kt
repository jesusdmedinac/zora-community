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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        // Mini Glyph
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(ZoraTokens.CoreSpectrumBrush),
                            contentAlignment = Alignment.Center,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )
                        }
                        Text(
                            text = "Zora",
                            fontFamily = ZoraTokens.FontHeadline,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = ZoraTokens.OnSurface,
                        )
                    }

                    // Close Drawer Button
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(ZoraTokens.SurfaceContainerLow)
                            .clickable(onClick = onCloseDrawer),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "✕",
                            color = ZoraTokens.OnSurface,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // New Chat Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .clip(CircleShape)
                        .background(ZoraTokens.SurfaceContainerHigh)
                        .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
                        .clickable(onClick = onNewChatClick)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text(
                            text = "+",
                            color = ZoraTokens.Primary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "Nueva conversación",
                            fontFamily = ZoraTokens.FontBody,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ZoraTokens.OnSurface,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Menu items
                DrawerMenuItem(
                    icon = "📦",
                    label = "Yonke virtual",
                    onClick = onYonkeClick,
                )

                Spacer(modifier = Modifier.height(6.dp))

                DrawerMenuItem(
                    icon = "🔧",
                    label = "Mecánico",
                    badge = "Próximamente",
                    enabled = false,
                )

                Spacer(modifier = Modifier.height(6.dp))

                DrawerMenuItem(
                    icon = "⛽",
                    label = "Gasolineras",
                    badge = "Próximamente",
                    enabled = false,
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Recent Chats Section
                Text(
                    text = "Recientes",
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ZoraTokens.Outline,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
                )

                val recentQuestions = listOf(
                    "¿Cuánto aire necesitan mis llantas?",
                    "¿Qué es la bujía?",
                    "¿Qué significa el Check Engine?",
                )

                recentQuestions.forEach { question ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .clip(CircleShape)
                            .clickable { onRecentQuestionClick(question) }
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        Text(
                            text = question,
                            fontFamily = ZoraTokens.FontBody,
                            fontSize = 13.sp,
                            color = ZoraTokens.OnSurfaceVariant,
                            maxLines = 1,
                        )
                    }
                }
            }

            // Pinned Footer (User Account & Settings)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ZoraTokens.SpacingMd)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        // User Avatar
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(ZoraTokens.PrimaryContainer),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "JM",
                                color = ZoraTokens.OnPrimaryContainer,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = "Jesús Medina",
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = ZoraTokens.OnSurface,
                            )
                            Text(
                                text = "Cuenta Pro",
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = ZoraTokens.Primary,
                            )
                        }
                    }

                    // Settings Icon Button
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .clickable(onClick = onSettingsClick),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "⚙️",
                            fontSize = 18.sp,
                        )
                    }
                }
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

@Composable
private fun DrawerMenuItem(
    icon: String,
    label: String,
    badge: String? = null,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(CircleShape)
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = icon,
                    fontSize = 16.sp,
                )
                Text(
                    text = label,
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (enabled) ZoraTokens.OnSurface else ZoraTokens.OnSurfaceVariant,
                )
            }

            if (badge != null) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(ZoraTokens.SurfaceContainerHigh)
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = badge,
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ZoraTokens.Primary,
                    )
                }
            }
        }
    }
}
