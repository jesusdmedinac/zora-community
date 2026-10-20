package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.jesusdmedinac.zora.ui.theme.ZoraIcons
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Atomic navigation row for drawer items with vector icon, label, and optional status badge.
 * Synchronized with nodes m2wOA, kzDef & HeQx3 from `zora-community.pen`.
 */
@Composable
fun DrawerMenuItem(
    icon: DrawableResource,
    label: String,
    badge: String? = null,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val contentColor = if (enabled) ZoraTokens.OnSurface else ZoraTokens.OnSurfaceVariant

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
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(20.dp),
                )
                Text(
                    text = label,
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = contentColor,
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

@Preview
@Composable
private fun DrawerMenuItemPreview() {
    ZoraTheme {
        DrawerMenuItem(
            icon = ZoraIcons.packageIcon,
            label = "Yonke virtual",
            badge = "Próximamente",
            onClick = {},
        )
    }
}
