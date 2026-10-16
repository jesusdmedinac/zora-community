package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import zora_community.generated.resources.telemetry_battery
import zora_community.generated.resources.telemetry_can_bus_status
import zora_community.generated.resources.telemetry_dtc_active
import zora_community.generated.resources.telemetry_prescription
import zora_community.generated.resources.telemetry_status_clean
import zora_community.generated.resources.telemetry_status_normal
import zora_community.generated.resources.telemetry_status_secure
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Automotive CAN-Bus connection and telemetry summary card.
 */
@Composable
fun VehicleStatusTelemetryCard(
    vehicleName: String = "Honda Civic EX 2.0L",
    canBusStatus: String = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.telemetry_can_bus_status),
    batteryVoltage: String = "12.6 V",
    dtcCount: String = "0",
    prescriptionStatus: String = "Vault OK",
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(ZoraTokens.ShapeLg)
            .background(ZoraTokens.SurfaceContainer)
            .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeLg)
            .padding(20.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = canBusStatus,
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = ZoraTokens.Primary,
                        letterSpacing = 1.sp,
                    )
                    Text(
                        text = vehicleName,
                        fontFamily = ZoraTokens.FontHeadline,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = ZoraTokens.OnSurface,
                    )
                }
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(ZoraTokens.StatusSuccess.copy(alpha = 0.15f))
                        .border(1.dp, ZoraTokens.StatusSuccess, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "✓", color = ZoraTokens.StatusSuccess, fontSize = 16.sp)
                }
            }

            // Telemetry Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TelemetryMetric(
                    label = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.telemetry_battery),
                    value = batteryVoltage,
                    status = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.telemetry_status_normal),
                )
                TelemetryMetric(
                    label = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.telemetry_dtc_active),
                    value = dtcCount,
                    status = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.telemetry_status_clean),
                )
                TelemetryMetric(
                    label = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.telemetry_prescription),
                    value = prescriptionStatus,
                    status = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.telemetry_status_secure),
                )
            }
        }
    }
}

@Preview
@Composable
private fun VehicleStatusTelemetryCardPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        VehicleStatusTelemetryCard()
    }
}

