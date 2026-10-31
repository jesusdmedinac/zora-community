package com.jesusdmedinac.zora.domain.ai

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class KoogZoraCopilotAgentTest {

    private val agent = KoogZoraCopilotAgent()

    private val sampleGarageContext = GarageVehicleContext(
        make = "Honda",
        model = "Civic",
        year = 2018,
        learnedMemories = listOf("Aceite 0W-20 sintético", "Balatas cambiadas a 110,000 km"),
        vehicleDisplayName = "2018 Honda Civic",
    )

    @Test
    fun `interact with brake vibration routes to mechanic shop specialist with local fallback`() = runTest {
        val reply = agent.interact(
            driverPrompt = "Siento que el pedal vibra al frenar en carretera",
            garageContext = sampleGarageContext,
        )

        assertEquals(SpecialistType.MECHANIC_SHOP, reply.suggestedSpecialist)
        assertNull(reply.memoryToConfirm)
        assertTrue(reply.isLocalFallback)
        assertTrue(reply.message.contains("2018 Honda Civic"))
        assertTrue(reply.message.contains("discos de freno"))
    }

    @Test
    fun `interact with oil change prompt proposes memory for virtual garage`() = runTest {
        val reply = agent.interact(
            driverPrompt = "Hoy le cambié el aceite y filtro a mi carro",
            garageContext = sampleGarageContext,
        )

        assertNull(reply.suggestedSpecialist)
        assertNotNull(reply.memoryToConfirm)
        assertEquals("Aceite sintético 0W-20 con cambio de filtro", reply.memoryToConfirm)
        assertTrue(reply.isLocalFallback)
        assertTrue(reply.message.contains("0W-20"))
    }

    @Test
    fun `interact with battery replacement proposes battery memory`() = runTest {
        val reply = agent.interact(
            driverPrompt = "Le puse una batería nueva",
            garageContext = sampleGarageContext,
        )

        assertNull(reply.suggestedSpecialist)
        assertEquals("Batería 12.6V reemplazada", reply.memoryToConfirm)
        assertTrue(reply.isLocalFallback)
        assertTrue(reply.message.contains("12.6V"))
    }

    @Test
    fun `interact with smoke warning routes to mechanic shop specialist`() = runTest {
        val reply = agent.interact(
            driverPrompt = "Sale humo azul por el escape al acelerar",
            garageContext = sampleGarageContext,
        )

        assertEquals(SpecialistType.MECHANIC_SHOP, reply.suggestedSpecialist)
        assertNull(reply.memoryToConfirm)
        assertTrue(reply.isLocalFallback)
        assertTrue(reply.message.contains("Humo azul"))
    }

    @Test
    fun `interact with salvage parts routes to salvage yard specialist`() = runTest {
        val reply = agent.interact(
            driverPrompt = "Dónde encuentro un yonke para conseguir un espejo retrovisor?",
            garageContext = sampleGarageContext,
        )

        assertEquals(SpecialistType.SALVAGE_YARD, reply.suggestedSpecialist)
        assertNull(reply.memoryToConfirm)
        assertTrue(reply.isLocalFallback)
        assertTrue(reply.message.contains("Catálogo de Yonke"))
    }

    @Test
    fun `interact with fuel and gas stations routes to gas station specialist`() = runTest {
        val reply = agent.interact(
            driverPrompt = "Qué tipo de gasolina y combustible debo ponerle?",
            garageContext = sampleGarageContext,
        )

        assertEquals(SpecialistType.GAS_STATION, reply.suggestedSpecialist)
        assertNull(reply.memoryToConfirm)
        assertTrue(reply.isLocalFallback)
        assertTrue(reply.message.contains("87 octanos"))
    }

    @Test
    fun `inspect garage tool produces informative context summary`() = runTest {
        val tool = InspectGarageContextTool(sampleGarageContext)
        val result = tool.execute(InspectGarageArgs())

        assertTrue(result.contains("2018 Honda Civic"))
        assertTrue(result.contains("Aceite 0W-20 sintético"))
        assertTrue(result.contains("Balatas cambiadas a 110,000 km"))
    }
}
