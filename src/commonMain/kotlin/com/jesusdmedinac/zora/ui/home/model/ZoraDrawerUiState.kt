package com.jesusdmedinac.zora.ui.home.model

import androidx.compose.runtime.Immutable
import com.jesusdmedinac.zora.ui.home.component.defaultRecentQuestions

/**
 * Identifiers for primary drawer navigation menu items.
 */
enum class ZoraDrawerMenuItemId {
    PARTS,
    MECHANIC,
    GAS_STATIONS,
}

/**
 * Immutable UI state for Zora shifted drawer.
 * Aligned with nodes VeQvH and DotCf in `zora-community.pen`.
 */
@Immutable
data class ZoraDrawerUiState(
    val isOpen: Boolean = false,
    val userName: String = "Jesús Medina",
    val accountTier: String = "Cuenta Pro",
    val recentQuestions: List<String> = defaultRecentQuestions(),
    val pinnedQuestions: List<String> = emptyList(),
    val selectedRecentQuestion: String? = null,
    val selectedMenuItem: ZoraDrawerMenuItemId? = null,
    val activeBottomSheetQuestion: String? = null,
    val activeRenameQuestion: String? = null,
)
