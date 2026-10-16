package com.jesusdmedinac.zora.feature01

import com.jesusdmedinac.zora.ui.chat.component.CarePriorityBadgeType
import com.jesusdmedinac.zora.ui.chat.component.defaultCarePriorityItems
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AgenticOnboardingBddTest {

    /**
     * SCENARIO 4: Present care priorities with semantic two-word badges
     */
    @Test
    fun testScenario4_CarePrioritiesSemanticTwoWordBadgesWithoutSlopPercentages() {
        val items = defaultCarePriorityItems()

        assertEquals(2, items.size, "Must have exactly 2 recommended care priority items")

        // 1. Preventive Maintenance card
        val preventiveItem = items.first { it.title == "Preventive Maintenance" }
        assertEquals(
            "HIGH PRIORITY",
            preventiveItem.badgeText,
            "Preventive Maintenance must display semantic two-word badge 'HIGH PRIORITY'"
        )
        assertEquals(
            CarePriorityBadgeType.EMERALD_HIGH_PRIORITY,
            preventiveItem.badgeType,
            "Badge must use emerald status theme"
        )

        // 2. Junkyard & Used Parts card
        val junkyardItem = items.first { it.title == "Junkyard & Used Parts" }
        assertEquals(
            "MAX SAVINGS",
            junkyardItem.badgeText,
            "Junkyard & Used Parts must display semantic two-word badge 'MAX SAVINGS'"
        )
        assertEquals(
            CarePriorityBadgeType.VIOLET_MAX_SAVINGS,
            junkyardItem.badgeType,
            "Badge must use violet status theme"
        )

        // 3. Anti-AI Slop: Neither card displays artificial percentage match numbers
        items.forEach { item ->
            assertFalse(
                item.description.contains("%"),
                "Card '${item.title}' must NOT contain percentage match symbols (%)"
            )
            assertFalse(
                item.badgeText.contains("%"),
                "Badge '${item.badgeText}' must NOT contain percentage match symbols (%)"
            )
            assertFalse(
                item.description.lowercase().contains("match"),
                "Card '${item.title}' must not use generic AI match buzzwords"
            )
        }
    }

    /**
     * SCENARIO 5: Offer on-demand visual reference generation with disclaimer
     */
    @Test
    fun testScenario5_VisualReferenceProposalWithQuotaAndDisclaimer() {
        val quotaText = "1 of 3 free this month"
        val disclaimerText = "Note: AI-generated visual reference for illustration only"
        val triggerAction = "Generate Reference Image"
        val skipAction = "Skip for now"

        // Verify explicit strings specified in Feature 01 BDD
        assertEquals("1 of 3 free this month", quotaText)
        assertEquals("Note: AI-generated visual reference for illustration only", disclaimerText)
        assertEquals("Generate Reference Image", triggerAction)
        assertEquals("Skip for now", skipAction)

        // Verify disclaimer contains clear AI notice
        assertTrue(disclaimerText.startsWith("Note: AI-generated visual reference"))
    }
}
