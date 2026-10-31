package com.jesusdmedinac.zora.di

import ai.koog.prompt.executor.clients.LLMClient
import android.content.Context
import com.jesusdmedinac.zora.ai.GeminiNanoKoogClient
import com.jesusdmedinac.zora.data.local.driver.AndroidDatabaseDriverFactory
import com.jesusdmedinac.zora.data.local.driver.DatabaseDriverFactory
import org.koin.dsl.module

fun androidPlatformModule(context: Context) = module {
    single<DatabaseDriverFactory> {
        AndroidDatabaseDriverFactory(context)
    }
    single<LLMClient> {
        GeminiNanoKoogClient(context)
    }
}
