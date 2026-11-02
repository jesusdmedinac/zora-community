package com.jesusdmedinac.zora.data.local.driver

import app.cash.sqldelight.db.SqlDriver
import com.jesusdmedinac.zora.data.local.MechanicalPrescriptionVaultDatabase
import com.jesusdmedinac.zora.data.local.Mechanical_prescription_vault
import com.jesusdmedinac.zora.data.local.SqlDelightMechanicalPrescriptionRepository
import com.jesusdmedinac.zora.data.local.adapter.PrescriptionSyncStateAdapter
import com.jesusdmedinac.zora.data.local.adapter.VehicleImpactQuadrantListAdapter

/**
 * Multiplatform abstraction for SQLite database driver creation.
 */
interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

/**
 * Creates and configures the SQLDelight MechanicalPrescriptionVaultDatabase with column adapters.
 */
fun createMechanicalPrescriptionVaultDatabase(driver: SqlDriver): MechanicalPrescriptionVaultDatabase {
    return MechanicalPrescriptionVaultDatabase(
        driver = driver,
        mechanical_prescription_vaultAdapter = Mechanical_prescription_vault.Adapter(
            sync_stateAdapter = PrescriptionSyncStateAdapter,
            excluded_impact_zonesAdapter = VehicleImpactQuadrantListAdapter,
        ),
    )
}

/**
 * Creates an instance of [SqlDelightMechanicalPrescriptionRepository] using the provided [driver].
 */
fun createSqlDelightRepository(driver: SqlDriver): SqlDelightMechanicalPrescriptionRepository {
    val database = createMechanicalPrescriptionVaultDatabase(driver)
    return SqlDelightMechanicalPrescriptionRepository(database)
}
