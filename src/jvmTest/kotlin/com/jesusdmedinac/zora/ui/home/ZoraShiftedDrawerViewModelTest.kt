package com.jesusdmedinac.zora.ui.home

import com.jesusdmedinac.zora.data.local.InMemoryMechanicalPrescriptionRepository
import com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescriptionEntity
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerIntent
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerMenuItemId
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerSideEffect
import com.jesusdmedinac.zora.ui.home.model.ZoraDrawerUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.orbitmvi.orbit.test.testWithInternalState
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ZoraShiftedDrawerViewModelTest {

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

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

    @Test
    fun `LongClickRecentQuestion opens options bottom sheet`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        val question = "¿Por qué rechinan mis frenos?"
        viewModel.testWithInternalState(this, initialState = ZoraDrawerUiState(activeBottomSheetQuestion = null)) {
            containerHost.processIntent(ZoraDrawerIntent.LongClickRecentQuestion(question))
            expectInternalState { copy(activeBottomSheetQuestion = question) }
        }
    }

    @Test
    fun `DismissRecentQuestionOptions closes options bottom sheet`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        val question = "¿Por qué rechinan mis frenos?"
        viewModel.testWithInternalState(this, initialState = ZoraDrawerUiState(activeBottomSheetQuestion = question)) {
            containerHost.processIntent(ZoraDrawerIntent.DismissRecentQuestionOptions)
            expectInternalState { copy(activeBottomSheetQuestion = null) }
        }
    }

    @Test
    fun `ShareRecentQuestion emits ShareChat side effect and closes sheet`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        val question = "¿Por qué rechinan mis frenos?"
        viewModel.testWithInternalState(this, initialState = ZoraDrawerUiState(activeBottomSheetQuestion = question)) {
            containerHost.processIntent(ZoraDrawerIntent.ShareRecentQuestion(question))
            expectInternalState { copy(activeBottomSheetQuestion = null) }
            expectSideEffect(ZoraDrawerSideEffect.ShareChat(question))
        }
    }

    @Test
    fun `PinRecentQuestion toggles pinned list`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        val question = "¿Por qué rechinan mis frenos?"
        viewModel.testWithInternalState(
            this,
            initialState = ZoraDrawerUiState(activeBottomSheetQuestion = question, pinnedQuestions = emptyList()),
        ) {
            containerHost.processIntent(ZoraDrawerIntent.PinRecentQuestion(question))
            expectInternalState { copy(activeBottomSheetQuestion = null, pinnedQuestions = listOf(question)) }

            containerHost.processIntent(ZoraDrawerIntent.PinRecentQuestion(question))
            expectInternalState { copy(activeBottomSheetQuestion = null, pinnedQuestions = emptyList()) }
        }
    }

    @Test
    fun `StartRenameRecentQuestion closes sheet and opens rename dialog`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        val question = "¿Por qué rechinan mis frenos?"
        viewModel.testWithInternalState(this, initialState = ZoraDrawerUiState(activeBottomSheetQuestion = question)) {
            containerHost.processIntent(ZoraDrawerIntent.StartRenameRecentQuestion(question))
            expectInternalState { copy(activeBottomSheetQuestion = null, activeRenameQuestion = question) }
        }
    }

    @Test
    fun `ConfirmRenameRecentQuestion updates title in state`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        val oldQuestion = "Antigua consulta"
        val newQuestion = "Nueva consulta renombrada"
        val initialState = ZoraDrawerUiState(
            recentQuestions = listOf(oldQuestion),
            pinnedQuestions = listOf(oldQuestion),
            selectedRecentQuestion = oldQuestion,
            activeRenameQuestion = oldQuestion,
        )

        viewModel.testWithInternalState(this, initialState = initialState) {
            containerHost.processIntent(ZoraDrawerIntent.ConfirmRenameRecentQuestion(oldQuestion, newQuestion))
            expectInternalState {
                copy(
                    recentQuestions = listOf(newQuestion),
                    pinnedQuestions = listOf(newQuestion),
                    selectedRecentQuestion = newQuestion,
                    activeRenameQuestion = null,
                )
            }
        }
    }

    @Test
    fun `ConfirmRenameRecentQuestion synchronizes updated title to repository`() = runTest {
        val repo = InMemoryMechanicalPrescriptionRepository()
        val oldQuestion = "Antigua consulta"
        val newQuestion = "Nueva consulta renombrada"
        val entity = MechanicalPrescriptionEntity(
            symptomId = "sym_rename_test",
            vehicleId = "honda_civic_2018",
            vinTechnicalPrefix = "2HGFC2F5",
            rawSymptomDescription = oldQuestion,
            localPreliminaryHypothesis = "Hypothesis",
            createdAtMs = 1000L,
            updatedAtMs = 1000L,
        )
        repo.insertLocalTriage(entity)

        val initialState = ZoraDrawerUiState(
            recentQuestions = listOf(oldQuestion),
            pinnedQuestions = listOf(oldQuestion),
            selectedRecentQuestion = oldQuestion,
            activeRenameQuestion = oldQuestion,
        )
        val viewModel = ZoraShiftedDrawerViewModel(
            initialState = initialState,
            repository = repo,
            vehicleId = "honda_civic_2018",
        )

        viewModel.testWithInternalState(this, initialState = initialState) {
            containerHost.processIntent(ZoraDrawerIntent.ConfirmRenameRecentQuestion(oldQuestion, newQuestion))
            expectInternalState {
                copy(
                    recentQuestions = listOf(newQuestion),
                    pinnedQuestions = listOf(newQuestion),
                    selectedRecentQuestion = newQuestion,
                    activeRenameQuestion = null,
                )
            }
        }

        val updatedEntity = repo.observeByVehicle("honda_civic_2018").first().first()
        assertEquals(newQuestion, updatedEntity.rawSymptomDescription)
    }

    @Test
    fun `CancelRenameRecentQuestion closes rename dialog`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        val question = "¿Por qué rechinan mis frenos?"
        viewModel.testWithInternalState(this, initialState = ZoraDrawerUiState(activeRenameQuestion = question)) {
            containerHost.processIntent(ZoraDrawerIntent.CancelRenameRecentQuestion)
            expectInternalState { copy(activeRenameQuestion = null) }
        }
    }

    @Test
    fun `DeleteRecentQuestion removes question from state`() = runTest {
        val viewModel = ZoraShiftedDrawerViewModel()
        val question = "Consulta a borrar"
        val initialState = ZoraDrawerUiState(
            recentQuestions = listOf(question, "Otra consulta"),
            pinnedQuestions = listOf(question),
            selectedRecentQuestion = question,
            activeBottomSheetQuestion = question,
        )

        viewModel.testWithInternalState(this, initialState = initialState) {
            containerHost.processIntent(ZoraDrawerIntent.DeleteRecentQuestion(question))
            expectInternalState {
                copy(
                    activeBottomSheetQuestion = null,
                    recentQuestions = listOf("Otra consulta"),
                    pinnedQuestions = emptyList(),
                    selectedRecentQuestion = null,
                )
            }
        }
    }

    @Test
    fun `DeleteRecentQuestion purges entity from repository`() = runTest {
        val repo = InMemoryMechanicalPrescriptionRepository()
        val question = "Consulta a borrar"
        val entity = MechanicalPrescriptionEntity(
            symptomId = "sym_delete_test",
            vehicleId = "honda_civic_2018",
            vinTechnicalPrefix = "2HGFC2F5",
            rawSymptomDescription = question,
            localPreliminaryHypothesis = "Hypothesis",
            createdAtMs = 1000L,
            updatedAtMs = 1000L,
        )
        repo.insertLocalTriage(entity)

        val initialState = ZoraDrawerUiState(
            recentQuestions = listOf(question, "Otra consulta"),
            pinnedQuestions = listOf(question),
            selectedRecentQuestion = question,
            activeBottomSheetQuestion = question,
        )
        val viewModel = ZoraShiftedDrawerViewModel(
            initialState = initialState,
            repository = repo,
            vehicleId = "honda_civic_2018",
        )

        viewModel.testWithInternalState(this, initialState = initialState) {
            containerHost.processIntent(ZoraDrawerIntent.DeleteRecentQuestion(question))
            expectInternalState {
                copy(
                    activeBottomSheetQuestion = null,
                    recentQuestions = listOf("Otra consulta"),
                    pinnedQuestions = emptyList(),
                    selectedRecentQuestion = null,
                )
            }
        }

        val remaining = repo.observeByVehicle("honda_civic_2018").first()
        assertEquals(emptyList(), remaining)
    }

    @Test
    fun `LoadRecentQuestions loads seeded prescriptions from repository`() = runTest {
        val repo = InMemoryMechanicalPrescriptionRepository()
        val question = "¿Por qué vibra el volante?"
        val entity = MechanicalPrescriptionEntity(
            symptomId = "sym_load_test",
            vehicleId = "honda_civic_2018",
            vinTechnicalPrefix = "2HGFC2F5",
            rawSymptomDescription = question,
            localPreliminaryHypothesis = "Hypothesis",
            createdAtMs = 1000L,
            updatedAtMs = 1000L,
        )
        repo.insertLocalTriage(entity)

        val viewModel = ZoraShiftedDrawerViewModel(repository = repo, vehicleId = "honda_civic_2018")
        viewModel.testWithInternalState(this) {
            containerHost.processIntent(ZoraDrawerIntent.LoadRecentQuestions)
            expectInternalState { copy(recentQuestions = listOf(question)) }
        }
    }

    @Test
    fun `LoadRecentQuestions and SetDrawerOpen reactively load recent questions from repository`() = runTest {
        val repo = InMemoryMechanicalPrescriptionRepository()
        val question1 = "Primera consulta"
        val question2 = "Segunda consulta"

        val entity1 = MechanicalPrescriptionEntity(
            symptomId = "sym_obs_1",
            vehicleId = "honda_civic_2018",
            vinTechnicalPrefix = "2HGFC2F5",
            rawSymptomDescription = question1,
            localPreliminaryHypothesis = "Hypothesis 1",
            createdAtMs = 1000L,
            updatedAtMs = 1000L,
        )
        repo.insertLocalTriage(entity1)

        val viewModel = ZoraShiftedDrawerViewModel(repository = repo, vehicleId = "honda_civic_2018")
        viewModel.testWithInternalState(this) {
            containerHost.processIntent(ZoraDrawerIntent.LoadRecentQuestions)
            expectInternalState { copy(recentQuestions = listOf(question1)) }

            val entity2 = MechanicalPrescriptionEntity(
                symptomId = "sym_obs_2",
                vehicleId = "honda_civic_2018",
                vinTechnicalPrefix = "2HGFC2F5",
                rawSymptomDescription = question2,
                localPreliminaryHypothesis = "Hypothesis 2",
                createdAtMs = 2000L,
                updatedAtMs = 2000L,
            )
            repo.insertLocalTriage(entity2)
            expectInternalState { copy(recentQuestions = listOf(question2, question1)) }

            containerHost.processIntent(ZoraDrawerIntent.SetDrawerOpen(true))
            expectInternalState { copy(isOpen = true, recentQuestions = listOf(question2, question1)) }
        }
    }
}
