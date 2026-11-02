package com.jesusdmedinac.zora.data.local.driver

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jesusdmedinac.zora.data.local.MechanicalPrescriptionVaultDatabase

/**
 * iOS Native SQLite database driver factory using [NativeSqliteDriver].
 */
class IosDatabaseDriverFactory(
    private val dbName: String = "zora_vault.db",
) : DatabaseDriverFactory {

    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = MechanicalPrescriptionVaultDatabase.Schema.synchronous(),
            name = dbName,
        )
    }
}
