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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.Res
import zora_community.generated.resources.cd_add_attachment
import zora_community.generated.resources.cd_dictation
import zora_community.generated.resources.cd_live_voice
import zora_community.generated.resources.cd_send_message
import zora_community.generated.resources.prompt_placeholder

/**
 * Floating prompt composer with input field, audio dictation, attachment trigger,
 * and adaptive live voice / send action button.
 */
@Composable
fun ZoraPromptComposer(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: (String) -> Unit,
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit = {},
    onMicClick: () -> Unit = {},
    onVoiceModeClick: () -> Unit = {},
    placeholder: String = stringResource(Res.string.prompt_placeholder),
) {
    val platform = ZoraTheme.platform
    val hasText = value.isNotEmpty()

    val containerColor = when (platform) {
        ZoraPlatformVariant.HUMAN_INTERFACE -> Color(0x14FFFFFF)
        ZoraPlatformVariant.MATERIAL3 -> ZoraTokens.SurfaceContainerHigh
    }
    val containerBorderColor = when (platform) {
        ZoraPlatformVariant.HUMAN_INTERFACE -> Color(0x33FFFFFF)
        ZoraPlatformVariant.MATERIAL3 -> ZoraTokens.OutlineVariant
    }
    val containerBorderWidth = when (platform) {
        ZoraPlatformVariant.HUMAN_INTERFACE -> 0.5.dp
        ZoraPlatformVariant.MATERIAL3 -> 1.dp
    }

    val addButtonColor = when (platform) {
        ZoraPlatformVariant.HUMAN_INTERFACE -> Color(0x12FFFFFF)
        ZoraPlatformVariant.MATERIAL3 -> ZoraTokens.SurfaceContainerLow
    }
    val addModifier = when (platform) {
        ZoraPlatformVariant.HUMAN_INTERFACE -> Modifier.border(0.5.dp, Color(0x1AFFFFFF), CircleShape)
        ZoraPlatformVariant.MATERIAL3 -> Modifier
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(CircleShape)
            .background(containerColor)
            .border(containerBorderWidth, containerBorderColor, CircleShape)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // 1. Add / Attachment Button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(addButtonColor)
                    .then(addModifier)
                    .clickable(onClick = onAddClick),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(ZoraTheme.icons.add),
                    contentDescription = stringResource(Res.string.cd_add_attachment),
                    tint = ZoraTokens.OnSurfaceVariant,
                    modifier = Modifier.size(20.dp),
                )
            }

            // 2. Text Input Field
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 14.sp,
                        color = ZoraTokens.OnSurfaceVariant,
                        maxLines = 1,
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        color = ZoraTokens.OnSurface,
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 14.sp,
                    ),
                    cursorBrush = SolidColor(ZoraTokens.Primary),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )
            }

            // 3. Audio Dictation (Mic) Button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onMicClick),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(ZoraTheme.icons.mic),
                    contentDescription = stringResource(Res.string.cd_dictation),
                    tint = ZoraTokens.OnSurfaceVariant,
                    modifier = Modifier.size(20.dp),
                )
            }

            // 4. Live Voice / Send Action Button
            // Dynamic swap: when text is entered, switches to Send; otherwise Live Voice.
            // When text is present, live voice mode cannot be triggered.
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(ZoraTokens.Primary)
                    .clickable {
                        if (hasText) {
                            onSend(value)
                        } else {
                            onVoiceModeClick()
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                if (hasText) {
                    Icon(
                        painter = painterResource(ZoraTheme.icons.send),
                        contentDescription = stringResource(Res.string.cd_send_message),
                        tint = ZoraTokens.OnPrimary,
                        modifier = Modifier.size(20.dp),
                    )
                } else {
                    Icon(
                        painter = painterResource(ZoraTheme.icons.voiceWaves),
                        contentDescription = stringResource(Res.string.cd_live_voice),
                        tint = ZoraTokens.OnPrimary,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ZoraPromptComposerEmptyPreview() {
    ZoraTheme {
        ZoraPromptComposer(
            value = "",
            onValueChange = {},
            onSend = {},
        )
    }
}

@Preview
@Composable
private fun ZoraPromptComposerTextPreview() {
    ZoraTheme {
        ZoraPromptComposer(
            value = "¿Por qué tiembla el volante a 80 km/h?",
            onValueChange = {},
            onSend = {},
        )
    }
}

@Preview
@Composable
private fun ZoraPromptComposerHIGPreview() {
    ZoraTheme(platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
        ZoraPromptComposer(
            value = "",
            onValueChange = {},
            onSend = {},
        )
    }
}
