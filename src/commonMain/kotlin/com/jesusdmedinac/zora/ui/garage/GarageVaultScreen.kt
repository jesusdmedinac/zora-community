package com.jesusdmedinac.zora.ui.garage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.garage.model.CopilotMemoryItem
import com.jesusdmedinac.zora.ui.garage.model.TelemetryCheckInState
import com.jesusdmedinac.zora.ui.garage.model.defaultCopilotMemories
import com.jesusdmedinac.zora.ui.theme.ZoraTokens

/**
 * Screen / M3 - Virtual Garage, Quick Telemetry Check-in & Copilot Memory Vault.
 * Features 02 scenarios:
 * 1. Primary vehicle showcase with mileage and health score
 * 2. Enforce single-vehicle garage slot limit for free tier (+ Add Another Vehicle with locked PRO badge)
 * 3. 30-second fluid and tire health check-in
 * 4. Copilot Memory Vault (3 slots capacity, local-first on-device indicator, on-demand item deletion)
 */
@Composable
fun GarageVaultScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onUpgradeToPro: (() -> Unit)? = null,
) {
    val memories = remember { mutableStateListOf<CopilotMemoryItem>().apply { addAll(defaultCopilotMemories()) } }
    var telemetry by remember { mutableStateOf(TelemetryCheckInState()) }
    var showProModal by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ZoraTokens.Canvas)
            .padding(vertical = ZoraTokens.SpacingSm),
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = ZoraTokens.SpacingMd),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(ZoraTokens.SurfaceContainerHigh)
                    .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "←",
                    color = ZoraTokens.OnSurface,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "VIRTUAL GARAGE",
                    fontFamily = ZoraTokens.FontHeadline,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = ZoraTokens.OnSurface,
                    letterSpacing = 0.5.sp,
                )
                Text(
                    text = "TELEMETRÍA Y BÓVEDA LOCAL",
                    fontFamily = ZoraTokens.FontMono,
                    fontSize = 10.sp,
                    color = ZoraTokens.Primary,
                )
            }

            Box(modifier = Modifier.size(36.dp))
        }

        // Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ZoraTokens.SpacingMd, vertical = ZoraTokens.SpacingMd),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // ==========================================
            // 1. Primary Vehicle Showcase Card
            // ==========================================
            Box(
                modifier = Modifier
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
                                text = "Primary Vehicle",
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
                                text = "OBD-II Enlazado",
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = ZoraTokens.StatusSuccess,
                            )
                        }
                    }

                    Text(
                        text = "2018 Honda Civic EX",
                        fontFamily = ZoraTokens.FontHeadline,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = ZoraTokens.OnSurface,
                    )

                    Text(
                        text = "2.0L 4-Cylinder · FWD Hatchback",
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
                            text = "VIN: 2HGFC2F59JH02****",
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
                                text = "Odómetro",
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 11.sp,
                                color = ZoraTokens.OnSurfaceVariant,
                            )
                            Text(
                                text = "82,450 km",
                                fontFamily = ZoraTokens.FontMono,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = ZoraTokens.OnSurface,
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Salud General",
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 11.sp,
                                color = ZoraTokens.OnSurfaceVariant,
                            )
                            Text(
                                text = "94% Normal",
                                fontFamily = ZoraTokens.FontMono,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = ZoraTokens.StatusSuccess,
                            )
                        }
                    }
                }
            }

            // ==========================================
            // 2. Garage Slots Section (Free Tier Limit)
            // ==========================================
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "ESPACIOS DE GARAJE",
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ZoraTokens.Primary,
                    )
                    Text(
                        text = "1 of 1 Free Slot Used",
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 11.sp,
                        color = ZoraTokens.OnSurfaceVariant,
                    )
                }

                // Slot 02: Locked with PRO badge
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ZoraTokens.ShapeLg)
                        .background(ZoraTokens.SurfaceContainerLow)
                        .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeLg)
                        .clickable {
                            showProModal = true
                            onUpgradeToPro?.invoke()
                        }
                        .padding(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "SLOT 02",
                                fontFamily = ZoraTokens.FontMono,
                                fontSize = 10.sp,
                                color = ZoraTokens.Outline,
                            )
                            Text(
                                text = "+ Add Another Vehicle",
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = ZoraTokens.OnSurfaceVariant,
                            )
                        }
                        // Locked PRO badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(ZoraTokens.SecondaryContainer)
                                .padding(horizontal = 8.dp, vertical = 3.dp),
                        ) {
                            Text(
                                text = "PRO",
                                fontFamily = ZoraTokens.FontMono,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                            )
                        }
                    }
                }

                if (showProModal) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(ZoraTokens.ShapeMd)
                            .background(ZoraTokens.SurfaceContainerHighest)
                            .border(1.dp, ZoraTokens.Primary, ZoraTokens.ShapeMd)
                            .padding(12.dp),
                    ) {
                        Text(
                            text = "Upgrade to Zora Pro to unlock unlimited garage slots and multi-vehicle telemetry.",
                            fontFamily = ZoraTokens.FontBody,
                            fontSize = 12.sp,
                            color = ZoraTokens.OnSurface,
                        )
                    }
                }
            }

            // ==========================================
            // 3. Quick Health Check-in (30s)
            // ==========================================
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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

            // ==========================================
            // 4. Copilot Memory Vault (Inspectable & Deletable)
            // ==========================================
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                val percentage = if (memories.isEmpty()) 0 else kotlin.math.round((memories.size.toDouble() / 3.0) * 100).toInt()
                val meterText = "${memories.size} of 3 Free Slots ($percentage%)"

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "COPILOT MEMORY VAULT",
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ZoraTokens.Primary,
                    )
                    Text(
                        text = meterText,
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 11.sp,
                        color = if (memories.size == 3) ZoraTokens.StatusWarning else ZoraTokens.Primary,
                    )
                }

                // Local-first Indicator
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ZoraTokens.ShapeSm)
                        .background(ZoraTokens.SurfaceContainerLow)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                ) {
                    Text(
                        text = "🔒 Stored strictly on-device in local SQLite. Zero cloud leakage.",
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 11.sp,
                        color = ZoraTokens.StatusSuccess,
                    )
                }

                // Memory items
                memories.forEach { memory ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(ZoraTokens.ShapeMd)
                            .background(ZoraTokens.SurfaceContainerLow)
                            .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeMd)
                            .padding(12.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top,
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = memory.category,
                                        fontFamily = ZoraTokens.FontBody,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ZoraTokens.OnSurface,
                                    )
                                    Text(
                                        text = "• ${memory.source}",
                                        fontFamily = ZoraTokens.FontBody,
                                        fontSize = 10.sp,
                                        color = ZoraTokens.OnSurfaceVariant,
                                    )
                                }
                                Text(
                                    text = memory.detail,
                                    fontFamily = ZoraTokens.FontBody,
                                    fontSize = 12.sp,
                                    color = ZoraTokens.OnSurfaceVariant,
                                    lineHeight = 16.sp,
                                )
                            }

                            // Delete Action
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(ZoraTokens.SurfaceContainerHigh)
                                    .clickable { memories.remove(memory) },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "✕",
                                    color = ZoraTokens.StatusError,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TelemetryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            fontFamily = ZoraTokens.FontBody,
            fontSize = 13.sp,
            color = ZoraTokens.OnSurfaceVariant,
        )
        Text(
            text = value,
            fontFamily = ZoraTokens.FontMono,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = ZoraTokens.OnSurface,
        )
    }
}
