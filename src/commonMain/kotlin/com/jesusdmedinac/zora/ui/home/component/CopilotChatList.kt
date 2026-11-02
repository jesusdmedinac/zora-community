package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jesusdmedinac.jsontocompose.model.ComposeAction
import com.jesusdmedinac.zora.ui.chat.component.AssistantChatBubble
import com.jesusdmedinac.zora.ui.chat.component.UserChatBubble
import com.jesusdmedinac.zora.ui.chat.component.ZoraAssistantActionBar
import com.jesusdmedinac.zora.ui.home.model.CopilotChatMessage
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Scrollable list displaying the active conversational stream of user and copilot messages.
 */
@Composable
fun CopilotChatList(
    messages: List<CopilotChatMessage>,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onCopyMessage: ((String) -> Unit)? = null,
    onCustomAction: ((ComposeAction.Custom) -> Unit)? = null,
) {
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = listState,
        contentPadding = PaddingValues(horizontal = ZoraTokens.SpacingMd, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            items = messages,
            key = { it.id },
        ) { message ->
            when (message) {
                is CopilotChatMessage.User -> {
                    UserChatBubble(text = message.text)
                }

                is CopilotChatMessage.Assistant -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        AssistantChatBubble(
                            text = message.text,
                            dynamicDocument = message.dynamicDocument,
                            onCustomAction = onCustomAction,
                        )
                        ZoraAssistantActionBar(
                            messageText = message.text,
                            onCopy = { onCopyMessage?.invoke(message.text) },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CopilotChatListPreview() {
    ZoraTheme {
        CopilotChatList(
            messages = listOf(
                CopilotChatMessage.User(
                    text = "¿Por qué tiembla el volante a más de 80 km/h?",
                    id = "preview_user_1",
                ),
                CopilotChatMessage.Assistant(
                    text = "Para Honda Civic EX 2.0L: la vibración a alta velocidad comúnmente indica desbalanceo de neumáticos o ligera deformación en los rotores de freno delanteros.",
                    id = "preview_asst_1",
                ),
            ),
        )
    }
}
