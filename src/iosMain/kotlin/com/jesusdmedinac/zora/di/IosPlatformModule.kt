package com.jesusdmedinac.zora.di

import com.jesusdmedinac.zora.data.local.driver.DatabaseDriverFactory
import com.jesusdmedinac.zora.data.local.driver.IosDatabaseDriverFactory
import org.koin.dsl.module

val iosPlatformModule = module {
    single<DatabaseDriverFactory> {
        IosDatabaseDriverFactory()
    }
}
