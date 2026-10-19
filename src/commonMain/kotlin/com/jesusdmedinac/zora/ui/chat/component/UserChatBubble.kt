package com.jesusdmedinac.zora.ui.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Chat bubble displaying driver queries aligned to the right.
 */
@Composable
fun UserChatBubble(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 48.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Box(
            modifier = Modifier
                .clip(ZoraTokens.ShapeLg)
                .background(ZoraTokens.PrimaryContainer)
                .padding(14.dp),
        ) {
            Text(
                text = text,
                fontFamily = ZoraTokens.FontBody,
                fontSize = 14.sp,
                color = ZoraTokens.OnPrimaryContainer,
            )
        }
    }
}

@Preview
@Composable
private fun UserChatBubblePreview() {
    ZoraTheme {
        UserChatBubble(text = "Siento que el pedal del freno vibra")
    }
}

