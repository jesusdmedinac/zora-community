package com.jesusdmedinac.zora.ui.splash

import com.jesusdmedinac.zora.ui.splash.model.TelemetryBootStage
import com.jesusdmedinac.zora.ui.splash.model.TelemetryBootState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TelemetryBootStateMachineTest {

    @Test
    fun testInitialProgressMapsToCanBusInit() {
        val stage = TelemetryBootStage.fromProgress(0.0f)
        assertEquals(TelemetryBootStage.CAN_BUS_INIT, stage)
        assertEquals("INITIALIZING CAN BUS ISO 15765-4 • 500 KBPS", stage.tickerText)
    }

    @Test
    fun testMidProgressTransitionsToMemoryVault() {
        val stage = TelemetryBootStage.fromProgress(0.45f)
        assertEquals(TelemetryBootStage.MEMORY_VAULT_INIT, stage)
        assertEquals("CALIBRATING LOCAL MEMORY VAULT...", stage.tickerText)
    }

    @Test
    fun testAdvancedProgressTransitionsToPrivacyGate() {
        val stage = TelemetryBootStage.fromProgress(0.75f)
        assertEquals(TelemetryBootStage.PRIVACY_GATE_ENGAGED, stage)
        assertEquals("ENGAGING OBD-II ZERO-TRUST PRIVACY GATE...", stage.tickerText)
    }

    @Test
    fun testFullProgressReachesIgnitionReady() {
        val stage = TelemetryBootStage.fromProgress(1.0f)
        assertEquals(TelemetryBootStage.IGNITION_READY, stage)
        assertEquals("ZORA AUTONOMOUS COPILOT READY • ONLINE", stage.tickerText)

        val state = TelemetryBootState(
            progress = 1.0f,
            stage = stage,
            isComplete = true
        )
        assertTrue(state.isComplete)
        assertEquals(1.0f, state.progress)
    }

    @Test
    fun testBoundaryThresholds() {
        assertEquals(TelemetryBootStage.CAN_BUS_INIT, TelemetryBootStage.fromProgress(0.34f))
        assertEquals(TelemetryBootStage.MEMORY_VAULT_INIT, TelemetryBootStage.fromProgress(0.35f))
        assertEquals(TelemetryBootStage.MEMORY_VAULT_INIT, TelemetryBootStage.fromProgress(0.69f))
        assertEquals(TelemetryBootStage.PRIVACY_GATE_ENGAGED, TelemetryBootStage.fromProgress(0.70f))
        assertEquals(TelemetryBootStage.PRIVACY_GATE_ENGAGED, TelemetryBootStage.fromProgress(0.99f))
        assertEquals(TelemetryBootStage.IGNITION_READY, TelemetryBootStage.fromProgress(1.0f))
    }
}
