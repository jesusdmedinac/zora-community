package com.jesusdmedinac.zora.platform

import androidx.compose.ui.platform.Clipboard

/**
 * Modern non-deprecated clipboard extension setting plain text into the platform [Clipboard].
 * Encapsulates multiplatform [androidx.compose.ui.platform.ClipEntry] creation across
 * Android, iOS, JVM Desktop, and WebAssembly targets.
 */
expect suspend fun Clipboard.setPlainText(text: String)
