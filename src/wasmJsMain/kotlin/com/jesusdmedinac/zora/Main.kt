package com.jesusdmedinac.zora

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.jesusdmedinac.zora.di.initKoin
import com.jesusdmedinac.zora.di.wasmPlatformModule
import com.jesusdmedinac.zora.ui.ZoraApp
import kotlinx.browser.document

/**
 * WebAssembly (WasmJs) Application Runner for Zora Community Edition.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin(listOf(wasmPlatformModule))
    val body = document.body ?: return
    ComposeViewport(body) {
        ZoraApp()
    }
}
