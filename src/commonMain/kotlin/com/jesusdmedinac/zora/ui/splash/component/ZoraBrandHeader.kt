package com.jesusdmedinac.zora.ui.splash.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens

/**
 * Brand wordmark and precision tagline for splash screen.
 */
@Composable
fun ZoraBrandHeader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "ZORA",
            style = TextStyle(
                fontFamily = ZoraTokens.FontHeadline,
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp,
                color = ZoraTokens.OnSurface,
                textAlign = TextAlign.Center
            )
        )

        Text(
            text = "PRECISION AUTOMOTIVE INTELLIGENCE",
            style = TextStyle(
                fontFamily = ZoraTokens.FontMono,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.2.sp,
                color = ZoraTokens.OnSurfaceVariant,
                textAlign = TextAlign.Center
            )
        )
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun ZoraBrandHeaderPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        ZoraBrandHeader()
    }
}

