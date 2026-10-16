package com.jesusdmedinac.zora.ui.paywall

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
import com.jesusdmedinac.zora.ui.paywall.model.SubscriptionBilling
import com.jesusdmedinac.zora.ui.paywall.model.defaultAgentNodes
import com.jesusdmedinac.zora.ui.paywall.model.defaultProSuperpowers
import com.jesusdmedinac.zora.ui.theme.ZoraTokens

/**
 * Screen / M3 - Zora Pro Multi-Agent Network & Subscription Paywall.
 * Implements Feature 04 BDD scenarios:
 * 1. Autonomous multi-agent network status & nodes (Junkyard & Salvage Agent, Fuel Agent, Mechanic Agent + "Local-First · Zero Leak")
 * 2. 2x2 Pro Superpowers Matrix (Camera Visual Diagnosis, Unlimited Multi-Car Garage, Infinite Copilot Memory, Unlimited Visual Renders)
 * 3. Annual ($79.99/yr, $6.66/mo, SAVE 33% · BEST VALUE) vs Monthly ($9.99/mo) plan toggle
 * 4. Free limit trigger with "Start 7-Day Free Trial" CTA
 */
@Composable
fun ZoraProPaywallScreen(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    onStartTrial: (() -> Unit)? = null,
) {
    var selectedBilling by remember { mutableStateOf(SubscriptionBilling.ANNUAL) }
    val agentNodes = remember { defaultAgentNodes() }
    val superpowers = remember { defaultProSuperpowers() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ZoraTokens.Canvas)
            .padding(vertical = ZoraTokens.SpacingSm),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        // --- 1. Top Header with Close Button ---
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
                    .clickable(onClick = onClose),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "✕",
                    color = ZoraTokens.OnSurface,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "ZORA PRO",
                    fontFamily = ZoraTokens.FontHeadline,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = ZoraTokens.OnSurface,
                    letterSpacing = 1.sp,
                )
                Text(
                    text = "AUTONOMOUS AGENT NETWORK",
                    fontFamily = ZoraTokens.FontMono,
                    fontSize = 9.sp,
                    color = ZoraTokens.Primary,
                )
            }

            Box(modifier = Modifier.size(36.dp))
        }

        // --- 2. Scrollable Body Content ---
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ZoraTokens.SpacingMd, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // ==========================================
            // Section A: Autonomous Multi-Agent Network
            // ==========================================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ZoraTokens.ShapeLg)
                    .background(ZoraTokens.SurfaceContainer)
                    .border(1.dp, ZoraTokens.Primary, ZoraTokens.ShapeLg)
                    .padding(14.dp),
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "RED AUTÓNOMA DE AGENTES",
                            fontFamily = ZoraTokens.FontBody,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ZoraTokens.Primary,
                        )

                        // Privacy Affirmation Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF003822))
                                .padding(horizontal = 8.dp, vertical = 2.dp),
                        ) {
                            Text(
                                text = "Local-First · Zero Leak",
                                fontFamily = ZoraTokens.FontMono,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = ZoraTokens.StatusSuccess,
                            )
                        }
                    }

                    // Agent Nodes List
                    agentNodes.forEach { node ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(ZoraTokens.ShapeMd)
                                .background(ZoraTokens.SurfaceContainerLow)
                                .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeMd)
                                .padding(10.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text(
                                        text = node.name,
                                        fontFamily = ZoraTokens.FontBody,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = ZoraTokens.OnSurface,
                                    )
                                    Text(
                                        text = "Cluster: ${node.cluster}",
                                        fontFamily = ZoraTokens.FontMono,
                                        fontSize = 10.sp,
                                        color = ZoraTokens.OnSurfaceVariant,
                                    )
                                }

                                val isLive = node.status.startsWith("ACTIVE")
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(if (isLive) Color(0xFF003822) else Color(0xFF25262B))
                                        .padding(horizontal = 6.dp, vertical = 2.dp),
                                ) {
                                    Text(
                                        text = node.status,
                                        fontFamily = ZoraTokens.FontMono,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isLive) ZoraTokens.StatusSuccess else ZoraTokens.OnSurfaceVariant,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ==========================================
            // Section B: 2x2 Pro Superpowers Matrix
            // ==========================================
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "SUPERPODERES PRO",
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ZoraTokens.Primary,
                )

                // 2x2 Grid rendered as paired rows
                superpowers.chunked(2).forEach { rowPair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        rowPair.forEach { power ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(ZoraTokens.ShapeMd)
                                    .background(ZoraTokens.SurfaceContainer)
                                    .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeMd)
                                    .padding(12.dp),
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = power.title,
                                        fontFamily = ZoraTokens.FontBody,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ZoraTokens.OnSurface,
                                    )
                                    Text(
                                        text = power.description,
                                        fontFamily = ZoraTokens.FontBody,
                                        fontSize = 11.sp,
                                        color = ZoraTokens.OnSurfaceVariant,
                                        lineHeight = 14.sp,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ==========================================
            // Section C: Subscription Pricing Toggle
            // ==========================================
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "PLANES DE SUSCRIPCIÓN",
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ZoraTokens.Primary,
                )

                // Option 1: Annual Membership (Default)
                val isAnnual = selectedBilling == SubscriptionBilling.ANNUAL
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ZoraTokens.ShapeMd)
                        .background(if (isAnnual) ZoraTokens.SurfaceContainerHigh else ZoraTokens.SurfaceContainerLow)
                        .border(
                            1.dp,
                            if (isAnnual) ZoraTokens.Primary else ZoraTokens.OutlineVariant,
                            ZoraTokens.ShapeMd
                        )
                        .clickable { selectedBilling = SubscriptionBilling.ANNUAL }
                        .padding(14.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Text(
                                    text = "Annual Membership",
                                    fontFamily = ZoraTokens.FontBody,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ZoraTokens.OnSurface,
                                )
                                // Best value badge
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(ZoraTokens.TertiaryContainer)
                                        .padding(horizontal = 6.dp, vertical = 2.dp),
                                ) {
                                    Text(
                                        text = "SAVE 33% · BEST VALUE",
                                        fontFamily = ZoraTokens.FontMono,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                    )
                                }
                            }
                            Text(
                                text = "$79.99 / year ($6.66/month)",
                                fontFamily = ZoraTokens.FontMono,
                                fontSize = 12.sp,
                                color = ZoraTokens.Primary,
                            )
                        }

                        // Radio Button
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .border(
                                    2.dp,
                                    if (isAnnual) ZoraTokens.Primary else ZoraTokens.OutlineVariant,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            if (isAnnual) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(ZoraTokens.Primary)
                                )
                            }
                        }
                    }
                }

                // Option 2: Monthly Membership
                val isMonthly = selectedBilling == SubscriptionBilling.MONTHLY
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ZoraTokens.ShapeMd)
                        .background(if (isMonthly) ZoraTokens.SurfaceContainerHigh else ZoraTokens.SurfaceContainerLow)
                        .border(
                            1.dp,
                            if (isMonthly) ZoraTokens.Primary else ZoraTokens.OutlineVariant,
                            ZoraTokens.ShapeMd
                        )
                        .clickable { selectedBilling = SubscriptionBilling.MONTHLY }
                        .padding(14.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = "Monthly Membership",
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = ZoraTokens.OnSurface,
                            )
                            Text(
                                text = "$9.99 / month · flexible cancellation",
                                fontFamily = ZoraTokens.FontMono,
                                fontSize = 12.sp,
                                color = ZoraTokens.OnSurfaceVariant,
                            )
                        }

                        // Radio Button
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .border(
                                    2.dp,
                                    if (isMonthly) ZoraTokens.Primary else ZoraTokens.OutlineVariant,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            if (isMonthly) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(ZoraTokens.Primary)
                                )
                            }
                        }
                    }
                }
            }
        }

        // --- 3. Bottom CTA Action Area ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = ZoraTokens.SpacingMd, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(CircleShape)
                    .background(ZoraTokens.PrimaryContainer)
                    .clickable { onStartTrial?.invoke() },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Start 7-Day Free Trial",
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }

            Text(
                text = "7 days free · Cancel anytime · Secured via App Store",
                fontFamily = ZoraTokens.FontBody,
                fontSize = 11.sp,
                color = ZoraTokens.OnSurfaceVariant,
            )
        }
    }
}
