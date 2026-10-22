package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraIcons
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.Res
import zora_community.generated.resources.recent_chat_action_delete
import zora_community.generated.resources.recent_chat_action_pin
import zora_community.generated.resources.recent_chat_action_rename
import zora_community.generated.resources.recent_chat_action_share
import zora_community.generated.resources.recent_chat_action_unpin
import zora_community.generated.resources.recent_chat_options_title

/**
 * Action row item inside [ZoraRecentChatOptionsBottomSheetContent].
 */
@Composable
fun ZoraRecentChatOptionRow(
    icon: DrawableResource,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDestructive: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.985f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
        label = "OptionRowScale",
    )

    val itemContentColor = if (isDestructive) {
        ZoraTokens.StatusError
    } else {
        ZoraTokens.OnSurface
    }

    val itemBackgroundColor by animateColorAsState(
        targetValue = when {
            isPressed && isDestructive -> ZoraTokens.StatusError.copy(alpha = 0.12f)
            isPressed -> ZoraTokens.SurfaceContainerHighest.copy(alpha = 0.6f)
            else -> Color.Transparent
        },
        animationSpec = tween(durationMillis = 150),
        label = "OptionRowBackground",
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .scale(scale)
            .clip(RoundedCornerShape(12.dp))
            .background(itemBackgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick,
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = itemContentColor,
            modifier = Modifier.size(20.dp),
        )

        Text(
            text = label,
            fontFamily = ZoraTokens.FontBody,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = itemContentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

/**
 * Stateless content presentation for recent chat options bottom sheet.
 * Synchronized with nodes L0nmlv & CRuGj in `zora-community.pen`.
 */
@Composable
fun ZoraRecentChatOptionsBottomSheetContent(
    question: String,
    onShareClick: () -> Unit,
    onPinClick: () -> Unit,
    onRenameClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPinned: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(ZoraTokens.SurfaceContainer, RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            .padding(top = 12.dp, bottom = 24.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Drag Handle (nodes SO1Mz & Or3vd)
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(4.dp)
                .clip(CircleShape)
                .background(ZoraTokens.OutlineVariant),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Header: Conversation summary
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            Text(
                text = stringResource(Res.string.recent_chat_options_title),
                fontFamily = ZoraTokens.FontHeadline,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = ZoraTokens.Outline,
                letterSpacing = 0.5.sp,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = question,
                fontFamily = ZoraTokens.FontBody,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = ZoraTokens.OnSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(ZoraTokens.OutlineVariant.copy(alpha = 0.5f)),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Actions List
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
        ) {
            // 1. Compartir conversación
            ZoraRecentChatOptionRow(
                icon = ZoraIcons.share,
                label = stringResource(Res.string.recent_chat_action_share),
                onClick = onShareClick,
            )

            // 2. Fijar / Desfijar
            ZoraRecentChatOptionRow(
                icon = ZoraIcons.pin,
                label = stringResource(
                    if (isPinned) Res.string.recent_chat_action_unpin else Res.string.recent_chat_action_pin
                ),
                onClick = onPinClick,
            )

            // 3. Cambiar nombre
            ZoraRecentChatOptionRow(
                icon = ZoraIcons.edit,
                label = stringResource(Res.string.recent_chat_action_rename),
                onClick = onRenameClick,
            )

            // 4. Borrar (Destructive)
            ZoraRecentChatOptionRow(
                icon = ZoraIcons.delete,
                label = stringResource(Res.string.recent_chat_action_delete),
                isDestructive = true,
                onClick = onDeleteClick,
            )
        }
    }
}

/**
 * Material 3 modal bottom sheet wrapper for recent chat options.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZoraRecentChatOptionsBottomSheet(
    question: String,
    onDismissRequest: () -> Unit,
    onShareClick: () -> Unit,
    onPinClick: () -> Unit,
    onRenameClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPinned: Boolean = false,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        containerColor = ZoraTokens.SurfaceContainer,
        contentColor = ZoraTokens.OnSurface,
        dragHandle = null,
    ) {
        ZoraRecentChatOptionsBottomSheetContent(
            question = question,
            isPinned = isPinned,
            onShareClick = onShareClick,
            onPinClick = onPinClick,
            onRenameClick = onRenameClick,
            onDeleteClick = onDeleteClick,
        )
    }
}

@Preview
@Composable
private fun ZoraRecentChatOptionsBottomSheetContentPreview() {
    ZoraTheme(platformVariant = ZoraPlatformVariant.MATERIAL3) {
        ZoraRecentChatOptionsBottomSheetContent(
            question = "¿Por qué rechinan mis frenos?",
            isPinned = false,
            onShareClick = {},
            onPinClick = {},
            onRenameClick = {},
            onDeleteClick = {},
        )
    }
}

@Preview
@Composable
private fun ZoraRecentChatOptionsBottomSheetContentHigPreview() {
    ZoraTheme(platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
        ZoraRecentChatOptionsBottomSheetContent(
            question = "¿Cuánto aire necesitan mis llantas?",
            isPinned = true,
            onShareClick = {},
            onPinClick = {},
            onRenameClick = {},
            onDeleteClick = {},
        )
    }
}
