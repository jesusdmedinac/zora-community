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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens

/**
 * Top App Bar for Zora Copilot Home (Screen / M3 - 01 Copilot Home & HIG - 01).
 * Features drawer menu trigger, vehicle status badge, and user avatar.
 */
@Composable
fun ZoraTopAppBar(
    onOpenDrawer: () -> Unit,
    onGarageClick: () -> Unit,
    vehicleTitle: String = "HONDA CIVIC 2018",
    statusBadge: String = "LOCAL-FIRST",
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = ZoraTokens.SpacingMd),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Left: Drawer Menu Button
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(ZoraTokens.SurfaceContainerHigh)
                .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
                .clickable(onClick = onOpenDrawer),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "☰",
                color = ZoraTokens.OnSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        // Center: Vehicle & Local-First Status Badge
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = vehicleTitle,
                fontFamily = ZoraTokens.FontHeadline,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = ZoraTokens.OnSurface,
                letterSpacing = 0.5.sp,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(ZoraTokens.StatusSuccess)
                )
                Text(
                    text = statusBadge,
                    fontFamily = ZoraTokens.FontMono,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ZoraTokens.Primary,
                )
            }
        }

        // Right: Garage Vault Button
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(ZoraTokens.SurfaceContainerHigh)
                .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
                .clickable(onClick = onGarageClick),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "🚗",
                fontSize = 16.sp,
            )
        }
    }
}
