package com.jesusdmedinac.zora.ui.splash.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens

/**
 * Zora Precision Geometric Ignition Glyph.
 * Renders the 6-facet automotive ignition crystal with the 135° Core Spectrum gradient.
 */
@Composable
fun ZoraIgnitionGlyph(
    modifier: Modifier = Modifier,
    size: Dp = 96.dp,
    animated: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ZoraIgnitionPulse")
    
    val breathingScale by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = 0.96f,
            targetValue = 1.04f,
            animationSpec = infiniteRepeatable(
                animation = tween(1400, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "IgnitionScale"
        )
    } else {
        androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(1f) }
    }

    val glowAlpha by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = 0.25f,
            targetValue = 0.65f,
            animationSpec = infiniteRepeatable(
                animation = tween(1400, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "GlowAlpha"
        )
    } else {
        androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(0.4f) }
    }

    Box(
        modifier = modifier
            .size(size)
            .scale(breathingScale),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val canvasWidth = this.size.width
            val canvasHeight = this.size.height

            // 1. Ignition Ambient Halo Glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF8A4DFF).copy(alpha = glowAlpha * 0.45f),
                        Color(0xFFEC168A).copy(alpha = glowAlpha * 0.20f),
                        Color.Transparent
                    ),
                    center = Offset(canvasWidth / 2f, canvasHeight / 2f),
                    radius = minOf(canvasWidth, canvasHeight) * 0.65f
                )
            )

            // 2. Geometric Crystal Facets (Canonical 4-Triangle Square Isotype)
            drawZoraGlyph(
                canvasSize = this.size
            )
        }
    }
}

/**
 * Renders the 4 interlocking facets of the canonical Zora square emblem (ic_zora_transparent)
 * with the exact bounded 135° Core Spectrum gradient.
 *
 * Guarantees a strict 1:1 square aspect ratio centered within the given canvasSize.
 */
private fun DrawScope.drawZoraGlyph(canvasSize: Size) {
    val nominalDimension = 1160f
    val glyphSide = minOf(canvasSize.width, canvasSize.height) * 0.85f
    val scale = glyphSide / nominalDimension

    val offsetX = (canvasSize.width - glyphSide) / 2f
    val offsetY = (canvasSize.height - glyphSide) / 2f

    fun sx(x: Float): Float = (x * scale) + offsetX
    fun sy(y: Float): Float = (y * scale) + offsetY

    val path = Path().apply {
        // Triangle 1: Bottom inverted triangle (apex pointing up in center)
        moveTo(sx(1145.86f), sy(1160.00f))
        lineTo(sx(14.14f), sy(1160.00f))
        lineTo(sx(580.00f), sy(594.14f))
        close()

        // Triangle 2: Bottom-right flank triangle
        moveTo(sx(1160.00f), sy(1145.86f))
        lineTo(sx(604.14f), sy(590.00f))
        lineTo(sx(1160.00f), sy(590.00f))
        close()

        // Triangle 3: Top-left flank triangle
        moveTo(sx(555.86f), sy(570.00f))
        lineTo(sx(0.00f), sy(570.00f))
        lineTo(sx(0.00f), sy(14.14f))
        close()

        // Triangle 4: Top upright triangle (apex pointing down in center)
        moveTo(sx(1145.86f), sy(0.00f))
        lineTo(sx(580.00f), sy(565.86f))
        lineTo(sx(14.14f), sy(0.00f))
        close()
    }

    // Exact bounded 135° Core Spectrum gradient across the strictly square glyph box
    val boundedCoreSpectrum = Brush.linearGradient(
        colors = listOf(
            Color(0xFF8A4DFF),
            Color(0xFFA732FD),
            Color(0xFFCD00EC),
            Color(0xFFE200C1),
            Color(0xFFED158A)
        ),
        start = Offset(offsetX, offsetY),
        end = Offset(offsetX + glyphSide, offsetY + glyphSide)
    )

    drawPath(
        path = path,
        brush = boundedCoreSpectrum
    )
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun ZoraIgnitionGlyphPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        ZoraIgnitionGlyph()
    }
}

