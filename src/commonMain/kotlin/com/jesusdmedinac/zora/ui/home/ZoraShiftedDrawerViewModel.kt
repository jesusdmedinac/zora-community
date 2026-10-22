package com.jesusdmedinac.zora.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesusdmedinac.zora.domain.repository.MechanicalPrescriptionRepository
import com.jesusdmedinac.zora.ui.home.component.defaultRecentQuestions
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerIntent
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerMenuItemId
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerSideEffect
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
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

    val uiState: StateFlow<ZoraDrawerUiState>
        get() = container.stateFlow

    val sideEffect: Flow<ZoraDrawerSideEffect>
        get() = container.sideEffectFlow

    fun processIntent(intent: ZoraDrawerIntent) {
        when (intent) {
            is ZoraDrawerIntent.SetDrawerOpen -> setDrawerOpen(intent.isOpen)
            is ZoraDrawerIntent.OpenDrawer -> setDrawerOpen(true)
            is ZoraDrawerIntent.CloseDrawer -> setDrawerOpen(false)
            is ZoraDrawerIntent.LoadRecentQuestions -> loadRecentQuestions()
            is ZoraDrawerIntent.ClickNewChat -> clickNewChat()
            is ZoraDrawerIntent.ClickParts -> clickParts()
            is ZoraDrawerIntent.ClickMechanic -> clickMechanic()
            is ZoraDrawerIntent.ClickGasStations -> clickGasStations()
            is ZoraDrawerIntent.SelectRecentQuestion -> selectRecentQuestion(intent.question)
            is ZoraDrawerIntent.LongClickRecentQuestion -> longClickRecentQuestion(intent.question)
            is ZoraDrawerIntent.DismissRecentQuestionOptions -> dismissRecentQuestionOptions()
            is ZoraDrawerIntent.ShareRecentQuestion -> shareRecentQuestion(intent.question)
            is ZoraDrawerIntent.PinRecentQuestion -> pinRecentQuestion(intent.question)
            is ZoraDrawerIntent.StartRenameRecentQuestion -> startRenameRecentQuestion(intent.question)
            is ZoraDrawerIntent.ConfirmRenameRecentQuestion -> confirmRenameRecentQuestion(intent.oldQuestion, intent.newQuestion)
            is ZoraDrawerIntent.CancelRenameRecentQuestion -> cancelRenameRecentQuestion()
            is ZoraDrawerIntent.DeleteRecentQuestion -> deleteRecentQuestion(intent.question)
            is ZoraDrawerIntent.ClickSettings -> clickSettings()
            is ZoraDrawerIntent.ClickProfile -> clickProfile()
        }
    }

    fun loadRecentQuestions() = intent {
        repository?.let { repo ->
            val prescriptions = repo.observeByVehicle(vehicleId).firstOrNull().orEmpty()
            val questions = if (prescriptions.isNotEmpty()) {
                prescriptions.map { it.rawSymptomDescription }
            } else {
                defaultRecentQuestions()
            }
            if (state.recentQuestions != questions) {
                reduce { state.copy(recentQuestions = questions) }
            }
        }
    }

    fun updateRecentQuestions(questions: List<String>) = intent {
        if (state.recentQuestions != questions) {
            reduce { state.copy(recentQuestions = questions) }
        }
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

    fun longClickRecentQuestion(question: String) = intent {
        reduce { state.copy(activeBottomSheetQuestion = question) }
    }

    fun dismissRecentQuestionOptions() = intent {
        reduce { state.copy(activeBottomSheetQuestion = null) }
    }

    fun shareRecentQuestion(question: String) = intent {
        reduce { state.copy(activeBottomSheetQuestion = null) }
        postSideEffect(ZoraDrawerSideEffect.ShareChat(question))
    }

    fun pinRecentQuestion(question: String) = intent {
        val currentPinned = state.pinnedQuestions
        val newPinned = if (currentPinned.contains(question)) {
            currentPinned - question
        } else {
            currentPinned + question
        }
        reduce {
            state.copy(
                activeBottomSheetQuestion = null,
                pinnedQuestions = newPinned,
            )
        }
    }

    fun startRenameRecentQuestion(question: String) = intent {
        reduce {
            state.copy(
                activeBottomSheetQuestion = null,
                activeRenameQuestion = question,
            )
        }
    }

    fun confirmRenameRecentQuestion(oldQuestion: String, newQuestion: String) = intent {
        val trimmed = newQuestion.trim()
        if (trimmed.isBlank() || trimmed == oldQuestion) {
            reduce { state.copy(activeRenameQuestion = null) }
            return@intent
        }
        val updatedQuestions = state.recentQuestions.map { if (it == oldQuestion) trimmed else it }
        val updatedPinned = state.pinnedQuestions.map { if (it == oldQuestion) trimmed else it }
        val updatedSelected = if (state.selectedRecentQuestion == oldQuestion) trimmed else state.selectedRecentQuestion

        reduce {
            state.copy(
                recentQuestions = updatedQuestions,
                pinnedQuestions = updatedPinned,
                selectedRecentQuestion = updatedSelected,
                activeRenameQuestion = null,
            )
        }

        repository?.let { repo ->
            try {
                val existing = repo.observeByVehicle(vehicleId).firstOrNull()?.find {
                    it.rawSymptomDescription == oldQuestion
                }
                if (existing != null) {
                    repo.insertLocalTriage(existing.copy(rawSymptomDescription = trimmed))
                }
            } catch (_: Throwable) {
                // Keep local state update
            }
        }
    }

    fun cancelRenameRecentQuestion() = intent {
        reduce { state.copy(activeRenameQuestion = null) }
    }

    fun deleteRecentQuestion(question: String) = intent {
        val updatedQuestions = state.recentQuestions.filterNot { it == question }
        val updatedPinned = state.pinnedQuestions.filterNot { it == question }
        val updatedSelected = if (state.selectedRecentQuestion == question) null else state.selectedRecentQuestion

        reduce {
            state.copy(
                activeBottomSheetQuestion = null,
                recentQuestions = updatedQuestions,
                pinnedQuestions = updatedPinned,
                selectedRecentQuestion = updatedSelected,
            )
        }

        repository?.let { repo ->
            try {
                val existing = repo.observeByVehicle(vehicleId).firstOrNull()?.find {
                    it.rawSymptomDescription == question
                }
                if (existing != null) {
                    repo.purgeById(existing.symptomId)
                }
            } catch (_: Throwable) {
                // Keep local state update
            }
        }
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
