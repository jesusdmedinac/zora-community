package com.jesusdmedinac.zora.ui.chat.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Micro-action button for chat interactions.
 * Conforms 100% to design/zora-community.pen specification:
 * - 32x32dp touch target with circular clip/ripple
 * - Transparent / invisible background
 * - Icon color matching AssistantChatBubble text color (ZoraTokens.OnSurface)
 */
@Composable
fun ActionButton(
    icon: DrawableResource,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = ZoraTokens.OnSurface,
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(15.dp),
        )
    }
}

@Preview
@Composable
private fun ActionButtonM3Preview() {
    ZoraTheme(platformVariant = ZoraPlatformVariant.MATERIAL3) {
        ActionButton(
            icon = ZoraTheme.icons.copy,
            contentDescription = "Copy",
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun ActionButtonHIGPreview() {
    ZoraTheme(platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
        ActionButton(
            icon = ZoraTheme.icons.copy,
            contentDescription = "Copy",
            onClick = {},
        )
    }
}
