package com.jesusdmedinac.zora.ui.home

import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerIntent
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerMenuItemId
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerSideEffect
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerUiState
import kotlinx.coroutines.test.runTest
import org.orbitmvi.orbit.test.testWithInternalState
import kotlin.test.Test

class ZoraShiftedDrawerViewModelTest {

    @Test
    fun `SetDrawerOpen intent controls drawer visibility`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        viewModel.testWithInternalState(this, initialState = ZoraDrawerUiState(isOpen = false)) {
            containerHost.processIntent(ZoraDrawerIntent.SetDrawerOpen(true))
            expectInternalState { copy(isOpen = true) }

            containerHost.processIntent(ZoraDrawerIntent.SetDrawerOpen(false))
            expectInternalState { copy(isOpen = false) }
            expectSideEffect(ZoraDrawerSideEffect.CloseDrawer)
        }
    }

    @Test
    fun `OpenDrawer and CloseDrawer intents control drawer visibility`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        viewModel.testWithInternalState(this, initialState = ZoraDrawerUiState(isOpen = false)) {
            containerHost.processIntent(ZoraDrawerIntent.OpenDrawer)
            expectInternalState { copy(isOpen = true) }

            containerHost.processIntent(ZoraDrawerIntent.CloseDrawer)
            expectInternalState { copy(isOpen = false) }
            expectSideEffect(ZoraDrawerSideEffect.CloseDrawer)
        }
    }

    @Test
    fun `ClickNewChat closes drawer, clears selected question and emits NavigateToNewChat`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        viewModel.testWithInternalState(
            this,
            initialState = ZoraDrawerUiState(isOpen = true, selectedRecentQuestion = "¿Qué es la bujía?"),
        ) {
            containerHost.processIntent(ZoraDrawerIntent.ClickNewChat)
            expectInternalState { copy(isOpen = false, selectedRecentQuestion = null) }
            expectSideEffect(ZoraDrawerSideEffect.NavigateToNewChat)
        }
    }

    @Test
    fun `ClickParts sets selectedMenuItem to PARTS, closes drawer and emits NavigateToPartsFeed`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        viewModel.testWithInternalState(this, initialState = ZoraDrawerUiState(isOpen = true)) {
            containerHost.processIntent(ZoraDrawerIntent.ClickParts)
            expectInternalState { copy(isOpen = false, selectedMenuItem = ZoraDrawerMenuItemId.PARTS) }
            expectSideEffect(ZoraDrawerSideEffect.NavigateToPartsFeed)
        }
    }

    @Test
    fun `ClickMechanic closes drawer and emits NavigateToPaywall for mechanic`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        viewModel.testWithInternalState(this, initialState = ZoraDrawerUiState(isOpen = true)) {
            containerHost.processIntent(ZoraDrawerIntent.ClickMechanic)
            expectInternalState { copy(isOpen = false) }
            expectSideEffect(ZoraDrawerSideEffect.NavigateToPaywall("drawer_menu_mechanic"))
        }
    }

    @Test
    fun `ClickGasStations closes drawer and emits NavigateToPaywall for gas stations`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        viewModel.testWithInternalState(this, initialState = ZoraDrawerUiState(isOpen = true)) {
            containerHost.processIntent(ZoraDrawerIntent.ClickGasStations)
            expectInternalState { copy(isOpen = false) }
            expectSideEffect(ZoraDrawerSideEffect.NavigateToPaywall("drawer_menu_gas_stations"))
        }
    }

    @Test
    fun `SelectRecentQuestion selects question, closes drawer and emits LoadRecentChat`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        val question = "¿Por qué rechinan mis frenos?"
        viewModel.testWithInternalState(this, initialState = ZoraDrawerUiState(isOpen = true)) {
            containerHost.processIntent(ZoraDrawerIntent.SelectRecentQuestion(question))
            expectInternalState { copy(isOpen = false, selectedRecentQuestion = question) }
            expectSideEffect(ZoraDrawerSideEffect.LoadRecentChat(question))
        }
    }

    @Test
    fun `ClickSettings closes drawer and emits NavigateToSettings`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        viewModel.testWithInternalState(this, initialState = ZoraDrawerUiState(isOpen = true)) {
            containerHost.processIntent(ZoraDrawerIntent.ClickSettings)
            expectInternalState { copy(isOpen = false) }
            expectSideEffect(ZoraDrawerSideEffect.NavigateToSettings)
        }
    }

    @Test
    fun `ClickProfile closes drawer and emits NavigateToProfile`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        viewModel.testWithInternalState(this, initialState = ZoraDrawerUiState(isOpen = true)) {
            containerHost.processIntent(ZoraDrawerIntent.ClickProfile)
            expectInternalState { copy(isOpen = false) }
            expectSideEffect(ZoraDrawerSideEffect.NavigateToProfile)
        }
    }
}
