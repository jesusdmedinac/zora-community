package com.jesusdmedinac.zora.ui.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Chat bubble displaying driver queries aligned to the right.
 * Conforms 100% to the specification in design/zora-community.pen (node S9vmE / BBb2K).
 */
@Composable
fun UserChatBubble(
    text: String,
    modifier: Modifier = Modifier,
) {
    val platform = ZoraTheme.platform
    val fontSize = when (platform) {
        ZoraPlatformVariant.HUMAN_INTERFACE -> 15.sp
        ZoraPlatformVariant.MATERIAL3 -> 14.sp
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 48.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .background(ZoraTokens.SurfaceContainerHigh)
                .padding(horizontal = 16.dp, vertical = 10.dp),
        ) {
            Text(
                text = text,
                fontFamily = ZoraTokens.FontBody,
                fontSize = fontSize,
                lineHeight = 20.sp,
                color = ZoraTokens.OnSurface,
            )
        }
    }
}

@Preview
@Composable
private fun UserChatBubbleM3Preview() {
    ZoraTheme(platformVariant = ZoraPlatformVariant.MATERIAL3) {
        UserChatBubble(text = "Tengo un Honda Civic 2018")
    }
}

@Preview
@Composable
private fun UserChatBubbleHIGPreview() {
    ZoraTheme(platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
        UserChatBubble(text = "Tengo un Honda Civic 2018")
    }
}


