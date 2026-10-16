package com.jesusdmedinac.zora.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

/**
 * Design Tokens for Zora Automotive Intelligence (v2.21).
 * Directly synchronized with `zora-community.pen`.
 */
object ZoraTokens {
    // 1. Superficies y Lienzo Oscuro (Dark Surface Hierarchy)
    val Canvas = Color(0xFF0D0E11)
    val Surface = Color(0xFF12151C)
    val SurfaceContainerLow = Color(0xFF171A21)
    val SurfaceContainer = Color(0xFF1B1B1F)
    val SurfaceContainerHigh = Color(0xFF25262B)
    val SurfaceContainerHighest = Color(0xFF30323A)

    // 2. Acentos de Ignición y Marca (135° Core Spectrum)
    val Primary = Color(0xFFD1BCFF)          // M3 Violeta Eléctrico
    val PrimaryContainer = Color(0xFF894CFF)
    val OnPrimary = Color(0xFF3D008F)
    val OnPrimaryContainer = Color(0xFFEADDFF)
    val Secondary = Color(0xFFE1B6FF)        // HIG Lavender
    val SecondaryContainer = Color(0xFF9201E7)
    val OnSecondary = Color(0xFF450073)
    val Tertiary = Color(0xFFFFB0CB)
    val TertiaryContainer = Color(0xFFEC168A) // Acento deportivo y diagnóstico crítico
    val OnTertiary = Color(0xFF5D0033)

    // 3. Contraste de Texto y Contornos
    val OnSurface = Color(0xFFE3E2E6)
    val OnSurfaceVariant = Color(0xFFA3AAC4)
    val Outline = Color(0xFF958DA1)
    val OutlineVariant = Color(0xFF4A4455)

    // 4. Estados del Sistema
    val StatusSuccess = Color(0xFF10B981)
    val StatusWarning = Color(0xFFF59E0B)
    val StatusError = Color(0xFFEF4444)

    // 5. Gradientes Canónicos
    val CoreSpectrumBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF8A4DFF),
            Color(0xFFA732FD),
            Color(0xFFCD00EC),
            Color(0xFFE200C1),
            Color(0xFFED158A)
        )
    )

    val CanBusIgnitionBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF894CFF),
            Color(0xFFEC168A)
        )
    )

    // 6. Escala Geométrica de Radios
    val RadiusSm = 8.dp
    val RadiusMd = 12.dp
    val RadiusLg = 16.dp
    val RadiusDevice = 54.dp
    val RadiusDeviceProMax = 62.dp
    val RadiusFull = CircleShape

    val ShapeSm = RoundedCornerShape(RadiusSm)
    val ShapeMd = RoundedCornerShape(RadiusMd)
    val ShapeLg = RoundedCornerShape(RadiusLg)
    val ShapeDevice = RoundedCornerShape(RadiusDevice)
    val ShapeDeviceProMax = RoundedCornerShape(RadiusDeviceProMax)

    // 7. Espaciados
    val SpacingXs = 4.dp
    val SpacingSm = 8.dp
    val SpacingMd = 16.dp
    val SpacingLg = 24.dp
    val SpacingXl = 32.dp

    // 8. Tipografía de Autoridad Técnica
    val FontHeadline = FontFamily.Default // Mapeado a Manrope
    val FontBody = FontFamily.Default     // Mapeado a Inter
    val FontMono = FontFamily.Monospace   // Mapeado a JetBrains Mono
}
