package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraIcons
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.Res
import zora_community.generated.resources.drawer_menu_settings
import zora_community.generated.resources.tier_pro

/**
 * Pinned user profile footer inside navigation drawer with avatar, user info, and interactive settings button.
 * Synchronized with nodes cBBbL, gqvG4, I4lp1B, KU8PM, dVnn3 & D0S8v from `zora-community.pen`.
 */
@Composable
fun ZoraDrawerUserFooter(
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    userName: String = "Jesús Medina",
    accountTier: String = stringResource(Res.string.tier_pro),
    onProfileClick: (() -> Unit)? = null,
) {
    val settingsInteractionSource = remember { MutableInteractionSource() }
    val isSettingsPressed by settingsInteractionSource.collectIsPressedAsState()

    val settingsScale by animateFloatAsState(
        targetValue = if (isSettingsPressed) 0.92f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
        label = "SettingsButtonScale",
    )

    val settingsRotation by animateFloatAsState(
        targetValue = if (isSettingsPressed) 25f else 0f,
        animationSpec = tween(durationMillis = 150),
        label = "SettingsButtonRotation",
    )

    val settingsBackground by animateColorAsState(
        targetValue = if (isSettingsPressed) ZoraTokens.SurfaceContainerHigh else Color.Transparent,
        animationSpec = tween(durationMillis = 150),
        label = "SettingsButtonBackground",
    )

    val settingsIconTint by animateColorAsState(
        targetValue = if (isSettingsPressed) ZoraTokens.OnSurface else ZoraTokens.OnSurfaceVariant,
        animationSpec = tween(durationMillis = 150),
        label = "SettingsIconTint",
    )

    val profileInteractionSource = remember { MutableInteractionSource() }
    val isProfilePressed by profileInteractionSource.collectIsPressedAsState()

    val profileScale by animateFloatAsState(
        targetValue = if (isProfilePressed && onProfileClick != null) 0.98f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
        label = "ProfileGroupScale",
    )

    val profileBackground by animateColorAsState(
        targetValue = if (isProfilePressed && onProfileClick != null) {
            ZoraTokens.SurfaceContainerHigh.copy(alpha = 0.5f)
        } else {
            Color.Transparent
        },
        animationSpec = tween(durationMillis = 150),
        label = "ProfileGroupBackground",
    )

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        // 1. Top Shader Fade Strip (node gqvG4)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            ZoraTokens.Canvas.copy(alpha = 0f),
                            ZoraTokens.Canvas,
                        ),
                    ),
                ),
        )

        // 2. Footer Content Bar (node I4lp1B: padding [4, 16, 12, 16])
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .background(ZoraTokens.Canvas)
                .padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // User Info Group (node AcBFr)
            Row(
                modifier = Modifier
                    .scale(profileScale)
                    .clip(RoundedCornerShape(20.dp))
                    .background(profileBackground)
                    .then(
                        if (onProfileClick != null) {
                            Modifier.clickable(
                                interactionSource = profileInteractionSource,
                                indication = null,
                                role = Role.Button,
                                onClick = onProfileClick,
                            )
                        } else {
                            Modifier
                        },
                    )
                    .padding(vertical = 4.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                // Footer User Avatar (node KU8PM: 36x36, circle, stroke 0.5dp)
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(ZoraTokens.SurfaceContainerHigh)
                        .border(0.5.dp, ZoraTokens.OutlineVariant, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(ZoraIcons.avatar),
                        contentDescription = null,
                        tint = ZoraTokens.OnSurface,
                        modifier = Modifier.size(18.dp),
                    )
                }

                // User Text Column (node dVnn3: vertical gap 2)
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Text(
                        text = userName,
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ZoraTokens.OnSurface,
                        lineHeight = 18.sp,
                    )
                    Text(
                        text = accountTier,
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = ZoraTokens.Primary,
                        lineHeight = 14.sp,
                    )
                }
            }

            // Settings Button (node D0S8v: 36x36, circle)
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .scale(settingsScale)
                    .clip(CircleShape)
                    .background(settingsBackground)
                    .clickable(
                        interactionSource = settingsInteractionSource,
                        indication = null,
                        role = Role.Button,
                        onClick = onSettingsClick,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(ZoraIcons.settings),
                    contentDescription = stringResource(Res.string.drawer_menu_settings),
                    tint = settingsIconTint,
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(settingsRotation),
                )
            }
        }
    }
}

/**
 * Backward compatibility alias for [ZoraDrawerUserFooter].
 */
@Composable
fun DrawerUserFooter(
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    userName: String = "Jesús Medina",
    accountTier: String = stringResource(Res.string.tier_pro),
    onProfileClick: (() -> Unit)? = null,
) = ZoraDrawerUserFooter(
    userName = userName,
    accountTier = accountTier,
    onSettingsClick = onSettingsClick,
    modifier = modifier,
    onProfileClick = onProfileClick,
)

@Preview
@Composable
private fun ZoraDrawerUserFooterPreview() {
    ZoraTheme {
        ZoraDrawerUserFooter(
            onSettingsClick = {},
            onProfileClick = {},
        )
    }
}
