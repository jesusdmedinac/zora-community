package com.jesusdmedinac.zora.ui.home.model

/**
 * User interaction intents for shifted drawer navigation.
 */
sealed interface ZoraDrawerIntent {
    data class SetDrawerOpen(val isOpen: Boolean) : ZoraDrawerIntent
    data object OpenDrawer : ZoraDrawerIntent
    data object CloseDrawer : ZoraDrawerIntent
    data object ClickNewChat : ZoraDrawerIntent
    data object ClickParts : ZoraDrawerIntent
    data object ClickMechanic : ZoraDrawerIntent
    data object ClickGasStations : ZoraDrawerIntent
    data class SelectRecentQuestion(val question: String) : ZoraDrawerIntent
    data object ClickSettings : ZoraDrawerIntent
    data object ClickProfile : ZoraDrawerIntent
}
