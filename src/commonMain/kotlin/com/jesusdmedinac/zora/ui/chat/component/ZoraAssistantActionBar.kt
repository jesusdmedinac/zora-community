package com.jesusdmedinac.zora.ui.chat.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.Res
import zora_community.generated.resources.cd_audio_response
import zora_community.generated.resources.cd_copy_response
import zora_community.generated.resources.cd_dislike_response
import zora_community.generated.resources.cd_like_response
import zora_community.generated.resources.cd_more_actions
import zora_community.generated.resources.cd_retry_response
import zora_community.generated.resources.cd_share_response

/**
 * Node ekGY6 / iqjWy / WIZZ0: Assistant micro-action bar (copy, audio, thumbs-up, thumbs-down, share, more).
 * Conforms 100% to design/zora-community.pen specification:
 * 32x32dp circular buttons with 15x15dp vector icons and 10dp gap.
 */
@Composable
fun ZoraAssistantActionBar(
    modifier: Modifier = Modifier,
    onCopy: () -> Unit = {},
    onAudio: () -> Unit = {},
    onLike: () -> Unit = {},
    onDislike: () -> Unit = {},
    onShare: () -> Unit = {},
    onMore: () -> Unit = {},
    onRetry: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ActionButton(
            icon = ZoraTheme.icons.copy,
            contentDescription = stringResource(Res.string.cd_copy_response),
            onClick = onCopy,
        )
        ActionButton(
            icon = ZoraTheme.icons.audio,
            contentDescription = stringResource(Res.string.cd_audio_response),
            onClick = onAudio,
        )
        ActionButton(
            icon = ZoraTheme.icons.thumbsUp,
            contentDescription = stringResource(Res.string.cd_like_response),
            onClick = onLike,
        )
        ActionButton(
            icon = ZoraTheme.icons.thumbsDown,
            contentDescription = stringResource(Res.string.cd_dislike_response),
            onClick = onDislike,
        )
        ActionButton(
            icon = ZoraTheme.icons.share,
            contentDescription = stringResource(Res.string.cd_share_response),
            onClick = onShare,
        )
        ActionButton(
            icon = ZoraTheme.icons.more,
            contentDescription = stringResource(Res.string.cd_more_actions),
            onClick = onMore,
        )
    }
}

@Preview
@Composable
private fun ZoraAssistantActionBarM3Preview() {
    ZoraTheme(platformVariant = ZoraPlatformVariant.MATERIAL3) {
        ZoraAssistantActionBar()
    }
}

@Preview
@Composable
private fun ZoraAssistantActionBarHIGPreview() {
    ZoraTheme(platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
        ZoraAssistantActionBar()
    }
}


