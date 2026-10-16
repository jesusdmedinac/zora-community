package com.jesusdmedinac.zora.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Pure Kotlin Multiplatform NavDisplay for Navigation 3.
 * Renders the top-most [NavEntry] from [backstack] using [entryProvider].
 */
@Composable
fun <T : Any> NavDisplay(
    backstack: List<T>,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    transitionSpec: () -> ContentTransform = {
        fadeIn(animationSpec = tween(250)) togetherWith fadeOut(animationSpec = tween(200))
    },
    entryProvider: (T) -> NavEntry<T>,
) {
    val currentKey = backstack.lastOrNull() ?: return

    AnimatedContent(
        targetState = currentKey,
        modifier = modifier.fillMaxSize(),
        contentAlignment = contentAlignment,
        transitionSpec = { transitionSpec() },
        label = "NavDisplayTransition",
    ) { key ->
        val entry = entryProvider(key)
        entry.Content()
    }
}

@Preview
@Composable
private fun NavDisplayPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        NavDisplay(
            backstack = listOf("Home"),
            entryProvider = { key ->
                NavEntry(key) {
                    androidx.compose.material3.Text("Route: $key")
                }
            }
        )
    }
}

