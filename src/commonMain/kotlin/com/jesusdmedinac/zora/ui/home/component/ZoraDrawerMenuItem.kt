package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
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
 * Atomic navigation row for drawer items with vector icon, label, selection state, and status badge.
 * Synchronized with nodes m2wOA, kzDef & HeQx3 from `zora-community.pen`.
 */
@Composable
fun ZoraDrawerMenuItem(
    icon: DrawableResource,
    label: String,
    modifier: Modifier = Modifier,
    badge: String? = null,
    selected: Boolean = false,
    enabled: Boolean = true,
    contentColor: Color? = null,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.985f else 1.0f,
        animationSpec = tween(durationMillis = 100),
        label = "DrawerMenuItemScale",
    )

    val containerColor by animateColorAsState(
        targetValue = when {
            selected -> ZoraTokens.SurfaceContainerHigh
            isPressed && enabled -> ZoraTokens.SurfaceContainerHigh.copy(alpha = 0.5f)
            else -> Color.Transparent
        },
        animationSpec = tween(durationMillis = 150),
        label = "DrawerMenuItemContainer",
    )

    val resolvedContentColor = contentColor ?: when {
        !enabled -> ZoraTokens.OnSurfaceVariant
        selected -> ZoraTokens.Primary
        badge != null -> ZoraTokens.OnSurfaceVariant
        else -> ZoraTokens.OnSurface
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(containerColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick,
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
                    tint = resolvedContentColor,
                    modifier = Modifier.size(20.dp),
                )
                Text(
                    text = label,
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 14.sp,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                    color = resolvedContentColor,
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

/**
 * Backward compatibility alias for [ZoraDrawerMenuItem].
 */
@Composable
fun DrawerMenuItem(
    icon: DrawableResource,
    label: String,
    modifier: Modifier = Modifier,
    badge: String? = null,
    selected: Boolean = false,
    enabled: Boolean = true,
    contentColor: Color? = null,
    onClick: () -> Unit = {},
) = ZoraDrawerMenuItem(
    icon = icon,
    label = label,
    modifier = modifier,
    badge = badge,
    selected = selected,
    enabled = enabled,
    contentColor = contentColor,
    onClick = onClick,
)

@Preview
@Composable
private fun ZoraDrawerMenuItemPreview() {
    ZoraTheme {
        ZoraDrawerMenuItem(
            icon = ZoraIcons.packageIcon,
            label = "Yonke virtual",
            badge = "Próximamente",
            onClick = {},
        )
    }
}
