package com.jesusdmedinac.zora.ui.chat.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.Res
import zora_community.generated.resources.cd_audio_response
import zora_community.generated.resources.cd_copied_response
import zora_community.generated.resources.cd_copy_response
import zora_community.generated.resources.cd_dislike_response
import zora_community.generated.resources.cd_like_response
import zora_community.generated.resources.cd_more_actions
import zora_community.generated.resources.cd_share_response

/**
 * Node ekGY6 / iqjWy / WIZZ0: Assistant micro-action bar (copy, audio, thumbs-up, thumbs-down, share, more).
 * Conforms 100% to design/zora-community.pen specification:
 * 32x32dp circular buttons with 15x15dp vector icons and 10dp gap.
 * Includes interactive clipboard copying with dynamic checkmark transition animation.
 */
@Composable
fun ZoraAssistantActionBar(
    modifier: Modifier = Modifier,
    messageText: String = "",
    onCopy: () -> Unit = {},
    onAudio: () -> Unit = {},
    onLike: () -> Unit = {},
    onDislike: () -> Unit = {},
    onShare: () -> Unit = {},
    onMore: () -> Unit = {},
) {
    @Suppress("DEPRECATION")
    val clipboardManager = LocalClipboardManager.current
    var isCopied by remember { mutableStateOf(false) }

    LaunchedEffect(isCopied) {
        if (isCopied) {
            delay(2000L)
            isCopied = false
        }
    }

    val handleCopy = {
        if (messageText.isNotEmpty()) {
            clipboardManager.setText(AnnotatedString(messageText))
        }
        isCopied = true
        onCopy()
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedContent(
            targetState = isCopied,
            transitionSpec = {
                (scaleIn(initialScale = 0.6f, animationSpec = tween(150)) + fadeIn(animationSpec = tween(150)))
                    .togetherWith(scaleOut(targetScale = 0.6f, animationSpec = tween(150)) + fadeOut(animationSpec = tween(150)))
            },
            label = "CopyCheckmarkTransition",
        ) { copied ->
            if (copied) {
                ActionButton(
                    icon = ZoraTheme.icons.check,
                    contentDescription = stringResource(Res.string.cd_copied_response),
                    onClick = handleCopy,
                    tint = ZoraTokens.Primary,
                )
            } else {
                ActionButton(
                    icon = ZoraTheme.icons.copy,
                    contentDescription = stringResource(Res.string.cd_copy_response),
                    onClick = handleCopy,
                    tint = ZoraTokens.OnSurface,
                )
            }
        }
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

@Preview
@Composable
private fun ZoraAssistantActionBarCopiedPreview() {
    ZoraTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ActionButton(
                icon = ZoraTheme.icons.check,
                contentDescription = "Copied",
                onClick = {},
                tint = ZoraTokens.Primary,
            )
            ActionButton(
                icon = ZoraTheme.icons.audio,
                contentDescription = "Audio",
                onClick = {},
            )
        }
    }
}
