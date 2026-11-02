package com.jesusdmedinac.zora.di

import com.jesusdmedinac.zora.data.local.driver.DatabaseDriverFactory
import com.jesusdmedinac.zora.data.local.driver.WasmDatabaseDriverFactory
import org.koin.dsl.module

val wasmPlatformModule = module {
    single<DatabaseDriverFactory> {
        WasmDatabaseDriverFactory()
    }
}
