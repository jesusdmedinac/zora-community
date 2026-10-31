package com.jesusdmedinac.zora.ui.home

import androidx.lifecycle.ViewModel
import com.jesusdmedinac.jsontocompose.model.ComposeDocument
import com.jesusdmedinac.zora.domain.ai.SpecialistType
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
        val resolution = resolveAssistantResolution(trimmed, state.activeVehicleName)
        val assistantMessage = CopilotChatMessage.Assistant(
            text = resolution.text,
            dynamicDocument = resolution.dynamicDocument,
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
                localPreliminaryHypothesis = resolution.text,
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
                localPreliminaryHypothesis = resolution.text,
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
        val resolution = resolveAssistantResolution(symptom, state.activeVehicleName)
        val assistantMessage = CopilotChatMessage.Assistant(
            text = resolution.text,
            dynamicDocument = resolution.dynamicDocument,
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
                localPreliminaryHypothesis = resolution.text,
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
                localPreliminaryHypothesis = resolution.text,
                serializedPrescriptionJson = serialized,
                createdAtMs = createdAt,
                updatedAtMs = now,
            )
            currentSymptomId to updatedEntity
        }

        reduce {
            state.copy(
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
        fun resolveAssistantReply(prompt: String, vehicle: String): String =
            resolveAssistantResolution(prompt, vehicle).text

        fun resolveAssistantResolution(prompt: String, vehicle: String): AssistantResolution {
            return when {
                prompt.contains("fren", ignoreCase = true) || prompt.contains("brake", ignoreCase = true) ->
                    AssistantResolution(
                        text = "Análisis para $vehicle: Si el pedal vibra al frenar a alta velocidad, suele indicar alabeo en los discos de freno delanteros o desgaste irregular de balatas. Se recomienda inspección de grosor de rotores.",
                        dynamicDocument = ZoraSduiTemplates.buildSpecialistTeaserDocument(
                            type = SpecialistType.MECHANIC_SHOP,
                            warningNotice = "La revisión del sistema de frenos requiere inspección física en taller especializado por seguridad del conductor.",
                            teaserMessage = "La conexión con mecánicos certificados y diagnóstico telemático asistido estará disponible próximamente en Zora Pro.",
                        ),
                    )
                prompt.contains("aceite", ignoreCase = true) || prompt.contains("oil", ignoreCase = true) -> {
                    val isMaintenanceRecord = prompt.contains("cambi", ignoreCase = true) || prompt.contains("pus", ignoreCase = true)
                    AssistantResolution(
                        text = "Especificación de servicio para $vehicle: Capacidad 4.2 L con cambio de filtro. Viscosidad recomendada: 0W-20 sintético. Intervalo cada 8,000 km.",
                        dynamicDocument = if (isMaintenanceRecord) {
                            ZoraSduiTemplates.buildConfirmMemoryDocument("Aceite sintético 0W-20 con cambio de filtro")
                        } else null,
                    )
                }
                prompt.contains("bater", ignoreCase = true) || prompt.contains("battery", ignoreCase = true) -> {
                    val isBatteryChange = prompt.contains("nueva", ignoreCase = true) || prompt.contains("cambi", ignoreCase = true)
                    AssistantResolution(
                        text = "Diagnóstico de batería para $vehicle: Voltaje nominal 12.6V verificado. Alternador cargando dentro de rango (14.2V). Bóveda de salud al 100%.",
                        dynamicDocument = if (isBatteryChange) {
                            ZoraSduiTemplates.buildConfirmMemoryDocument("Batería 12.6V reemplazada")
                        } else null,
                    )
                }
                prompt.contains("humo", ignoreCase = true) || prompt.contains("smoke", ignoreCase = true) ->
                    AssistantResolution(
                        text = "Alerta para $vehicle: Humo azul indica quema de aceite en cámara de combustión (posibles sellos de válvula o anillos de pistón gastados).",
                        dynamicDocument = ZoraSduiTemplates.buildSpecialistTeaserDocument(
                            type = SpecialistType.MECHANIC_SHOP,
                            warningNotice = "La emisión anómala de humo puede provocar daños severos al convertidor catalítico y motor.",
                            teaserMessage = "Próximamente en Zora Pro: telemetría en tiempo real y enlace directo con talleres mecánicos especializados.",
                        ),
                    )
                prompt.contains("yonke", ignoreCase = true) || prompt.contains("pieza", ignoreCase = true) || prompt.contains("refaccion", ignoreCase = true) || prompt.contains("part", ignoreCase = true) ->
                    AssistantResolution(
                        text = "Catálogo de Yonke para $vehicle: Puedes consultar estimaciones de piezas de salvamento compatibles con la carrocería y motor de tu vehículo.",
                        dynamicDocument = ZoraSduiTemplates.buildSpecialistTeaserDocument(
                            type = SpecialistType.SALVAGE_YARD,
                            warningNotice = "Las piezas mostradas en Zora Community son hipotéticas y con fines informativos.",
                            teaserMessage = "Para consultar inventario en vivo, verificar piezas en yonkes locales reales y activar oportunidades de compra, actualiza a Zora Pro.",
                        ),
                    )
                prompt.contains("gasolin", ignoreCase = true) || prompt.contains("combustible", ignoreCase = true) ->
                    AssistantResolution(
                        text = "Orientación de combustible para $vehicle: Se recomienda gasolina de mínimo 87 octanos (Regular) o superior para un rendimiento óptimo de motor.",
                        dynamicDocument = ZoraSduiTemplates.buildSpecialistTeaserDocument(
                            type = SpecialistType.GAS_STATION,
                            warningNotice = "Zora Community ofrece datos de referencia de consumo y especificaciones de octanaje.",
                            teaserMessage = "La localización inteligente y precios de gasolineras/centros de servicio en tiempo real estarán próximamente en Zora Pro.",
                        ),
                    )
                else ->
                    AssistantResolution(
                        text = "He registrado \"$prompt\" en tu bóveda mecánica local para $vehicle. Consultando telemetría de abordo y especificaciones de taller...",
                    )
            }
        }
    }
}
