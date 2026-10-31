package com.jesusdmedinac.zora.ui.home

import androidx.lifecycle.ViewModel
import com.jesusdmedinac.jsontocompose.model.ComposeDocument
import com.jesusdmedinac.zora.domain.ai.CopilotReply
import com.jesusdmedinac.zora.domain.ai.GarageVehicleContext
import com.jesusdmedinac.zora.domain.ai.KoogZoraCopilotAgent
import com.jesusdmedinac.zora.domain.ai.SpecialistType
import com.jesusdmedinac.zora.domain.ai.ZoraCopilotAgent
import com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescriptionEntity
import com.jesusdmedinac.zora.domain.repository.MechanicalPrescriptionRepository
import com.jesusdmedinac.zora.ui.home.model.CopilotChatMessage
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeIntent
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeSideEffect
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeUiState
import com.jesusdmedinac.zora.ui.home.sdui.ZoraSduiTemplates
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.viewmodel.orbitContainer

/**
 * ViewModel managing the state, intents, and side effects for Copilot Home via Orbit MVI.
 */
@OptIn(ExperimentalTime::class)
class CopilotHomeViewModel(
    private val repository: MechanicalPrescriptionRepository? = null,
    private val copilotAgent: ZoraCopilotAgent = KoogZoraCopilotAgent(),
    private val vehicleId: String = "honda_civic_2018",
    private val timeProvider: () -> Long = { Clock.System.now().toEpochMilliseconds() },
    private val messageIdGenerator: (prefix: String) -> String = { prefix ->
        "${prefix}_${kotlin.random.Random.nextInt(100000, 999999)}"
    },
    private val symptomIdGenerator: (now: Long) -> String = { now ->
        "sym_${now}_${kotlin.random.Random.nextInt(100, 999)}"
    },
) :
    ViewModel(),
    OrbitContainerHost<CopilotHomeUiState, CopilotHomeUiState, CopilotHomeSideEffect> {

    private val json = Json { ignoreUnknownKeys = true }

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
            is CopilotHomeIntent.ConfirmGarageMemory -> confirmGarageMemory(intent.memory)
            is CopilotHomeIntent.DismissGarageMemory -> dismissGarageMemory(intent.memory)
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
        val garageContext = GarageVehicleContext(
            make = "Honda",
            model = "Civic",
            year = 2018,
            learnedMemories = emptyList(),
            vehicleDisplayName = state.activeVehicleName,
        )
        val reply = copilotAgent.interact(trimmed, garageContext)
        val dynamicDocument = Companion.buildDynamicDocumentForReply(reply)
        val assistantMessage = CopilotChatMessage.Assistant(
            text = reply.message,
            dynamicDocument = dynamicDocument,
            id = messageIdGenerator("asst"),
        )

        val updatedMessages = state.messages + userMessage + assistantMessage
        val now = timeProvider()
        val currentSymptomId = state.activeSymptomId

        val (targetSymptomId, entity) = if (currentSymptomId == null) {
            val newSymptomId = symptomIdGenerator(now)
            val serialized = json.encodeToString(updatedMessages)
            val newEntity = MechanicalPrescriptionEntity(
                symptomId = newSymptomId,
                vehicleId = vehicleId,
                vinTechnicalPrefix = "2HGFC2F5",
                rawSymptomDescription = trimmed,
                localPreliminaryHypothesis = reply.message,
                serializedPrescriptionJson = serialized,
                createdAtMs = now,
                updatedAtMs = now,
            )
            newSymptomId to newEntity
        } else {
            val existing = repository?.selectById(currentSymptomId)
            val createdAt = existing?.createdAtMs ?: now
            val title = existing?.rawSymptomDescription ?: trimmed
            val serialized = json.encodeToString(updatedMessages)
            val updatedEntity = MechanicalPrescriptionEntity(
                symptomId = currentSymptomId,
                vehicleId = vehicleId,
                vinTechnicalPrefix = "2HGFC2F5",
                rawSymptomDescription = title,
                localPreliminaryHypothesis = reply.message,
                serializedPrescriptionJson = serialized,
                createdAtMs = createdAt,
                updatedAtMs = now,
            )
            currentSymptomId to updatedEntity
        }

        reduce {
            state.copy(
                promptText = "",
                messages = updatedMessages,
                activeSymptomId = targetSymptomId,
            )
        }
        postSideEffect(CopilotHomeSideEffect.ScrollToBottom)

        repository?.insertLocalTriage(entity)
    }

    fun selectSuggestion(symptom: String) = intent {
        val userMessage = CopilotChatMessage.User(
            text = symptom,
            id = messageIdGenerator("user"),
        )
        val garageContext = GarageVehicleContext(
            make = "Honda",
            model = "Civic",
            year = 2018,
            learnedMemories = emptyList(),
            vehicleDisplayName = state.activeVehicleName,
        )
        val reply = copilotAgent.interact(symptom, garageContext)
        val dynamicDocument = Companion.buildDynamicDocumentForReply(reply)
        val assistantMessage = CopilotChatMessage.Assistant(
            text = reply.message,
            dynamicDocument = dynamicDocument,
            id = messageIdGenerator("asst"),
        )

        val updatedMessages = state.messages + userMessage + assistantMessage
        val now = timeProvider()
        val currentSymptomId = state.activeSymptomId

        val (targetSymptomId, entity) = if (currentSymptomId == null) {
            val newSymptomId = symptomIdGenerator(now)
            val serialized = json.encodeToString(updatedMessages)
            val newEntity = MechanicalPrescriptionEntity(
                symptomId = newSymptomId,
                vehicleId = vehicleId,
                vinTechnicalPrefix = "2HGFC2F5",
                rawSymptomDescription = symptom,
                localPreliminaryHypothesis = reply.message,
                serializedPrescriptionJson = serialized,
                createdAtMs = now,
                updatedAtMs = now,
            )
            newSymptomId to newEntity
        } else {
            val existing = repository?.selectById(currentSymptomId)
            val createdAt = existing?.createdAtMs ?: now
            val title = existing?.rawSymptomDescription ?: symptom
            val serialized = json.encodeToString(updatedMessages)
            val updatedEntity = MechanicalPrescriptionEntity(
                symptomId = currentSymptomId,
                vehicleId = vehicleId,
                vinTechnicalPrefix = "2HGFC2F5",
                rawSymptomDescription = title,
                localPreliminaryHypothesis = reply.message,
                serializedPrescriptionJson = serialized,
                createdAtMs = createdAt,
                updatedAtMs = now,
            )
            currentSymptomId to updatedEntity
        }

        reduce {
            state.copy(
                promptText = "",
                messages = updatedMessages,
                activeSymptomId = targetSymptomId,
            )
        }
        postSideEffect(CopilotHomeSideEffect.ScrollToBottom)

        repository?.insertLocalTriage(entity)
    }

    fun openNewChat() = intent {
        reduce {
            state.copy(
                isDrawerOpen = false,
                messages = emptyList(),
                promptText = "",
                activeSymptomId = null,
            )
        }
    }

    fun selectRecentChat(question: String) = intent {
        val matchingEntity = try {
            repository?.observeByVehicle(vehicleId)?.firstOrNull()?.find {
                it.rawSymptomDescription.equals(question, ignoreCase = true)
            }
        } catch (_: Throwable) {
            null
        }

        val restoredMessages: List<CopilotChatMessage>? = matchingEntity?.serializedPrescriptionJson?.let { rawJson ->
            try {
                json.decodeFromString<List<CopilotChatMessage>>(rawJson)
            } catch (_: Throwable) {
                null
            }
        }

        val messages = if (!restoredMessages.isNullOrEmpty()) {
            restoredMessages
        } else {
            val assistantText = matchingEntity?.localPreliminaryHypothesis ?: resolveAssistantReply(question, state.activeVehicleName)
            listOf(
                CopilotChatMessage.User(
                    text = question,
                    id = messageIdGenerator("user"),
                ),
                CopilotChatMessage.Assistant(
                    text = assistantText,
                    id = messageIdGenerator("asst"),
                ),
            )
        }

        reduce {
            state.copy(
                isDrawerOpen = false,
                messages = messages,
                promptText = "",
                activeSymptomId = matchingEntity?.symptomId,
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
        reduce {
            state.copy(
                messages = emptyList(),
                activeSymptomId = null,
            )
        }
    }

    fun confirmGarageMemory(memory: String) = intent {
        val ackMessage = CopilotChatMessage.Assistant(
            text = "Confirmado: He guardado \"$memory\" en la memoria de tu Garaje Virtual.",
            id = messageIdGenerator("asst"),
        )
        val updatedMessages = state.messages + ackMessage
        val now = timeProvider()
        val currentSymptomId = state.activeSymptomId
        if (currentSymptomId != null) {
            val existing = repository?.selectById(currentSymptomId)
            val createdAt = existing?.createdAtMs ?: now
            val title = existing?.rawSymptomDescription ?: memory
            val serialized = json.encodeToString(updatedMessages)
            val updatedEntity = MechanicalPrescriptionEntity(
                symptomId = currentSymptomId,
                vehicleId = vehicleId,
                vinTechnicalPrefix = "2HGFC2F5",
                rawSymptomDescription = title,
                localPreliminaryHypothesis = ackMessage.text,
                serializedPrescriptionJson = serialized,
                createdAtMs = createdAt,
                updatedAtMs = now,
            )
            repository?.insertLocalTriage(updatedEntity)
        }

        reduce {
            state.copy(messages = updatedMessages)
        }
        postSideEffect(CopilotHomeSideEffect.ScrollToBottom)
    }

    fun dismissGarageMemory(memory: String) = intent {
        val ackMessage = CopilotChatMessage.Assistant(
            text = "Entendido, no he guardado \"$memory\" en tu Garaje.",
            id = messageIdGenerator("asst"),
        )
        val updatedMessages = state.messages + ackMessage
        reduce {
            state.copy(messages = updatedMessages)
        }
        postSideEffect(CopilotHomeSideEffect.ScrollToBottom)
    }

    data class AssistantResolution(
        val text: String,
        val dynamicDocument: ComposeDocument? = null,
    )

    companion object {
        private val defaultAgent = KoogZoraCopilotAgent()

        fun buildDynamicDocumentForReply(reply: CopilotReply): ComposeDocument? {
            return when {
                reply.suggestedSpecialist == SpecialistType.MECHANIC_SHOP ->
                    ZoraSduiTemplates.buildSpecialistTeaserDocument(
                        type = SpecialistType.MECHANIC_SHOP,
                        warningNotice = "La revisión del sistema de frenos requiere inspección física en taller especializado por seguridad del conductor.",
                        teaserMessage = "La conexión con mecánicos certificados y diagnóstico telemático asistido estará disponible próximamente en Zora Pro.",
                    )
                reply.suggestedSpecialist == SpecialistType.SALVAGE_YARD ->
                    ZoraSduiTemplates.buildSpecialistTeaserDocument(
                        type = SpecialistType.SALVAGE_YARD,
                        warningNotice = "Las piezas mostradas en Zora Community son hipotéticas y con fines informativos.",
                        teaserMessage = "Para consultar inventario en vivo, verificar piezas en yonkes locales reales y activar oportunidades de compra, actualiza a Zora Pro.",
                    )
                reply.suggestedSpecialist == SpecialistType.GAS_STATION ->
                    ZoraSduiTemplates.buildSpecialistTeaserDocument(
                        type = SpecialistType.GAS_STATION,
                        warningNotice = "Zora Community ofrece datos de referencia de consumo y especificaciones de octanaje.",
                        teaserMessage = "La localización inteligente y precios de gasolineras/centros de servicio en tiempo real estarán próximamente en Zora Pro.",
                    )
                reply.memoryToConfirm != null ->
                    ZoraSduiTemplates.buildConfirmMemoryDocument(reply.memoryToConfirm)
                else -> null
            }
        }

        fun resolveAssistantReply(prompt: String, vehicle: String): String =
            resolveAssistantResolution(prompt, vehicle).text

        fun resolveAssistantResolution(prompt: String, vehicle: String): AssistantResolution {
            val garageContext = GarageVehicleContext(
                make = "Honda",
                model = "Civic",
                year = 2018,
                vehicleDisplayName = vehicle,
            )
            val reply = defaultAgent.resolveLocalHeuristic(prompt, garageContext)
            return AssistantResolution(
                text = reply.message,
                dynamicDocument = buildDynamicDocumentForReply(reply),
            )
        }
    }
}
