package com.jesusdmedinac.zora.data.local.driver

import com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescriptionEntity
import com.jesusdmedinac.zora.domain.model.prescription.PrescriptionSyncState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class JvmDatabaseDriverFactoryTest {

    @Test
    fun `JvmDatabaseDriverFactory creates working in-memory SQLite driver`() {
        val factory = JvmDatabaseDriverFactory(inMemory = true)
        val driver = factory.createDriver()
        assertNotNull(driver)

        val database = createMechanicalPrescriptionVaultDatabase(driver)
        assertNotNull(database.mechanicalPrescriptionVaultQueries)
    }

    @Test
    fun `createSqlDelightRepository performs CRUD operations using factory driver`() = runTest {
        val factory = JvmDatabaseDriverFactory(inMemory = true)
        val driver = factory.createDriver()
        val repository = createSqlDelightRepository(driver)

        val entity = MechanicalPrescriptionEntity(
            symptomId = "driver_test_01",
            vehicleId = "civic_driver_test",
            vinTechnicalPrefix = "2HGFC2F5",
            rawSymptomDescription = "Ruido metálico en suspensión delantera",
            localPreliminaryHypothesis = "Buje de horquilla desgastado",
            syncState = PrescriptionSyncState.LOCAL_TRIAGE_ONLY,
            createdAtMs = 1729000000000L,
            updatedAtMs = 1729000000000L,
        )

        val insertResult = repository.insertLocalTriage(entity)
        assertTrue(insertResult.isSuccess)

        val retrieved = repository.selectById("driver_test_01")
        assertNotNull(retrieved)
        assertEquals("Ruido metálico en suspensión delantera", retrieved.rawSymptomDescription)
        assertEquals("civic_driver_test", retrieved.vehicleId)

        val flowEntities = repository.observeByVehicle("civic_driver_test").first()
        assertEquals(1, flowEntities.size)
        assertEquals("driver_test_01", flowEntities.first().symptomId)
    }
}
