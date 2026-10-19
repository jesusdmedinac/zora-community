package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.drawer_recent_chats

/**
 * Recent questions list inside the navigation drawer.
 */
@Composable
fun DrawerRecentChatsList(
    recentQuestions: List<String> = listOf(
        "¿Cuánto aire necesitan mis llantas?",
        "¿Qué es la bujía?",
        "¿Qué significa el Check Engine?",
    ),
    onRecentQuestionClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.drawer_recent_chats),
            fontFamily = ZoraTokens.FontBody,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = ZoraTokens.Outline,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
        )

        recentQuestions.forEach { question ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .clip(CircleShape)
                    .clickable { onRecentQuestionClick(question) }
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                Text(
                    text = question,
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 13.sp,
                    color = ZoraTokens.OnSurfaceVariant,
                    maxLines = 1,
                )
            }
        }
    }
}

@Preview
@Composable
private fun DrawerRecentChatsListPreview() {
    ZoraTheme {
        DrawerRecentChatsList(onRecentQuestionClick = {})
    }
}

