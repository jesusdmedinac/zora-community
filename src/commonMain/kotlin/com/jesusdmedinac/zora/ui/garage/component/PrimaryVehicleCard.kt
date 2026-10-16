package com.jesusdmedinac.zora.ui.garage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import zora_community.generated.resources.general_health_label
import zora_community.generated.resources.obd2_linked
import zora_community.generated.resources.odometer_label
import zora_community.generated.resources.primary_vehicle_label

/**
 * Card showcasing the driver's primary vehicle, OBD-II link, masked VIN, and general health.
 */
@Composable
fun PrimaryVehicleCard(
    vehicleName: String = "2018 Honda Civic EX",
    engineSummary: String = "2.0L 4-Cylinder · FWD Hatchback",
    maskedVin: String = "VIN: 2HGFC2F59JH02****",
    odometerText: String = "82,450 km",
    healthScoreText: String = "94% Normal",
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(ZoraTokens.ShapeLg)
            .background(ZoraTokens.SurfaceContainer)
            .border(1.dp, ZoraTokens.Primary, ZoraTokens.ShapeLg)
            .padding(16.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Primary Vehicle Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(ZoraTokens.PrimaryContainer)
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                ) {
                    Text(
                        text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.primary_vehicle_label),
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }

                // OBD-II Link Status
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(ZoraTokens.StatusSuccess.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                ) {
                    Text(
                        text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.obd2_linked),
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ZoraTokens.StatusSuccess,
                    )
                }
            }

            Text(
                text = vehicleName,
                fontFamily = ZoraTokens.FontHeadline,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = ZoraTokens.OnSurface,
            )

            Text(
                text = engineSummary,
                fontFamily = ZoraTokens.FontBody,
                fontSize = 13.sp,
                color = ZoraTokens.OnSurfaceVariant,
            )

            // Masked VIN
            Row(
                modifier = Modifier
                    .clip(ZoraTokens.ShapeSm)
                    .background(ZoraTokens.SurfaceContainerHigh)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = maskedVin,
                    fontFamily = ZoraTokens.FontMono,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = ZoraTokens.Primary,
                )
            }

            // Metrics Row: Odometer and Health
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.odometer_label),
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 11.sp,
                        color = ZoraTokens.OnSurfaceVariant,
                    )
                    Text(
                        text = odometerText,
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = ZoraTokens.OnSurface,
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.general_health_label),
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 11.sp,
                        color = ZoraTokens.OnSurfaceVariant,
                    )
                    Text(
                        text = healthScoreText,
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = ZoraTokens.StatusSuccess,
                    )
                }
            }
        }
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun PrimaryVehicleCardPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        PrimaryVehicleCard()
    }
}
