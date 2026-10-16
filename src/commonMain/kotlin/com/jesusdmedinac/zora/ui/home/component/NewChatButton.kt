package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.action_new_chat

/**
 * Action button to initiate a new diagnostic conversation in the drawer.
 */
@Composable
fun NewChatButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(CircleShape)
            .background(ZoraTokens.SurfaceContainerHigh)
            .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = "+",
                color = ZoraTokens.Primary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.action_new_chat),
                fontFamily = ZoraTokens.FontBody,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = ZoraTokens.OnSurface,
            )
        }
    }
}

@Preview
@Composable
private fun NewChatButtonPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        NewChatButton(onClick = {})
    }
}

