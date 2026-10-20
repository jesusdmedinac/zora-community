package com.jesusdmedinac.zora.ui.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Micro-action button with circular surface container for chat interactions.
 * Conforms to the 32x32dp specification with 15x15dp vector iconography in design/zora-community.pen.
 */
@Composable
fun ActionButton(
    icon: DrawableResource,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(ZoraTokens.SurfaceContainerLow)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = contentDescription,
            tint = ZoraTokens.OnSurfaceVariant,
            modifier = Modifier.size(15.dp),
        )
    }
}

@Preview
@Composable
private fun ActionButtonPreview() {
    ZoraTheme {
        ActionButton(
            icon = ZoraTheme.icons.copy,
            contentDescription = "Copy",
            onClick = {},
        )
    }
}

