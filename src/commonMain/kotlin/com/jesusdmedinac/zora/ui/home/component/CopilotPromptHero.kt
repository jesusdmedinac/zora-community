package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.splash.component.ZoraIgnitionGlyph
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.Res
import zora_community.generated.resources.copilot_hero_subtitle
import zora_community.generated.resources.copilot_hero_title

/**
 * Centered welcome hero greeting the driver with the Zora precision ignition glyph
 * and automotive diagnostic consultation prompt.
 *
 * Matches 'Centered Welcome Hero' (node IP21f in M3 / J062l in HIG) in design/zora-community.pen.
 */
@Composable
fun CopilotPromptHero(
    modifier: Modifier = Modifier,
    glyphSize: Dp = 96.dp,
    animated: Boolean = true,
    title: String = stringResource(Res.string.copilot_hero_title),
    subtitle: String = stringResource(Res.string.copilot_hero_subtitle),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // 1. Zora Precision Geometric Ignition Glyph with Core Spectrum Gradient (96dp)
        ZoraIgnitionGlyph(
            size = glyphSize,
            animated = animated,
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 2. Ignition Greeting Headline
        Text(
            text = title,
            fontFamily = ZoraTokens.FontHeadline,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = ZoraTokens.OnSurface,
            textAlign = TextAlign.Center,
            lineHeight = 30.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 3. Informative Local Diagnostic Subtitle
        Text(
            text = subtitle,
            fontFamily = ZoraTokens.FontBody,
            fontSize = 13.sp,
            color = ZoraTokens.OnSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
        )
    }
}

@Preview
@Composable
private fun CopilotPromptHeroPreview() {
    ZoraTheme {
        CopilotPromptHero()
    }
}
