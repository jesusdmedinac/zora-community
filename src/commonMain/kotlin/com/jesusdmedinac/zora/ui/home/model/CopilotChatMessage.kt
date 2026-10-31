package com.jesusdmedinac.zora.ui.home.model

import androidx.compose.runtime.Immutable
import com.jesusdmedinac.jsontocompose.model.ComposeDocument
import kotlinx.serialization.Serializable
import kotlin.random.Random

/**
 * Immutable chat message domain model for the Copilot conversational stream.
 */
@Serializable
@Immutable
sealed interface CopilotChatMessage {
    val id: String

    @Serializable
    @Immutable
    data class User(
        val text: String,
        override val id: String = "user_${Random.nextInt(100000, 999999)}",
    ) : CopilotChatMessage

    @Serializable
    @Immutable
    data class Assistant(
        val text: String,
        val isTyping: Boolean = false,
        val dynamicDocument: ComposeDocument? = null,
        override val id: String = "asst_${Random.nextInt(100000, 999999)}",
    ) : CopilotChatMessage
}

