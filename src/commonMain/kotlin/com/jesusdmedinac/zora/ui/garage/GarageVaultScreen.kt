package com.jesusdmedinac.zora.ui.garage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jesusdmedinac.zora.ui.garage.component.CopilotMemoryVaultCard
import com.jesusdmedinac.zora.ui.garage.component.GarageSlotsCard
import com.jesusdmedinac.zora.ui.garage.component.GarageTopBar
import com.jesusdmedinac.zora.ui.garage.component.PrimaryVehicleCard
import com.jesusdmedinac.zora.ui.garage.component.QuickHealthCheckInCard
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
        // 1. Top Header
        GarageTopBar(onBack = onBack)

        // 2. Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ZoraTokens.SpacingMd, vertical = ZoraTokens.SpacingMd),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Primary Vehicle Showcase Card
            PrimaryVehicleCard()

            // Garage Slots Section (Free Tier Limit)
            GarageSlotsCard(
                showProModal = showProModal,
                onSlotClick = {
                    showProModal = true
                    onUpgradeToPro?.invoke()
                },
            )

            // Quick Health Check-in (30s)
            QuickHealthCheckInCard(telemetry = telemetry)

            // Copilot Memory Vault (Inspectable & Deletable)
            CopilotMemoryVaultCard(
                memories = memories,
                onDeleteMemory = { memory -> memories.remove(memory) },
            )
        }
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun GarageVaultScreenPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        GarageVaultScreen(
            onBack = {},
            onUpgradeToPro = {},
        )
    }
}
