package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
 * Pinned user profile footer inside navigation drawer with avatar, user info, and settings button.
 * Synchronized with nodes cBBbL, gqvG4, I4lp1B & D0S8v from `zora-community.pen`.
 */
@Composable
fun DrawerUserFooter(
    userName: String = "Jesús Medina",
    accountTier: String = stringResource(Res.string.tier_pro),
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        // Top Shader Fade Strip (node gqvG4)
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
                    )
                )
        )

        // Footer Content Bar (node I4lp1B)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .background(ZoraTokens.Canvas)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                // Footer User Avatar (node KU8PM)
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

                // User Text Column (node dVnn3)
                Column(
                    verticalArrangement = Arrangement.Center,
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

            // Settings Button (node D0S8v)
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onSettingsClick),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(ZoraIcons.settings),
                    contentDescription = stringResource(Res.string.drawer_menu_settings),
                    tint = ZoraTokens.OnSurfaceVariant,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun DrawerUserFooterPreview() {
    ZoraTheme {
        DrawerUserFooter(onSettingsClick = {})
    }
}
