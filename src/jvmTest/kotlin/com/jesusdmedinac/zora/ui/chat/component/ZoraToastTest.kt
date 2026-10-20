package com.jesusdmedinac.zora.ui.chat.component

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ZoraToastTest {

    @Test
    fun initialStateIsEmptyByDefault() {
        val state = ZoraToastState()
        assertNull(state.message)
        assertFalse(state.isVisible)
    }

    @Test
    fun initialStateWithCustomMessageIsImmediatelyVisible() {
        val state = ZoraToastState(initialMessage = "Text copied")
        assertEquals("Text copied", state.message)
        assertTrue(state.isVisible)
    }

    @Test
    fun showToastImmediatelyUpdatesState() {
        val state = ZoraToastState()
        state.showToastImmediately("Texto copiado")
        assertEquals("Texto copiado", state.message)
        assertTrue(state.isVisible)
    }

    @Test
    fun dismissClearsActiveToast() {
        val state = ZoraToastState(initialMessage = "Text copied")
        assertTrue(state.isVisible)
        state.dismiss()
        assertNull(state.message)
        assertFalse(state.isVisible)
    }

    @Test
    fun showToastAutomatedDismissAfterDuration() = runTest {
        val state = ZoraToastState()
        val job = backgroundScope.launch {
            state.showToast("Text copied", durationMs = 500L)
        }

        testScheduler.advanceTimeBy(50L)
        assertEquals("Text copied", state.message)
        assertTrue(state.isVisible)

        testScheduler.advanceTimeBy(500L)
        job.join()

        assertNull(state.message)
        assertFalse(state.isVisible)
    }
}
