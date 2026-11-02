package com.jesusdmedinac.zora.ui.chat.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * State controller for displaying transient toasts in Zora screens.
 */
@Stable
class ZoraToastState(initialMessage: String? = null) {
    var message by mutableStateOf<String?>(initialMessage)
        private set

    val isVisible: Boolean
        get() = message != null

    suspend fun showToast(text: String, durationMs: Long = 2000L) {
        message = text
        delay(durationMs)
        message = null
    }

    fun showToastImmediately(text: String) {
        message = text
    }

    fun dismiss() {
        message = null
    }
}

/**
 * Creates and remembers a [ZoraToastState] across recompositions.
 */
@Composable
fun rememberZoraToastState(initialMessage: String? = null): ZoraToastState {
    return remember { ZoraToastState(initialMessage) }
}

/**
 * Animated toast container sliding in from the bottom with positive confirmation.
 */
@Composable
fun ZoraToastHost(
    state: ZoraToastState,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = state.isVisible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMediumLow,
            ),
        ) + fadeIn(animationSpec = tween(150)),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 200),
        ) + fadeOut(animationSpec = tween(150)),
        modifier = modifier,
    ) {
        state.message?.let { text ->
            ZoraToast(text = text)
        }
    }
}

/**
 * Floating Dark Titanium pill toast with checkmark icon and localized message.
 */
@Composable
fun ZoraToast(
    text: String,
    modifier: Modifier = Modifier,
    icon: DrawableResource = ZoraTheme.icons.check,
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = ZoraTokens.SurfaceContainerHighest,
        border = BorderStroke(1.dp, ZoraTokens.OutlineVariant),
        shadowElevation = 8.dp,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = ZoraTokens.Primary,
                modifier = Modifier.size(16.dp),
            )
            Text(
                text = text,
                fontFamily = ZoraTokens.FontBody,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = ZoraTokens.OnSurface,
            )
        }
    }
}

@Preview
@Composable
private fun ZoraToastM3Preview() {
    ZoraTheme(platformVariant = ZoraPlatformVariant.MATERIAL3) {
        ZoraToast(text = "Text copied")
    }
}

@Preview
@Composable
private fun ZoraToastHIGPreview() {
    ZoraTheme(platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
        ZoraToast(text = "Texto copiado")
    }
}
