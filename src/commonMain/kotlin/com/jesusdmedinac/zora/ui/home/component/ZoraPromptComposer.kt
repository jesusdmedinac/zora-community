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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Floating prompt composer with input field, audio trigger, and primary send action.
 */
@Composable
fun ZoraPromptComposer(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Pregúntale a Zora sobre tu auto...",
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(CircleShape)
            .background(ZoraTokens.SurfaceContainerHighest)
            .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // Audio input icon button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(ZoraTokens.SurfaceContainerHigh)
                    .clickable { /* Audio dictation trigger */ },
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "🎙️", fontSize = 16.sp)
            }

            // Text input field
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 14.sp,
                        color = ZoraTokens.OnSurfaceVariant,
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

            // Send action button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (value.isNotBlank()) ZoraTokens.Primary else ZoraTokens.SurfaceContainerHigh
                    )
                    .clickable(enabled = value.isNotBlank()) {
                        onSend(value)
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "➤",
                    color = if (value.isNotBlank()) ZoraTokens.OnPrimary else ZoraTokens.OnSurfaceVariant,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ZoraPromptComposerPreview() {
    ZoraTheme {
        ZoraPromptComposer(
            value = "¿Por qué tiembla el volante a 80 km/h?",
            onValueChange = {},
            onSend = {},
        )
    }
}

