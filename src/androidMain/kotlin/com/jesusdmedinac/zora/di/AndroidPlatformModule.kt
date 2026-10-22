package com.jesusdmedinac.zora.di

import android.content.Context
import com.jesusdmedinac.zora.data.local.driver.AndroidDatabaseDriverFactory
import com.jesusdmedinac.zora.data.local.driver.DatabaseDriverFactory
import org.koin.dsl.module

fun androidPlatformModule(context: Context) = module {
    single<DatabaseDriverFactory> {
        AndroidDatabaseDriverFactory(context)
    }
}
