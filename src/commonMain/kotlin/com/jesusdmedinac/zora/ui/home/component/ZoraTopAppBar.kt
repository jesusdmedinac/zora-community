package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.Res
import zora_community.generated.resources.app_name
import zora_community.generated.resources.badge_local_first
import zora_community.generated.resources.cd_open_drawer
import zora_community.generated.resources.cd_open_garage
import zora_community.generated.resources.chat_top_bar_title

/**
 * Top App Bar for Zora Copilot Home (Screen / M3 - 01 Copilot Home & HIG - 01).
 * Features platform-adaptive vector iconography from [ZoraTheme.icons] and dimensions for Material 3 and Apple HIG.
 */
@Composable
fun ZoraTopAppBar(
    onOpenDrawer: () -> Unit,
    onGarageClick: () -> Unit,
    modifier: Modifier = Modifier,
    statusBadge: String = stringResource(Res.string.badge_local_first),
) {
    val platform = ZoraTheme.platform

    val barHeight = when (platform) {
        ZoraPlatformVariant.HUMAN_INTERFACE -> 52.dp
        ZoraPlatformVariant.MATERIAL3 -> 64.dp
    }
    val buttonSize = when (platform) {
        ZoraPlatformVariant.HUMAN_INTERFACE -> 34.dp
        ZoraPlatformVariant.MATERIAL3 -> 40.dp
    }
    val iconSize = when (platform) {
        ZoraPlatformVariant.HUMAN_INTERFACE -> 18.dp
        ZoraPlatformVariant.MATERIAL3 -> 20.dp
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(barHeight)
            .padding(horizontal = ZoraTokens.SpacingMd),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Left: Drawer Menu Button
        Box(
            modifier = Modifier
                .size(buttonSize)
                .clip(CircleShape)
                .background(ZoraTokens.SurfaceContainerHigh)
                .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
                .clickable(onClick = onOpenDrawer),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(ZoraTheme.icons.menu),
                contentDescription = stringResource(Res.string.cd_open_drawer),
                tint = ZoraTokens.OnSurface,
                modifier = Modifier.size(iconSize),
            )
        }

        // Center: Vehicle & Local-First Status Badge
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.app_name),
                fontFamily = ZoraTokens.FontHeadline,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = ZoraTokens.OnSurface,
                letterSpacing = 0.5.sp,
            )
            Spacer(
                modifier = Modifier.width(8.dp),
                )
            Text(
                text = statusBadge,
                fontFamily = ZoraTokens.FontMono,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = ZoraTokens.StatusSuccess,
            )
            Icon(
                painter = painterResource(ZoraTheme.icons.dropdown),
                contentDescription = null,
                tint = ZoraTokens.OnSurface,
                modifier = Modifier.size(12.dp),
            )
        }

        // Right: Garage Vault Button
        Box(
            modifier = Modifier
                .size(buttonSize)
                .clip(CircleShape)
                .background(ZoraTokens.SurfaceContainerHigh)
                .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
                .clickable(onClick = onGarageClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(ZoraTheme.icons.avatar),
                contentDescription = stringResource(Res.string.cd_open_garage),
                tint = ZoraTokens.OnSurface,
                modifier = Modifier.size(iconSize),
            )
        }
    }
}

@Preview
@Composable
private fun ZoraTopAppBarPreview() {
    ZoraTheme {
        ZoraTopAppBar(
            onOpenDrawer = {},
            onGarageClick = {},
        )
    }
}
