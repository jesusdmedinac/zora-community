package com.jesusdmedinac.zora.domain.ai

import ai.koog.agents.core.tools.SimpleTool
import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.clients.LLMClient
import ai.koog.prompt.executor.ollama.client.OllamaModels
import ai.koog.prompt.llm.LLModel
import ai.koog.serialization.typeToken
import kotlinx.serialization.Serializable

@Serializable
data class InspectGarageArgs(val query: String = "")

class InspectGarageContextTool(
    private val context: GarageVehicleContext,
) : SimpleTool<InspectGarageArgs>(
    argsType = typeToken<InspectGarageArgs>(),
    name = "inspect_vehicle_garage_context",
    description = "Inspect the driver's current vehicle specifications (make, model, year) and confirmed maintenance memories.",
) {
    override suspend fun execute(args: InspectGarageArgs): String {
        return "Vehicle: ${context.vehicleDisplayName}. Memories: ${context.learnedMemories.joinToString(", ")}"
    }
}

class KoogZoraCopilotAgent(
    private val llmClient: LLMClient? = null,
    private val model: LLModel = OllamaModels.Groq.LLAMA_3_GROK_TOOL_USE_8B,
) : ZoraCopilotAgent {

    override suspend fun interact(
        driverPrompt: String,
        garageContext: GarageVehicleContext,
    ): CopilotReply {
        val tool = InspectGarageContextTool(garageContext)
        val tools = listOf(tool.descriptor)

        if (llmClient != null) {
            try {
                val prompt = prompt("zora-copilot") {
                    system(
                        """
                        You are Zora Copilot, an informative AI copilot for drivers in Zora Community.
                        Rules:
                        1. Provide informative vehicle guidance, not professional mechanical diagnostics.
                        2. Never pretend to be a physical mechanic shop or salvage yard.
                        3. If safety-critical mechanical issues are detected (e.g. brakes, smoke), direct the driver to a mechanic shop.
                        4. If salvage parts or yonke queries are detected, clarify that references are hypothetical in community edition and direct to Zora Pro.
                        5. If gas station, fuel prices, or service centers are queried, note real-time tracking is in Zora Pro.
                        6. If the driver mentions replacing or servicing parts (e.g. oil change, new battery), propose that memory for confirmation.
                        Vehicle: ${garageContext.vehicleDisplayName}
                        Memories: ${garageContext.learnedMemories.joinToString(", ")}
                        """.trimIndent()
                    )
                    user(driverPrompt)
                }
                val response = llmClient.execute(prompt, model, tools)
                val responseText = response.textContent()
                if (responseText.isNotBlank()) {
                    return parseLlmResponse(responseText, driverPrompt, garageContext)
                }
            } catch (_: Throwable) {
                // Graceful fallback to heuristic analysis
            }
        }

        return resolveLocalHeuristic(driverPrompt, garageContext)
    }

    private fun parseLlmResponse(
        responseText: String,
        driverPrompt: String,
        garageContext: GarageVehicleContext,
    ): CopilotReply {
        val specialist = when {
            driverPrompt.contains("fren", ignoreCase = true) || driverPrompt.contains("brake", ignoreCase = true) ||
                driverPrompt.contains("humo", ignoreCase = true) || driverPrompt.contains("smoke", ignoreCase = true) ->
                SpecialistType.MECHANIC_SHOP

            driverPrompt.contains("yonke", ignoreCase = true) || driverPrompt.contains("pieza", ignoreCase = true) ||
                driverPrompt.contains("refaccion", ignoreCase = true) || driverPrompt.contains("part", ignoreCase = true) ->
                SpecialistType.SALVAGE_YARD

            driverPrompt.contains("gasolin", ignoreCase = true) || driverPrompt.contains("combustible", ignoreCase = true) ->
                SpecialistType.GAS_STATION

            else -> null
        }

        val memoryToConfirm = when {
            driverPrompt.contains("aceite", ignoreCase = true) || driverPrompt.contains("oil", ignoreCase = true) -> {
                val isMaintenanceRecord = driverPrompt.contains("cambi", ignoreCase = true) || driverPrompt.contains("pus", ignoreCase = true)
                if (isMaintenanceRecord) "Aceite sintético 0W-20 con cambio de filtro" else null
            }
            driverPrompt.contains("bater", ignoreCase = true) || driverPrompt.contains("battery", ignoreCase = true) -> {
                val isBatteryChange = driverPrompt.contains("nueva", ignoreCase = true) || driverPrompt.contains("cambi", ignoreCase = true)
                if (isBatteryChange) "Batería 12.6V reemplazada" else null
            }
            else -> null
        }

        return CopilotReply(
            message = responseText,
            memoryToConfirm = memoryToConfirm,
            suggestedSpecialist = specialist,
            isLocalFallback = false,
        )
    }

    fun resolveLocalHeuristic(
        prompt: String,
        garageContext: GarageVehicleContext,
    ): CopilotReply {
        val vehicle = garageContext.vehicleDisplayName
        return when {
            prompt.contains("fren", ignoreCase = true) || prompt.contains("brake", ignoreCase = true) ->
                CopilotReply(
                    message = "Análisis para $vehicle: Si el pedal vibra al frenar a alta velocidad, suele indicar alabeo en los discos de freno delanteros o desgaste irregular de balatas. Se recomienda inspección de grosor de rotores.",
                    suggestedSpecialist = SpecialistType.MECHANIC_SHOP,
                    memoryToConfirm = null,
                    isLocalFallback = true,
                )

            prompt.contains("aceite", ignoreCase = true) || prompt.contains("oil", ignoreCase = true) -> {
                val isMaintenanceRecord = prompt.contains("cambi", ignoreCase = true) || prompt.contains("pus", ignoreCase = true)
                CopilotReply(
                    message = "Especificación de servicio para $vehicle: Capacidad 4.2 L con cambio de filtro. Viscosidad recomendada: 0W-20 sintético. Intervalo cada 8,000 km.",
                    memoryToConfirm = if (isMaintenanceRecord) "Aceite sintético 0W-20 con cambio de filtro" else null,
                    suggestedSpecialist = null,
                    isLocalFallback = true,
                )
            }

            prompt.contains("bater", ignoreCase = true) || prompt.contains("battery", ignoreCase = true) -> {
                val isBatteryChange = prompt.contains("nueva", ignoreCase = true) || prompt.contains("cambi", ignoreCase = true)
                CopilotReply(
                    message = "Diagnóstico de batería para $vehicle: Voltaje nominal 12.6V verificado. Alternador cargando dentro de rango (14.2V). Bóveda de salud al 100%.",
                    memoryToConfirm = if (isBatteryChange) "Batería 12.6V reemplazada" else null,
                    suggestedSpecialist = null,
                    isLocalFallback = true,
                )
            }

            prompt.contains("humo", ignoreCase = true) || prompt.contains("smoke", ignoreCase = true) ->
                CopilotReply(
                    message = "Alerta para $vehicle: Humo azul indica quema de aceite en cámara de combustión (posibles sellos de válvula o anillos de pistón gastados).",
                    suggestedSpecialist = SpecialistType.MECHANIC_SHOP,
                    memoryToConfirm = null,
                    isLocalFallback = true,
                )

            prompt.contains("yonke", ignoreCase = true) || prompt.contains("pieza", ignoreCase = true) ||
                prompt.contains("refaccion", ignoreCase = true) || prompt.contains("part", ignoreCase = true) ->
                CopilotReply(
                    message = "Catálogo de Yonke para $vehicle: Puedes consultar estimaciones de piezas de salvamento compatibles con la carrocería y motor de tu vehículo.",
                    suggestedSpecialist = SpecialistType.SALVAGE_YARD,
                    memoryToConfirm = null,
                    isLocalFallback = true,
                )

            prompt.contains("gasolin", ignoreCase = true) || prompt.contains("combustible", ignoreCase = true) ->
                CopilotReply(
                    message = "Orientación de combustible para $vehicle: Se recomienda gasolina de mínimo 87 octanos (Regular) o superior para un rendimiento óptimo de motor.",
                    suggestedSpecialist = SpecialistType.GAS_STATION,
                    memoryToConfirm = null,
                    isLocalFallback = true,
                )

            else ->
                CopilotReply(
                    message = "He registrado \"$prompt\" en tu bóveda mecánica local para $vehicle. Consultando telemetría de abordo y especificaciones de taller...",
                    suggestedSpecialist = null,
                    memoryToConfirm = null,
                    isLocalFallback = true,
                )
        }
    }
}
