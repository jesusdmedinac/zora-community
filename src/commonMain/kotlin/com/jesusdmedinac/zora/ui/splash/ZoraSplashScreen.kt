package com.jesusdmedinac.zora.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jesusdmedinac.zora.ui.splash.component.CanBusProgressLoader
import com.jesusdmedinac.zora.ui.splash.component.SecurityProtocolFooter
import com.jesusdmedinac.zora.ui.splash.component.ZoraBrandHeader
import com.jesusdmedinac.zora.ui.splash.component.ZoraIgnitionGlyph
import com.jesusdmedinac.zora.ui.splash.model.TelemetryBootStage
import com.jesusdmedinac.zora.ui.splash.model.TelemetryBootState
import com.jesusdmedinac.zora.ui.theme.LocalZoraPlatform
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

/**
 * Zora Automotive Ignition Splash Screen (Screens 00 - M3 & HIG).
 *
 * Implements the canonical precision automotive boot sequence:
 * 1. 135° Core Spectrum geometric crystal glyph with ambient pulse.
 * 2. High-contrast typography: "ZORA" wordmark + "PRECISION AUTOMOTIVE INTELLIGENCE" tagline.
 * 3. Animated CAN-bus telemetry loader cycling through zero-trust boot stages.
 * 4. Cryptographic local-first assurance footer.
 */
@Composable
fun ZoraSplashScreen(
    onIgnitionComplete: () -> Unit = {},
    durationMillis: Int = 2400,
    modifier: Modifier = Modifier
) {
    val platform = LocalZoraPlatform.current
    val topSystemPadding = if (platform == ZoraPlatformVariant.HUMAN_INTERFACE) 54.dp else 28.dp

    val progressAnimatable = remember { Animatable(0f) }
    var bootState by remember { mutableStateOf(TelemetryBootState()) }

    LaunchedEffect(Unit) {
        progressAnimatable.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(
                durationMillis = durationMillis,
                easing = FastOutSlowInEasing
            )
        ) {
            val currentStage = TelemetryBootStage.fromProgress(value)
            bootState = bootState.copy(
                progress = value,
                stage = currentStage,
                isComplete = value >= 1.0f
            )
        }

        // Brief delay upon reaching 100% before transition
        delay(150)
        onIgnitionComplete()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ZoraTokens.Canvas)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topSystemPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Center Ignition Container (Fills middle viewport, centered)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    // 1. Zora 135° Core Spectrum Geometric Crystal
                    ZoraIgnitionGlyph(
                        size = 96.dp,
                        animated = true
                    )

                    // 2. Brand Typography Block
                    ZoraBrandHeader()

                    // 3. CAN-bus Loader Block
                    CanBusProgressLoader(
                        progress = bootState.progress,
                        stage = bootState.stage
                    )
                }
            }

            // Bottom Security Protocol Footer
            SecurityProtocolFooter(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun ZoraSplashScreenPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        ZoraSplashScreen()
    }
}


