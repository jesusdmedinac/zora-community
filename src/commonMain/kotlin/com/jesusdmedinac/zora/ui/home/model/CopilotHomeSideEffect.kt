package com.jesusdmedinac.zora.ui.home.model

/**
 * Single-shot side effects emitted by [com.jesusdmedinac.zora.ui.home.CopilotHomeViewModel].
 */
sealed interface CopilotHomeSideEffect {
    data class NavigateToChat(val initialPrompt: String? = null) : CopilotHomeSideEffect
    data object NavigateToGarage : CopilotHomeSideEffect
    data class ShowSnackbar(val message: String) : CopilotHomeSideEffect
}
