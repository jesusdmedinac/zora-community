package com.jesusdmedinac.zora.platform

import android.content.ClipData
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard

actual suspend fun Clipboard.setPlainText(text: String) {
    val clipData = ClipData.newPlainText("text", text)
    setClipEntry(ClipEntry(clipData))
}
