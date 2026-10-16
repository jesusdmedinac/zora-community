package com.jesusdmedinac.zora.ui.splash.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.splash.model.TelemetryBootStage
import com.jesusdmedinac.zora.ui.theme.LocalZoraPlatform
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * CAN-bus Loader Block with telemetry ticker text and 135° ignition progress bar.
 */
@Composable
fun CanBusProgressLoader(
    progress: Float,
    stage: TelemetryBootStage,
    modifier: Modifier = Modifier
) {
    val platform = LocalZoraPlatform.current
    val tickerColor = if (platform == ZoraPlatformVariant.HUMAN_INTERFACE) {
        ZoraTokens.Secondary
    } else {
        ZoraTokens.Primary
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 350),
        label = "CanBusProgress"
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // 1. Progress Track (200 x 4 dp)
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(4.dp)
                .clip(CircleShape)
                .background(ZoraTokens.SurfaceContainerHighest)
        ) {
            // Active Bar with 135° Core Spectrum Gradient
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress)
                    .clip(CircleShape)
                    .background(ZoraTokens.CanBusIgnitionBrush)
            )
        }

        // 2. CAN-bus Telemetry Ticker Text
        Text(
            text = org.jetbrains.compose.resources.stringResource(stage.textRes),
            style = TextStyle(
                fontFamily = ZoraTokens.FontMono,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp,
                color = tickerColor,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview
@Composable
private fun CanBusProgressLoaderPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        CanBusProgressLoader(
            progress = 0.65f,
            stage = TelemetryBootStage.CAN_BUS_INIT,
        )
    }
}


