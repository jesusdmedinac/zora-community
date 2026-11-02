package com.jesusdmedinac.zora.data.local.driver

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jesusdmedinac.zora.data.local.MechanicalPrescriptionVaultDatabase

/**
 * Android SQLite database driver factory using [AndroidSqliteDriver].
 */
class AndroidDatabaseDriverFactory(
    private val context: Context,
    private val dbName: String = "zora_vault.db",
) : DatabaseDriverFactory {

    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = MechanicalPrescriptionVaultDatabase.Schema.synchronous(),
            context = context,
            name = dbName,
        )
    }
}
