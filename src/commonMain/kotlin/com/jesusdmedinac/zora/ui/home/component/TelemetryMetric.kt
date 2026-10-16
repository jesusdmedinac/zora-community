package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Atomic metric tile displaying automotive telemetry values (voltage, DTCs, health).
 */
@Composable
fun TelemetryMetric(
    label: String,
    value: String,
    status: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(ZoraTokens.ShapeSm)
            .background(ZoraTokens.SurfaceContainerHigh)
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = label,
                fontFamily = ZoraTokens.FontBody,
                fontSize = 11.sp,
                color = ZoraTokens.OnSurfaceVariant,
            )
            Text(
                text = value,
                fontFamily = ZoraTokens.FontMono,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = ZoraTokens.OnSurface,
            )
            Text(
                text = status,
                fontFamily = ZoraTokens.FontMono,
                fontSize = 10.sp,
                color = ZoraTokens.Primary,
            )
        }
    }
}

@Preview
@Composable
private fun TelemetryMetricPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        TelemetryMetric(
            label = "Batería",
            value = "12.6 V",
            status = "Normal",
        )
    }
}

