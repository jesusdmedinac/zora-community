package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens

/**
 * Centered hero invitation greeting the driver and prompting for diagnostic questions.
 */
@Composable
fun CopilotPromptHero(
    title: String = "¿Cómo te ayudo hoy con tu auto?",
    subtitle: String = "Respuestas mecánicas y diagnóstico local en tu dispositivo.",
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            fontFamily = ZoraTokens.FontHeadline,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = ZoraTokens.OnSurface,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = subtitle,
            fontFamily = ZoraTokens.FontBody,
            fontSize = 13.sp,
            color = ZoraTokens.OnSurfaceVariant,
        )
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun CopilotPromptHeroPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        CopilotPromptHero()
    }
}

