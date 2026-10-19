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
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.stringResource
import zora_community.generated.resources.Res
import zora_community.generated.resources.fluid_coolant
import zora_community.generated.resources.fluid_engine_oil
import zora_community.generated.resources.last_calibration_recent
import zora_community.generated.resources.quick_checkin_title
import zora_community.generated.resources.telemetry_battery
import zora_community.generated.resources.telemetry_tire_pressure
import org.jetbrains.compose.ui.tooling.preview.Preview

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
                text = stringResource(Res.string.quick_checkin_title),
                fontFamily = ZoraTokens.FontBody,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = ZoraTokens.Primary,
            )
            Text(
                text = stringResource(Res.string.last_calibration_recent),
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
                TelemetryRow(
                    label = stringResource(Res.string.fluid_engine_oil),
                    value = telemetry.engineOil
                )
                TelemetryRow(
                    label = stringResource(Res.string.fluid_coolant),
                    value = telemetry.coolant
                )
                TelemetryRow(
                    label = stringResource(Res.string.telemetry_tire_pressure),
                    value = telemetry.tirePressure
                )
                TelemetryRow(
                    label = stringResource(Res.string.telemetry_battery),
                    value = telemetry.battery
                )
            }
        }
    }
}

@Preview
@Composable
private fun QuickHealthCheckInCardPreview() {
    ZoraTheme {
        QuickHealthCheckInCard(
            telemetry = TelemetryCheckInState(),
        )
    }
}
