package com.jesusdmedinac.zora.ui.home.model

/**
 * One-off navigation and event side effects emitted by [com.jesusdmedinac.zora.ui.home.ZoraShiftedDrawerViewModel].
 */
sealed interface ZoraDrawerSideEffect {
    data object NavigateToNewChat : ZoraDrawerSideEffect
    data object NavigateToPartsFeed : ZoraDrawerSideEffect
    data class NavigateToPaywall(val sourceReason: String) : ZoraDrawerSideEffect
    data class LoadRecentChat(val question: String) : ZoraDrawerSideEffect
    data object NavigateToSettings : ZoraDrawerSideEffect
    data object NavigateToProfile : ZoraDrawerSideEffect
    data object CloseDrawer : ZoraDrawerSideEffect
}
