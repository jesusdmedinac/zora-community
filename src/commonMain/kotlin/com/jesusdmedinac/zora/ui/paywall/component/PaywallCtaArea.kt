package com.jesusdmedinac.zora.ui.paywall.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import zora_community.generated.resources.action_start_trial
import zora_community.generated.resources.paywall_trial_disclaimer

/**
 * Bottom call-to-action area with trial initiation and app store disclaimer.
 */
@Composable
fun PaywallCtaArea(
    onStartTrial: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = ZoraTokens.SpacingMd, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(CircleShape)
                .background(ZoraTokens.PrimaryContainer)
                .clickable { onStartTrial?.invoke() },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.action_start_trial),
                fontFamily = ZoraTokens.FontBody,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }

        Text(
            text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.paywall_trial_disclaimer),
            fontFamily = ZoraTokens.FontBody,
            fontSize = 11.sp,
            color = ZoraTokens.OnSurfaceVariant,
        )
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun PaywallCtaAreaPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        PaywallCtaArea(onStartTrial = {})
    }
}
