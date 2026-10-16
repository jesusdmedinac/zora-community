package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens

/**
 * Atomic navigation row for drawer items with icon, label, and optional status badge.
 */
@Composable
fun DrawerMenuItem(
    icon: String,
    label: String,
    badge: String? = null,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(CircleShape)
            .then(
                if (enabled) Modifier.clickable(onClick = onClick)
                else Modifier
            )
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
                    fontSize = 18.sp,
                )
                Text(
                    text = label,
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (enabled) ZoraTokens.OnSurface else ZoraTokens.Outline,
                )
            }

            if (badge != null) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(ZoraTokens.SurfaceContainerHighest)
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                ) {
                    Text(
                        text = badge,
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = ZoraTokens.Outline,
                    )
                }
            }
        }
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun DrawerMenuItemPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        DrawerMenuItem(
            icon = "📦",
            label = "Yonke virtual",
            badge = "Nuevo",
            onClick = {},
        )
    }
}

