package com.jesusdmedinac.zora.ui.chat.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Node ekGY6: Assistant micro-action bar (copy, like, retry).
 */
@Composable
fun ZoraAssistantActionBar(
    onCopy: () -> Unit = {},
    onLike: () -> Unit = {},
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ActionButton(icon = "📋", onClick = onCopy)
        ActionButton(icon = "👍", onClick = onLike)
        ActionButton(icon = "🔄", onClick = onRetry)
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun ZoraAssistantActionBarPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        ZoraAssistantActionBar()
    }
}

