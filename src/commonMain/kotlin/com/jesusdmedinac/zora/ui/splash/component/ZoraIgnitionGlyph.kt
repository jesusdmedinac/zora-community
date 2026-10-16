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
                    radius = canvasWidth * 0.65f
                )
            )

            // 2. Geometric Crystal Facets
            drawZoraGlyph(
                canvasSize = this.size,
                brush = ZoraTokens.CoreSpectrumBrush
            )
        }
    }
}

/**
 * Renders the 6 interlocking facets of the Zora geometric emblem scaled to canvasSize.
 */
private fun DrawScope.drawZoraGlyph(canvasSize: Size, brush: Brush) {
    val nominalWidth = 2360f
    val nominalHeight = 1180f
    val scaleX = (canvasSize.width * 0.85f) / nominalWidth
    val scaleY = (canvasSize.height * 0.85f * (nominalHeight / nominalWidth)) / nominalHeight
    val uniformScale = minOf(scaleX, scaleY)

    val offsetX = (canvasSize.width - (nominalWidth * uniformScale)) / 2f
    val offsetY = (canvasSize.height - (nominalHeight * uniformScale)) / 2f

    fun sx(x: Float): Float = (x * uniformScale) + offsetX
    fun sy(y: Float): Float = (y * uniformScale) + offsetY

    val path = Path().apply {
        // Facet 1: Right-side outer triangle
        moveTo(sx(1775.86f), sy(1171.72f))
        lineTo(sx(1775.86f), sy(595.86f))
        lineTo(sx(2351.72f), sy(595.86f))
        close()

        // Facet 2: Bottom-center inverted triangle
        moveTo(sx(1741.72f), sy(1165.86f))
        lineTo(sx(610.00f), sy(1165.86f))
        lineTo(sx(1175.86f), sy(600.00f))
        close()

        // Facet 3: Right-middle inner triangle
        moveTo(sx(1755.86f), sy(1151.72f))
        lineTo(sx(1200.00f), sy(595.86f))
        lineTo(sx(1755.86f), sy(595.86f))
        close()

        // Facet 4: Left-side outer triangle
        moveTo(sx(575.86f), sy(575.86f))
        lineTo(sx(0.00f), sy(575.86f))
        lineTo(sx(575.86f), sy(0.00f))
        close()

        // Facet 5: Left-middle inner triangle
        moveTo(sx(1151.72f), sy(575.86f))
        lineTo(sx(595.86f), sy(575.86f))
        lineTo(sx(595.86f), sy(20.00f))
        close()

        // Facet 6: Top-center triangle
        moveTo(sx(1739.72f), sy(7.86f))
        lineTo(sx(1175.86f), sy(571.72f))
        lineTo(sx(612.00f), sy(7.86f))
        close()
    }

    drawPath(
        path = path,
        brush = brush
    )
}
