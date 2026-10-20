package com.jesusdmedinac.zora.ui.home.model

import androidx.compose.runtime.Immutable
import kotlin.random.Random

/**
 * Immutable chat message domain model for the Copilot conversational stream.
 */
@Immutable
sealed interface CopilotChatMessage {
    val id: String

    @Immutable
    data class User(
        val text: String,
        override val id: String = "user_${Random.nextInt(100000, 999999)}",
    ) : CopilotChatMessage

    @Immutable
    data class Assistant(
        val text: String,
        val isTyping: Boolean = false,
        override val id: String = "asst_${Random.nextInt(100000, 999999)}",
    ) : CopilotChatMessage
}

