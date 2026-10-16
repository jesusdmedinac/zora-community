package com.jesusdmedinac.zora

import androidx.compose.ui.window.ComposeUIViewController
import com.jesusdmedinac.zora.ui.ZoraApp
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant

/**
 * iOS Application Runner entry point for Zora Community Edition.
 * Exported to Swift as `MainViewControllerKt.MainViewController()`.
 */
fun MainViewController() = ComposeUIViewController {
    ZoraApp(platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE)
}

