package com.jesusdmedinac.zora.ui.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens

/**
 * Chat bubble displaying AI assistant responses with dark surface border.
 */
@Composable
fun AssistantChatBubble(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(ZoraTokens.ShapeLg)
            .background(ZoraTokens.SurfaceContainer)
            .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeLg)
            .padding(14.dp),
    ) {
        Text(
            text = text,
            fontFamily = ZoraTokens.FontBody,
            fontSize = 14.sp,
            color = ZoraTokens.OnSurface,
            lineHeight = 20.sp,
        )
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun AssistantChatBubblePreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        AssistantChatBubble(
            text = "He detectado tu vehículo como Honda Civic 2018 mediante CAN-Bus. Diagnóstico verificado.",
        )
    }
}

