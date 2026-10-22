package com.jesusdmedinac.zora.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesusdmedinac.zora.data.local.ZoraRepositories
import com.jesusdmedinac.zora.domain.repository.MechanicalPrescriptionRepository
import com.jesusdmedinac.zora.ui.home.component.defaultRecentQuestions
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerIntent
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerMenuItemId
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerSideEffect
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.viewmodel.orbitContainer

/**
 * Orbit MVI ViewModel managing state, actions, and navigation triggers for the Shifted Navigation Drawer.
 * Controls drawer slide state, selected menu/recent chat items, and user profile metadata.
 */
class ZoraShiftedDrawerViewModel(
    initialState: ZoraDrawerUiState = ZoraDrawerUiState(),
    private val repository: MechanicalPrescriptionRepository? = null,
    private val vehicleId: String = "honda_civic_2018",
) : ViewModel(),
    OrbitContainerHost<ZoraDrawerUiState, ZoraDrawerUiState, ZoraDrawerSideEffect> {

    override val container = orbitContainer<ZoraDrawerUiState, ZoraDrawerSideEffect>(initialState)

    init {
        repository?.let { repo ->
            viewModelScope.launch {
                repo.observeByVehicle(vehicleId).collect { prescriptions ->
                    val questions = if (prescriptions.isNotEmpty()) {
                        prescriptions.map { it.rawSymptomDescription }
                    } else {
                        defaultRecentQuestions()
                    }
                    updateRecentQuestions(questions)
                }
            }
        }
    }

    val uiState: StateFlow<ZoraDrawerUiState>
        get() = container.stateFlow

    val sideEffect: Flow<ZoraDrawerSideEffect>
        get() = container.sideEffectFlow

    fun processIntent(intent: ZoraDrawerIntent) {
        when (intent) {
            is ZoraDrawerIntent.SetDrawerOpen -> setDrawerOpen(intent.isOpen)
            is ZoraDrawerIntent.OpenDrawer -> setDrawerOpen(true)
            is ZoraDrawerIntent.CloseDrawer -> setDrawerOpen(false)
            is ZoraDrawerIntent.ClickNewChat -> clickNewChat()
            is ZoraDrawerIntent.ClickParts -> clickParts()
            is ZoraDrawerIntent.ClickMechanic -> clickMechanic()
            is ZoraDrawerIntent.ClickGasStations -> clickGasStations()
            is ZoraDrawerIntent.SelectRecentQuestion -> selectRecentQuestion(intent.question)
            is ZoraDrawerIntent.ClickSettings -> clickSettings()
            is ZoraDrawerIntent.ClickProfile -> clickProfile()
        }
    }

    fun updateRecentQuestions(questions: List<String>) = intent {
        reduce { state.copy(recentQuestions = questions) }
    }

    fun setDrawerOpen(isOpen: Boolean) = intent {
        reduce { state.copy(isOpen = isOpen) }
        if (!isOpen) {
            postSideEffect(ZoraDrawerSideEffect.CloseDrawer)
        }
    }

    fun clickNewChat() = intent {
        reduce { state.copy(isOpen = false, selectedRecentQuestion = null) }
        postSideEffect(ZoraDrawerSideEffect.NavigateToNewChat)
    }

    fun clickParts() = intent {
        reduce { state.copy(isOpen = false, selectedMenuItem = ZoraDrawerMenuItemId.PARTS) }
        postSideEffect(ZoraDrawerSideEffect.NavigateToPartsFeed)
    }

    fun clickMechanic() = intent {
        reduce { state.copy(isOpen = false) }
        postSideEffect(ZoraDrawerSideEffect.NavigateToPaywall("drawer_menu_mechanic"))
    }

    fun clickGasStations() = intent {
        reduce { state.copy(isOpen = false) }
        postSideEffect(ZoraDrawerSideEffect.NavigateToPaywall("drawer_menu_gas_stations"))
    }

    fun selectRecentQuestion(question: String) = intent {
        reduce {
            state.copy(
                isOpen = false,
                selectedRecentQuestion = question,
            )
        }
        postSideEffect(ZoraDrawerSideEffect.LoadRecentChat(question))
    }

    fun clickSettings() = intent {
        reduce { state.copy(isOpen = false) }
        postSideEffect(ZoraDrawerSideEffect.NavigateToSettings)
    }

    fun clickProfile() = intent {
        reduce { state.copy(isOpen = false) }
        postSideEffect(ZoraDrawerSideEffect.NavigateToProfile)
    }
}

/**
 * Backward compatibility alias for [ZoraShiftedDrawerViewModel].
 */
typealias ShiftedDrawerViewModel = ZoraShiftedDrawerViewModel
