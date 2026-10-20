package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraIcons
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.Res
import zora_community.generated.resources.action_new_chat

/**
 * Action button to initiate a new diagnostic conversation in the drawer.
 * Synchronized with node ay6yS from `zora-community.pen`.
 */
@Composable
fun ZoraNewChatButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = stringResource(Res.string.action_new_chat),
    icon: DrawableResource = ZoraIcons.add,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.98f else 1.0f,
        animationSpec = tween(durationMillis = 100),
        label = "NewChatButtonScale",
    )

    val borderColor by animateColorAsState(
        targetValue = if (isPressed && enabled) {
            ZoraTokens.Primary.copy(alpha = 0.6f)
        } else {
            ZoraTokens.OutlineVariant
        },
        animationSpec = tween(durationMillis = 150),
        label = "NewChatButtonBorder",
    )

    val containerColor by animateColorAsState(
        targetValue = if (isPressed && enabled) {
            ZoraTokens.SurfaceContainerHighest
        } else {
            ZoraTokens.SurfaceContainerHigh
        },
        animationSpec = tween(durationMillis = 150),
        label = "NewChatButtonContainer",
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(containerColor)
            .border(1.dp, borderColor, CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick,
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = if (enabled) ZoraTokens.Primary else ZoraTokens.Outline,
                modifier = Modifier.size(18.dp),
            )
            Text(
                text = text,
                fontFamily = ZoraTokens.FontBody,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (enabled) ZoraTokens.OnSurface else ZoraTokens.Outline,
            )
        }
    }
}

/**
 * Backward compatibility alias for [ZoraNewChatButton].
 */
@Composable
fun NewChatButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = stringResource(Res.string.action_new_chat),
    icon: DrawableResource = ZoraIcons.add,
    enabled: Boolean = true,
) = ZoraNewChatButton(
    onClick = onClick,
    modifier = modifier,
    text = text,
    icon = icon,
    enabled = enabled,
)

@Preview
@Composable
private fun ZoraNewChatButtonPreview() {
    ZoraTheme {
        ZoraNewChatButton(onClick = {})
    }
}
