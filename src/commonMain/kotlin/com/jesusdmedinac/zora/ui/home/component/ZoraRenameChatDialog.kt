package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.Res
import zora_community.generated.resources.recent_chat_rename_dialog_cancel
import zora_community.generated.resources.recent_chat_rename_dialog_confirm
import zora_community.generated.resources.recent_chat_rename_dialog_title

/**
 * Stateless dialog content for renaming a conversation.
 */
@Composable
fun ZoraRenameChatDialogContent(
    initialTitle: String,
    onConfirm: (newName: String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var titleText by remember(initialTitle) { mutableStateOf(initialTitle) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(ZoraTokens.SurfaceContainerHigh)
            .border(1.dp, ZoraTokens.OutlineVariant, RoundedCornerShape(24.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(Res.string.recent_chat_rename_dialog_title),
            fontFamily = ZoraTokens.FontHeadline,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = ZoraTokens.OnSurface,
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = titleText,
            onValueChange = { titleText = it },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = ZoraTokens.OnSurface,
                unfocusedTextColor = ZoraTokens.OnSurface,
                focusedBorderColor = ZoraTokens.Primary,
                unfocusedBorderColor = ZoraTokens.OutlineVariant,
                cursorColor = ZoraTokens.Primary,
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = ZoraTokens.OnSurfaceVariant),
            ) {
                Text(
                    text = stringResource(Res.string.recent_chat_rename_dialog_cancel),
                    fontWeight = FontWeight.Medium,
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            Button(
                onClick = { onConfirm(titleText) },
                enabled = titleText.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ZoraTokens.Primary,
                    contentColor = ZoraTokens.OnPrimary,
                ),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = stringResource(Res.string.recent_chat_rename_dialog_confirm),
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

/**
 * Stateful dialog wrapper for renaming a recent chat question.
 */
@Composable
fun ZoraRenameChatDialog(
    initialTitle: String,
    onConfirm: (newName: String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = onDismiss) {
        ZoraRenameChatDialogContent(
            initialTitle = initialTitle,
            onConfirm = onConfirm,
            onDismiss = onDismiss,
            modifier = modifier,
        )
    }
}

@Preview
@Composable
private fun ZoraRenameChatDialogContentPreview() {
    ZoraTheme(platformVariant = ZoraPlatformVariant.MATERIAL3) {
        ZoraRenameChatDialogContent(
            initialTitle = "¿Por qué rechinan mis frenos?",
            onConfirm = {},
            onDismiss = {},
        )
    }
}

@Preview
@Composable
private fun ZoraRenameChatDialogContentHigPreview() {
    ZoraTheme(platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
        ZoraRenameChatDialogContent(
            initialTitle = "¿Cuánto aire necesitan mis llantas?",
            onConfirm = {},
            onDismiss = {},
        )
    }
}
