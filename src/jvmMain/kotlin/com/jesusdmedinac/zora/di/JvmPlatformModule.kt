package com.jesusdmedinac.zora.di

import com.jesusdmedinac.zora.data.local.driver.DatabaseDriverFactory
import com.jesusdmedinac.zora.data.local.driver.JvmDatabaseDriverFactory
import org.koin.dsl.module

val jvmPlatformModule = module {
    single<DatabaseDriverFactory> {
        JvmDatabaseDriverFactory()
    }
}
