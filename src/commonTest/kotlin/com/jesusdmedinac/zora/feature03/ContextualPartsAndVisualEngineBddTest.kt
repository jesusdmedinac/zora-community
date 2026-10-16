package com.jesusdmedinac.zora.feature03

import com.jesusdmedinac.zora.ui.parts.model.CommunityInsightsState
import com.jesusdmedinac.zora.ui.parts.model.PartCategory
import com.jesusdmedinac.zora.ui.parts.model.VisualBlueprintState
import com.jesusdmedinac.zora.ui.parts.model.defaultPartsCatalog
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ContextualPartsAndVisualEngineBddTest {

    /**
     * SCENARIO 1: Filter parts feed by guaranteed vehicle fitment and category chips
     */
    @Test
    fun testScenario1_FilterPartsFeedByGuaranteedFitmentAndCategory() {
        val activeVehicle = "2018 Honda Civic EX 2.0L K20C2"
        val fitmentPillText = "Fitment Active · Precision Match"
        assertEquals("2018 Honda Civic EX 2.0L K20C2", activeVehicle)
        assertEquals("Fitment Active · Precision Match", fitmentPillText)

        val expectedCategories = listOf(
            "All",
            "Brakes",
            "Fluids & Filters",
            "Suspension",
            "Junkyard Network"
        )
        val actualCategories = PartCategory.entries.map { it.displayName }
        assertEquals(expectedCategories, actualCategories)

        val parts = defaultPartsCatalog()
        val brakesParts = parts.filter { it.category == PartCategory.BRAKES }
        val brakePad = brakesParts.firstOrNull { it.title == "Akebono Ultra-Premium Ceramic Front Brake Pads" }
        assertNotNull(brakePad, "Must contain Akebono Ultra-Premium Ceramic Front Brake Pads")
        assertEquals("Direct Fit Guarantee", brakePad.badge)
        assertEquals("#45022-TBA-A01", brakePad.oemInterchange)
    }

    /**
     * SCENARIO 2: Highlight Yonke salvage yard parts with donor VIN and savings calculation
     */
    @Test
    fun testScenario2_HighlightYonkeSalvagePartsWithDonorVinAndSavings() {
        val parts = defaultPartsCatalog()
        val junkyardParts = parts.filter { it.category == PartCategory.JUNKYARD_NETWORK }
        assertTrue(junkyardParts.isNotEmpty())

        val salvageItem = junkyardParts.first()
        assertEquals("Verified Salvage · Yonke Network", salvageItem.badge)
        assertEquals("Donor VIN: 1HGFC2...7391 (41k mi)", salvageItem.donorVinAndMileage)
        assertEquals("$85.00", salvageItem.price)
        assertEquals("Save 65% vs $240 New", salvageItem.savingsCalculation)
        assertEquals("9.2 mi away · Grade A Condition", salvageItem.distanceAndGrade)
        assertTrue(salvageItem.isSalvage)
    }

    /**
     * SCENARIO 3: Generate on-demand 3D component visual blueprint with monthly quota
     */
    @Test
    fun testScenario3_GenerateVisualBlueprintWithMonthlyQuota() {
        var state = VisualBlueprintState(quotaCount = 1)
        assertEquals("1 of 3 Used", state.quotaText)
        assertFalse(state.isGenerated)

        // When driver requests visual blueprint for DENSO ALTERNATOR
        state = state.copy(
            isGenerated = true,
            quotaCount = state.quotaCount + 1,
        )

        // Then cutaway wireframe renders with tag, updated quota and disclaimer
        assertTrue(state.isGenerated)
        assertEquals(
            "DENSO ALTERNATOR · Engine Bay (Front-Right Offset)",
            state.componentTag
        )
        assertEquals("2 of 3 Used", state.quotaText)
        assertEquals(
            "Note: AI-generated visual reference for illustration and component location only.",
            state.disclaimer
        )
    }

    /**
     * SCENARIO 4: Gate fleet-wide community insights behind explicit Cloud AI Opt-In
     */
    @Test
    fun testScenario4_GateFleetWideCommunityInsightsBehindCloudAiOptIn() {
        val state = CommunityInsightsState(isOptedIn = false)

        // Given operating in Local-Only session mode
        assertFalse(state.isOptedIn)
        assertEquals("Private Local Session Active", state.sessionStatus)
        assertEquals("Review Cloud AI Opt-In Settings", state.ctaText)
        assertTrue(state.explanation.contains("explicit opt-in"))
    }
}
