package com.jesusdmedinac.zora.ui.garage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.garage.model.TelemetryCheckInState
import com.jesusdmedinac.zora.ui.theme.ZoraTokens

/**
 * Rapid 30-second fluid and battery health check-in card.
 */
@Composable
fun QuickHealthCheckInCard(
    telemetry: TelemetryCheckInState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "QUICK HEALTH CHECK-IN (30S)",
                fontFamily = ZoraTokens.FontBody,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = ZoraTokens.Primary,
            )
            Text(
                text = "Última calibración: Reciente",
                fontFamily = ZoraTokens.FontMono,
                fontSize = 10.sp,
                color = ZoraTokens.OnSurfaceVariant,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(ZoraTokens.ShapeLg)
                .background(ZoraTokens.SurfaceContainer)
                .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeLg)
                .padding(14.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                TelemetryRow(label = "Engine Oil", value = telemetry.engineOil)
                TelemetryRow(label = "Coolant", value = telemetry.coolant)
                TelemetryRow(label = "Tire Pressure", value = telemetry.tirePressure)
                TelemetryRow(label = "Battery", value = telemetry.battery)
            }
        }
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun QuickHealthCheckInCardPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        QuickHealthCheckInCard(
            telemetry = TelemetryCheckInState(),
        )
    }
}
