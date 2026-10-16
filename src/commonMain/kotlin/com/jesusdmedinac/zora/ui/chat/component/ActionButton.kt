package com.jesusdmedinac.zora.ui.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Micro-action button with circular icon indicator for chat interactions.
 */
@Composable
fun ActionButton(
    icon: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(ZoraTokens.SurfaceContainerHigh)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = icon, fontSize = 13.sp)
    }
}

@Preview
@Composable
private fun ActionButtonPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        ActionButton(icon = "📋", onClick = {})
    }
}
