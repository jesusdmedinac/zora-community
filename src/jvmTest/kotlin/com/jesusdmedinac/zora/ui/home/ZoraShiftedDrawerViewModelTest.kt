package com.jesusdmedinac.zora.ui.home

import com.jesusdmedinac.zora.data.local.InMemoryMechanicalPrescriptionRepository
import com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescriptionEntity
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerIntent
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerMenuItemId
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerSideEffect
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerUiState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.orbitmvi.orbit.test.testWithInternalState
import kotlin.test.Test
import kotlin.test.assertEquals

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

    @Test
    fun `updateRecentQuestions updates recent questions in state`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        val customQuestions = listOf("Pregunta 1", "Pregunta 2")
        viewModel.testWithInternalState(this) {
            containerHost.updateRecentQuestions(customQuestions)
            expectInternalState { copy(recentQuestions = customQuestions) }
        }
    }

    @Test
    fun `observeByVehicle emits diagnostic questions in reverse chronological order`() = runTest {
        val repo = InMemoryMechanicalPrescriptionRepository()
        val entity1 = MechanicalPrescriptionEntity(
            symptomId = "sym_1",
            vehicleId = "honda_civic_2018",
            vinTechnicalPrefix = "2HGFC2F5",
            rawSymptomDescription = "Primera consulta",
            localPreliminaryHypothesis = "H1",
            createdAtMs = 1000L,
            updatedAtMs = 1000L,
        )
        val entity2 = MechanicalPrescriptionEntity(
            symptomId = "sym_2",
            vehicleId = "honda_civic_2018",
            vinTechnicalPrefix = "2HGFC2F5",
            rawSymptomDescription = "Segunda consulta",
            localPreliminaryHypothesis = "H2",
            createdAtMs = 2000L,
            updatedAtMs = 2000L,
        )
        repo.insertLocalTriage(entity1)
        repo.insertLocalTriage(entity2)

        val questions = repo.observeByVehicle("honda_civic_2018").first().map { it.rawSymptomDescription }
        assertEquals(listOf("Segunda consulta", "Primera consulta"), questions)
    }
}
