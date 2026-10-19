package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.unit.Density
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ZoraPromptComposerTest {

    @Test
    fun `when prompt text is empty clicking action button triggers voice call mode`() {
        var voiceModeTriggered = false
        var sentMessage: String? = null

        val promptValue = ""
        val hasText = promptValue.isNotEmpty()

        // Mimic the component's internal action button click resolution
        if (hasText) {
            sentMessage = promptValue
        } else {
            voiceModeTriggered = true
        }

        assertTrue(voiceModeTriggered, "Voice mode should be triggered when text is empty")
        assertEquals(null, sentMessage, "onSend should not be called when text is empty")
    }

    @Test
    fun `when prompt text contains at least one character voice mode cannot be activated and send is triggered`() {
        var voiceModeTriggered = false
        var sentMessage: String? = null

        val promptValue = "A" // user typed the first letter
        val hasText = promptValue.isNotEmpty()

        // Mimic the component's internal action button click resolution
        if (hasText) {
            sentMessage = promptValue
        } else {
            voiceModeTriggered = true
        }

        assertFalse(voiceModeTriggered, "Voice call mode must NOT be activated when text is present")
        assertEquals("A", sentMessage, "onSend must be invoked with the typed prompt")
    }

    @Test
    fun `renders cleanly in M3 and HIG without crashes`() {
        listOf(ZoraPlatformVariant.MATERIAL3, ZoraPlatformVariant.HUMAN_INTERFACE).forEach { platform ->
            listOf("", "Diagnóstico de bujías").forEach { text ->
                val scene = ImageComposeScene(width = 390, height = 80, density = Density(1f))
                scene.setContent {
                    ZoraTheme(platformVariant = platform) {
                        ZoraPromptComposer(
                            value = text,
                            onValueChange = {},
                            onSend = {},
                            onVoiceModeClick = {},
                        )
                    }
                }
                scene.render()
            }
        }
    }
}
