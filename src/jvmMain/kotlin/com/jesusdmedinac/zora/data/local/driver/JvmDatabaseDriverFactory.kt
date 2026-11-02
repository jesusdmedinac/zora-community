package com.jesusdmedinac.zora.data.local.driver

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.jesusdmedinac.zora.data.local.MechanicalPrescriptionVaultDatabase
import kotlinx.coroutines.runBlocking
import java.io.File

/**
 * Desktop (JVM) SQLite database driver factory using [JdbcSqliteDriver].
 * Supports persistent file storage under user directory or in-memory for testing.
 */
class JvmDatabaseDriverFactory(
    private val dbPath: String = defaultDbPath(),
    private val inMemory: Boolean = false,
) : DatabaseDriverFactory {

    override fun createDriver(): SqlDriver {
        val url = if (inMemory) {
            JdbcSqliteDriver.IN_MEMORY
        } else {
            val file = File(dbPath)
            file.parentFile?.mkdirs()
            "jdbc:sqlite:${file.absolutePath}"
        }
        val driver = JdbcSqliteDriver(url)
        runBlocking {
            try {
                MechanicalPrescriptionVaultDatabase.Schema.create(driver).await()
            } catch (_: Throwable) {
                // Schema already exists
            }
        }
        return driver
    }

    companion object {
        fun defaultDbPath(): String {
            val userHome = System.getProperty("user.home") ?: "."
            return "$userHome/.zora/zora_vault.db"
        }
    }
}
