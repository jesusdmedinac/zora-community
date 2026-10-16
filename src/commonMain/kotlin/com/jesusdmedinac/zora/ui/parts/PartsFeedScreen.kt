package com.jesusdmedinac.zora.ui.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.jesusdmedinac.zora.ui.parts.component.CommunityInsightsCard
import com.jesusdmedinac.zora.ui.parts.component.PartCard
import com.jesusdmedinac.zora.ui.parts.component.PartsCategoryChips
import com.jesusdmedinac.zora.ui.parts.component.PartsTopBar
import com.jesusdmedinac.zora.ui.parts.component.VisualBlueprintCard
import com.jesusdmedinac.zora.ui.parts.model.CommunityInsightsState
import com.jesusdmedinac.zora.ui.parts.model.PartCategory
import com.jesusdmedinac.zora.ui.parts.model.VisualBlueprintState
import com.jesusdmedinac.zora.ui.parts.model.defaultPartsCatalog
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import com.jesusdmedinac.zora.ui.theme.zoraEdgePadding
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Screen / M3 - Contextual Parts Feed, Salvage Yonke Network & 3D Component Engine.
 * Adheres to Feature 03 BDD specifications:
 * 1. Filter by guaranteed fitment & category chips ("All", "Brakes", "Fluids & Filters", "Suspension", "Junkyard Network")
 * 2. Yonke Salvage Yard highlights: "Verified Salvage · Yonke Network", Donor VIN & mileage, savings calculation, distance/grade
 * 3. 3D Cutaway Visual Blueprint with quota counter ("1 of 3 Used" -> "2 of 3 Used") and disclaimer
 * 4. Fleet-wide community insights privacy gate behind Cloud AI Opt-In
 */
@Composable
fun PartsFeedScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    activeVehicle: String = "2018 Honda Civic EX 2.0L K20C2",
    onReviewOptInSettings: (() -> Unit)? = null,
) {
    var selectedCategory by remember { mutableStateOf(PartCategory.ALL) }
    var blueprintState by remember { mutableStateOf(VisualBlueprintState(quotaCount = 1)) }
    var communityState by remember { mutableStateOf(CommunityInsightsState()) }
    var showOptInModal by remember { mutableStateOf(false) }

    val parts = remember { defaultPartsCatalog() }
    val filteredParts = remember(selectedCategory) {
        if (selectedCategory == PartCategory.ALL) parts
        else parts.filter { it.category == selectedCategory }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ZoraTokens.Canvas)
            .zoraEdgePadding(),
    ) {
        // 1. Top Bar with Fitment Pill & Subtitle
        PartsTopBar(
            activeVehicle = activeVehicle,
            onBack = onBack,
        )

        // 2. Category Filter Chips
        PartsCategoryChips(
            selectedCategory = selectedCategory,
            onSelectCategory = { selectedCategory = it },
        )

        // 3. Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ZoraTokens.SpacingMd, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Parts List
            filteredParts.forEach { item ->
                PartCard(item = item)
            }

            // 4. On-demand 3D Component Visual Blueprint
            VisualBlueprintCard(
                blueprintState = blueprintState,
                onRequestBlueprint = {
                    blueprintState = blueprintState.copy(
                        isGenerated = true,
                        quotaCount = 2,
                    )
                },
            )

            // 5. Fleet-wide Community Insights Privacy Gate
            CommunityInsightsCard(
                communityState = communityState,
                showOptInModal = showOptInModal,
                onOptInClick = {
                    showOptInModal = true
                    onReviewOptInSettings?.invoke()
                },
            )
        }
    }
}

@Preview
@Composable
private fun PartsFeedScreenPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        PartsFeedScreen(
            onBack = {},
            onReviewOptInSettings = {},
        )
    }
}
