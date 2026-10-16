package com.jesusdmedinac.zora

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.jesusdmedinac.zora.ui.ZoraApp
import org.jetbrains.compose.resources.painterResource
import zora_community.generated.resources.Res
import zora_community.generated.resources.zora_icon

/**
 * Desktop JVM Application Runner for Zora Community Edition.
 */
fun main() = application {
    val windowState = rememberWindowState(width = 412.dp, height = 892.dp)
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "Zora Community",
        icon = painterResource(Res.drawable.zora_icon),
    ) {
        ZoraApp()
    }
}
