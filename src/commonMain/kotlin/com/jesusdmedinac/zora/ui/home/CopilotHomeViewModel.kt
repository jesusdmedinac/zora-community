package com.jesusdmedinac.zora.ui.home

import androidx.lifecycle.ViewModel
import com.jesusdmedinac.zora.data.local.ZoraRepositories
import com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescriptionEntity
import com.jesusdmedinac.zora.domain.repository.MechanicalPrescriptionRepository
import com.jesusdmedinac.zora.ui.home.model.CopilotChatMessage
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeIntent
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeSideEffect
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeUiState
import kotlinx.coroutines.flow.firstOrNull
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.viewmodel.orbitContainer

/**
 * ViewModel managing the state, intents, and side effects for Copilot Home via Orbit MVI.
 */
@OptIn(ExperimentalTime::class)
class CopilotHomeViewModel(
    private val repository: MechanicalPrescriptionRepository = ZoraRepositories.mechanicalPrescriptionRepository,
    private val vehicleId: String = "honda_civic_2018",
    private val timeProvider: () -> Long = { Clock.System.now().toEpochMilliseconds() },
    private val messageIdGenerator: (prefix: String) -> String = { prefix ->
        "${prefix}_${kotlin.random.Random.nextInt(100000, 999999)}"
    },
) :
    ViewModel(),
    OrbitContainerHost<CopilotHomeUiState, CopilotHomeUiState, CopilotHomeSideEffect> {

    override val container = orbitContainer<CopilotHomeUiState, CopilotHomeSideEffect>(CopilotHomeUiState())

    val uiState: kotlinx.coroutines.flow.StateFlow<CopilotHomeUiState>
        get() = container.stateFlow

    val sideEffect: kotlinx.coroutines.flow.Flow<CopilotHomeSideEffect>
        get() = container.sideEffectFlow

    fun processIntent(intent: CopilotHomeIntent) {
        when (intent) {
            is CopilotHomeIntent.UpdatePromptText -> updatePromptText(intent.text)
            is CopilotHomeIntent.ClearPromptText -> clearPromptText()
            is CopilotHomeIntent.SetDrawerOpen -> setDrawerOpen(intent.isOpen)
            is CopilotHomeIntent.SendPrompt -> sendPrompt(intent.text)
            is CopilotHomeIntent.SelectSuggestion -> selectSuggestion(intent.symptom)
            is CopilotHomeIntent.OpenNewChat -> openNewChat()
            is CopilotHomeIntent.SelectRecentChat -> selectRecentChat(intent.question)
            is CopilotHomeIntent.OpenGarage -> openGarage()
            is CopilotHomeIntent.OpenPaywall -> openPaywall(intent.sourceReason)
            is CopilotHomeIntent.RefreshTelemetry -> refreshTelemetry()
            is CopilotHomeIntent.ClearChat -> clearChat()
        }
    }

    fun updatePromptText(text: String) = intent {
        reduce { state.copy(promptText = text) }
    }

    fun clearPromptText() = intent {
        reduce { state.copy(promptText = "") }
    }

    fun setDrawerOpen(isOpen: Boolean) = intent {
        reduce { state.copy(isDrawerOpen = isOpen) }
    }

    fun sendPrompt(text: String) = intent {
        val trimmed = text.trim()
        if (trimmed.isBlank()) return@intent

        val userMessage = CopilotChatMessage.User(
            text = trimmed,
            id = messageIdGenerator("user"),
        )
        val replyText = resolveAssistantReply(trimmed, state.activeVehicleName)
        val assistantMessage = CopilotChatMessage.Assistant(
            text = replyText,
            id = messageIdGenerator("asst"),
        )

        reduce {
            state.copy(
                promptText = "",
                messages = state.messages + userMessage + assistantMessage,
            )
        }
        postSideEffect(CopilotHomeSideEffect.ScrollToBottom)

        val now = timeProvider()
        val symptomId = "sym_${now}_${kotlin.random.Random.nextInt(100, 999)}"
        val entity = MechanicalPrescriptionEntity(
            symptomId = symptomId,
            vehicleId = vehicleId,
            vinTechnicalPrefix = "2HGFC2F5",
            rawSymptomDescription = trimmed,
            localPreliminaryHypothesis = replyText,
            createdAtMs = now,
            updatedAtMs = now,
        )
        repository.insertLocalTriage(entity)
    }

    fun selectSuggestion(symptom: String) = intent {
        val userMessage = CopilotChatMessage.User(
            text = symptom,
            id = messageIdGenerator("user"),
        )
        val replyText = resolveAssistantReply(symptom, state.activeVehicleName)
        val assistantMessage = CopilotChatMessage.Assistant(
            text = replyText,
            id = messageIdGenerator("asst"),
        )

        reduce {
            state.copy(
                messages = state.messages + userMessage + assistantMessage,
            )
        }
        postSideEffect(CopilotHomeSideEffect.ScrollToBottom)

        val now = timeProvider()
        val symptomId = "sym_${now}_${kotlin.random.Random.nextInt(100, 999)}"
        val entity = MechanicalPrescriptionEntity(
            symptomId = symptomId,
            vehicleId = vehicleId,
            vinTechnicalPrefix = "2HGFC2F5",
            rawSymptomDescription = symptom,
            localPreliminaryHypothesis = replyText,
            createdAtMs = now,
            updatedAtMs = now,
        )
        repository.insertLocalTriage(entity)
    }

    fun openNewChat() = intent {
        reduce {
            state.copy(
                isDrawerOpen = false,
                messages = emptyList(),
                promptText = "",
            )
        }
    }

    fun selectRecentChat(question: String) = intent {
        val pastHypothesis = try {
            repository.observeByVehicle(vehicleId).firstOrNull()?.find {
                it.rawSymptomDescription.equals(question, ignoreCase = true)
            }?.localPreliminaryHypothesis
        } catch (_: Throwable) {
            null
        }

        val assistantText = pastHypothesis ?: resolveAssistantReply(question, state.activeVehicleName)

        val userMessage = CopilotChatMessage.User(
            text = question,
            id = messageIdGenerator("user"),
        )
        val assistantMessage = CopilotChatMessage.Assistant(
            text = assistantText,
            id = messageIdGenerator("asst"),
        )

        reduce {
            state.copy(
                isDrawerOpen = false,
                messages = listOf(userMessage, assistantMessage),
                promptText = "",
            )
        }
        postSideEffect(CopilotHomeSideEffect.ScrollToBottom)
    }

    fun openGarage() = intent {
        reduce { state.copy(isDrawerOpen = false) }
        postSideEffect(CopilotHomeSideEffect.NavigateToGarage)
    }

    fun openPaywall(sourceReason: String? = null) = intent {
        reduce { state.copy(isDrawerOpen = false) }
        postSideEffect(CopilotHomeSideEffect.NavigateToPaywall(sourceReason))
    }

    fun refreshTelemetry() = intent {
        reduce {
            state.copy(
                batteryVoltage = "12.6 V",
                dtcCount = "0",
                prescriptionStatus = "Vault OK",
                isCanBusConnected = true,
            )
        }
    }

    fun clearChat() = intent {
        reduce { state.copy(messages = emptyList()) }
    }

    companion object {
        fun resolveAssistantReply(prompt: String, vehicle: String): String {
            return when {
                prompt.contains("freno", ignoreCase = true) || prompt.contains("brake", ignoreCase = true) ->
                    "Análisis para $vehicle: Si el pedal vibra al frenar a alta velocidad, suele indicar alabeo en los discos de freno delanteros o desgaste irregular de balatas. Se recomienda inspección de grosor de rotores."
                prompt.contains("aceite", ignoreCase = true) || prompt.contains("oil", ignoreCase = true) ->
                    "Especificación de servicio para $vehicle: Capacidad 4.2 L con cambio de filtro. Viscosidad recomendada: 0W-20 sintético. Intervalo cada 8,000 km."
                prompt.contains("batería", ignoreCase = true) || prompt.contains("battery", ignoreCase = true) ->
                    "Diagnóstico de batería para $vehicle: Voltaje nominal 12.6V verificado. Alternador cargando dentro de rango (14.2V). Bóveda de salud al 100%."
                prompt.contains("humo", ignoreCase = true) || prompt.contains("smoke", ignoreCase = true) ->
                    "Alerta para $vehicle: Humo azul indica quema de aceite en cámara de combustión (posibles sellos de válvula o anillos de pistón gastados)."
                else ->
                    "He registrado \"$prompt\" en tu bóveda mecánica local para $vehicle. Consultando telemetría de abordo y especificaciones de taller..."
            }
        }
    }
}
