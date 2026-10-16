package com.jesusdmedinac.zora.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

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
    CompositionLocalProvider(LocalZoraPlatform provides platformVariant) {
        MaterialTheme(
            colorScheme = ZoraDarkColorScheme,
            content = content
        )
    }
}
