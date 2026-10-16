package com.jesusdmedinac.zora.ui.paywall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jesusdmedinac.zora.ui.paywall.component.AgentNetworkCard
import com.jesusdmedinac.zora.ui.paywall.component.PaywallCtaArea
import com.jesusdmedinac.zora.ui.paywall.component.PaywallTopBar
import com.jesusdmedinac.zora.ui.paywall.component.ProSuperpowersMatrix
import com.jesusdmedinac.zora.ui.paywall.component.SubscriptionPlansSelector
import com.jesusdmedinac.zora.ui.paywall.model.SubscriptionBilling
import com.jesusdmedinac.zora.ui.paywall.model.defaultAgentNodes
import com.jesusdmedinac.zora.ui.paywall.model.defaultProSuperpowers
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview

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
        // 1. Top Header with Close Button
        PaywallTopBar(onClose = onClose)

        // 2. Scrollable Body Content
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ZoraTokens.SpacingMd, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Section A: Autonomous Multi-Agent Network
            AgentNetworkCard(agentNodes = agentNodes)

            // Section B: 2x2 Pro Superpowers Matrix
            ProSuperpowersMatrix(superpowers = superpowers)

            // Section C: Subscription Pricing Toggle
            SubscriptionPlansSelector(
                selectedBilling = selectedBilling,
                onSelectBilling = { selectedBilling = it },
            )
        }

        // 3. Bottom CTA Action Area
        PaywallCtaArea(onStartTrial = onStartTrial)
    }
}

@Preview
@Composable
private fun ZoraProPaywallScreenPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        ZoraProPaywallScreen(
            onClose = {},
            onStartTrial = {},
        )
    }
}
