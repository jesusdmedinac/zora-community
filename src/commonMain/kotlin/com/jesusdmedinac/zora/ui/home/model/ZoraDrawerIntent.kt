package com.jesusdmedinac.zora.ui.home.model

/**
 * User interaction intents for shifted drawer navigation.
 */
sealed interface ZoraDrawerIntent {
    data class SetDrawerOpen(val isOpen: Boolean) : ZoraDrawerIntent
    data object OpenDrawer : ZoraDrawerIntent
    data object CloseDrawer : ZoraDrawerIntent
    data object LoadRecentQuestions : ZoraDrawerIntent
    data object ClickNewChat : ZoraDrawerIntent
    data object ClickParts : ZoraDrawerIntent
    data object ClickMechanic : ZoraDrawerIntent
    data object ClickGasStations : ZoraDrawerIntent
    data class SelectRecentQuestion(val question: String) : ZoraDrawerIntent
    data class LongClickRecentQuestion(val question: String) : ZoraDrawerIntent
    data object DismissRecentQuestionOptions : ZoraDrawerIntent
    data class ShareRecentQuestion(val question: String) : ZoraDrawerIntent
    data class PinRecentQuestion(val question: String) : ZoraDrawerIntent
    data class StartRenameRecentQuestion(val question: String) : ZoraDrawerIntent
    data class ConfirmRenameRecentQuestion(val oldQuestion: String, val newQuestion: String) : ZoraDrawerIntent
    data object CancelRenameRecentQuestion : ZoraDrawerIntent
    data class DeleteRecentQuestion(val question: String) : ZoraDrawerIntent
    data object ClickSettings : ZoraDrawerIntent
    data object ClickProfile : ZoraDrawerIntent
}
