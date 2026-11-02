package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.Res
import zora_community.generated.resources.drawer_recent_chats

/**
 * Default historical diagnostic questions presented in the shifted drawer.
 * Aligned with node fnMOF in `zora-community.pen`.
 */
fun defaultRecentQuestions(): List<String> = listOf(
    "¿Cuánto aire necesitan mis llantas?",
    "¿Qué es la bujía?",
    "¿Por qué rechinan mis frenos?",
    "¿Qué significa el Check Engine?",
)

/**
 * Atomic recent chat item inside navigation drawer with scale damping, selection tint and long click support.
 * Synchronized with nodes VoEUY, LBLta, gO0i8 & e2vDXb from `zora-community.pen`.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ZoraDrawerRecentChatItem(
    question: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onLongClick: (() -> Unit)? = null,
    selected: Boolean = false,
    isPinned: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.985f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
        label = "RecentChatItemScale",
    )

    val containerColor by animateColorAsState(
        targetValue = when {
            selected -> ZoraTokens.SurfaceContainerHigh
            isPressed -> ZoraTokens.SurfaceContainerHigh.copy(alpha = 0.5f)
            else -> Color.Transparent
        },
        animationSpec = tween(durationMillis = 150),
        label = "RecentChatItemContainer",
    )

    val contentColor by animateColorAsState(
        targetValue = when {
            selected -> ZoraTokens.Primary
            isPressed -> ZoraTokens.OnSurface
            else -> ZoraTokens.OnSurfaceVariant
        },
        animationSpec = tween(durationMillis = 150),
        label = "RecentChatItemColor",
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(36.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(containerColor)
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onLongClick = onLongClick,
                onClick = onClick,
            )
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = question,
            fontFamily = ZoraTokens.FontBody,
            fontSize = 13.sp,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f, fill = false),
        )
        if (isPinned) {
            Icon(
                painter = painterResource(ZoraIcons.pin),
                contentDescription = null,
                tint = ZoraTokens.Primary,
                modifier = Modifier.size(14.dp).padding(start = 4.dp),
            )
        }
    }
}

/**
 * Recent questions section inside navigation drawer.
 * Synchronized with node fnMOF from `zora-community.pen`.
 */
@Composable
fun ZoraDrawerRecentChatsList(
    onRecentQuestionClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    recentQuestions: List<String> = defaultRecentQuestions(),
    pinnedQuestions: List<String> = emptyList(),
    selectedQuestion: String? = null,
    onRecentQuestionLongClick: ((String) -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = stringResource(Res.string.drawer_recent_chats),
            fontFamily = ZoraTokens.FontBody,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = ZoraTokens.Outline,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
        )

        val sortedQuestions = remember(recentQuestions, pinnedQuestions) {
            val pinned = recentQuestions.filter { it in pinnedQuestions }
            val unpinned = recentQuestions.filterNot { it in pinnedQuestions }
            pinned + unpinned
        }

        sortedQuestions.forEach { question ->
            val isPinned = question in pinnedQuestions
            ZoraDrawerRecentChatItem(
                question = question,
                selected = question == selectedQuestion,
                isPinned = isPinned,
                onClick = { onRecentQuestionClick(question) },
                onLongClick = onRecentQuestionLongClick?.let { { it(question) } },
            )
        }
    }
}

/**
 * Backward compatibility alias for [ZoraDrawerRecentChatItem].
 */
@Composable
fun DrawerRecentChatItem(
    question: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) = ZoraDrawerRecentChatItem(
    question = question,
    onClick = onClick,
    modifier = modifier,
    selected = selected,
)

/**
 * Backward compatibility alias for [ZoraDrawerRecentChatsList].
 */
@Composable
fun DrawerRecentChatsList(
    onRecentQuestionClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    recentQuestions: List<String> = defaultRecentQuestions(),
    selectedQuestion: String? = null,
) = ZoraDrawerRecentChatsList(
    onRecentQuestionClick = onRecentQuestionClick,
    modifier = modifier,
    recentQuestions = recentQuestions,
    selectedQuestion = selectedQuestion,
)

@Preview
@Composable
private fun ZoraDrawerRecentChatsListPreview() {
    ZoraTheme {
        ZoraDrawerRecentChatsList(
            onRecentQuestionClick = {},
            selectedQuestion = "¿Qué es la bujía?",
        )
    }
}
