package com.jesusdmedinac.zora.ui.theme

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ZoraPlatformVariant {
    MATERIAL3,
    HUMAN_INTERFACE
}

val LocalZoraPlatform = staticCompositionLocalOf { ZoraPlatformVariant.MATERIAL3 }

private val ZoraDarkColorScheme = darkColorScheme(
    primary = ZoraTokens.Primary,
    onPrimary = ZoraTokens.OnPrimary,
    primaryContainer = ZoraTokens.PrimaryContainer,
    onPrimaryContainer = ZoraTokens.OnPrimaryContainer,
    secondary = ZoraTokens.Secondary,
    onSecondary = ZoraTokens.OnSecondary,
    secondaryContainer = ZoraTokens.SecondaryContainer,
    tertiary = ZoraTokens.Tertiary,
    onTertiary = ZoraTokens.OnTertiary,
    tertiaryContainer = ZoraTokens.TertiaryContainer,
    background = ZoraTokens.Canvas,
    onBackground = ZoraTokens.OnSurface,
    surface = ZoraTokens.Surface,
    onSurface = ZoraTokens.OnSurface,
    surfaceVariant = ZoraTokens.SurfaceContainerHigh,
    onSurfaceVariant = ZoraTokens.OnSurfaceVariant,
    outline = ZoraTokens.Outline,
    outlineVariant = ZoraTokens.OutlineVariant
)

@Composable
fun ZoraTheme(
    platformVariant: ZoraPlatformVariant = ZoraPlatformVariant.MATERIAL3,
    content: @Composable () -> Unit
) {
    val iconSet = when (platformVariant) {
        ZoraPlatformVariant.MATERIAL3 -> ZoraM3Icons
        ZoraPlatformVariant.HUMAN_INTERFACE -> ZoraHIGIcons
    }

    CompositionLocalProvider(
        LocalZoraPlatform provides platformVariant,
        LocalZoraIcons provides iconSet,
    ) {
        MaterialTheme(
            colorScheme = ZoraDarkColorScheme,
            content = content
        )
    }
}

/**
 * Accessor object for Zora Theme tokens and icon systems.
 */
object ZoraTheme {
    val icons: ZoraIconSet
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current

    val platform: ZoraPlatformVariant
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraPlatform.current
}

/**
 * Computes adaptive top edge padding (Status Bar / Dynamic Island) combining
 * platform-specific fallback tokens with system [WindowInsets.safeDrawing].
 */
@Composable
fun zoraTopEdgePadding(): Dp {
    val platform = LocalZoraPlatform.current
    val systemTop = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding()
    val platformDefault = when (platform) {
        ZoraPlatformVariant.HUMAN_INTERFACE -> ZoraTokens.StatusBarHeightHIG
        ZoraPlatformVariant.MATERIAL3 -> ZoraTokens.StatusBarHeightM3
    }
    return maxOf(systemTop, platformDefault)
}

/**
 * Computes adaptive bottom edge padding (Navigation Bar / Home Indicator) combining
 * platform-specific fallback tokens with system [WindowInsets.safeDrawing].
 */
@Composable
fun zoraBottomEdgePadding(): Dp {
    val platform = LocalZoraPlatform.current
    val systemBottom = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding()
    val platformDefault = when (platform) {
        ZoraPlatformVariant.HUMAN_INTERFACE -> ZoraTokens.HomeIndicatorHeightHIG
        ZoraPlatformVariant.MATERIAL3 -> ZoraTokens.NavigationBarHeightM3
    }
    return maxOf(systemBottom, platformDefault)
}

/**
 * Modifier applying adaptive platform top and bottom edge padding.
 */
@Composable
fun Modifier.zoraEdgePadding(
    top: Boolean = true,
    bottom: Boolean = true,
): Modifier {
    val topPadding = if (top) zoraTopEdgePadding() else 0.dp
    val bottomPadding = if (bottom) zoraBottomEdgePadding() else 0.dp
    return this.padding(top = topPadding, bottom = bottomPadding)
}


@Preview
@Composable
private fun ZoraThemePreview() {
    ZoraTheme {
        Text(
            text = "Zora Precision Intelligence",
            color = ZoraTokens.Primary,
        )
    }
}

