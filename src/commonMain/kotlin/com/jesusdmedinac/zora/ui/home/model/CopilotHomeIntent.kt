package com.jesusdmedinac.zora.ui.home.model

/**
 * User interaction intents for Copilot Home screen.
 */
sealed interface CopilotHomeIntent {
    data class UpdatePromptText(val text: String) : CopilotHomeIntent
    data object ClearPromptText : CopilotHomeIntent
    data class SetDrawerOpen(val isOpen: Boolean) : CopilotHomeIntent
    data class SendPrompt(val text: String) : CopilotHomeIntent
    data class SelectSuggestion(val symptom: String) : CopilotHomeIntent
    data object OpenNewChat : CopilotHomeIntent
    data class SelectRecentChat(val question: String) : CopilotHomeIntent
    data object OpenGarage : CopilotHomeIntent
    data class OpenPaywall(val sourceReason: String? = null) : CopilotHomeIntent
    data object RefreshTelemetry : CopilotHomeIntent
    data object ClearChat : CopilotHomeIntent
}
