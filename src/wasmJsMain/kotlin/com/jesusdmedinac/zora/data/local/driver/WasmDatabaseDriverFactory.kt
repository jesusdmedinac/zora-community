package com.jesusdmedinac.zora.data.local.driver

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.createDefaultWebWorkerDriver

/**
 * WebAssembly (WasmJs) SQLite database driver factory using [createDefaultWebWorkerDriver].
 */
class WasmDatabaseDriverFactory : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return createDefaultWebWorkerDriver()
    }
}
