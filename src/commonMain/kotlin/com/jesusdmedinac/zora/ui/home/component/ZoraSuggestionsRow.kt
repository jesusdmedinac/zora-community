package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import zora_community.generated.resources.suggestion_battery_12v
import zora_community.generated.resources.suggestion_change_tire
import zora_community.generated.resources.suggestion_check_dtc
import zora_community.generated.resources.suggestion_mechanical_prescription
import zora_community.generated.resources.suggestion_vibrate_braking
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Quick diagnostic suggestions row from Copilot Home (v2.21).
 */
@Composable
fun ZoraSuggestionsRow(
    onSuggestionClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    suggestions: List<String> = listOf(
        org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.suggestion_vibrate_braking),
        org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.suggestion_change_tire),
        org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.suggestion_check_dtc),
        org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.suggestion_mechanical_prescription),
        org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.suggestion_battery_12v),
    ),
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = ZoraTokens.SpacingMd),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        suggestions.forEach { suggestion ->
            Box(
                modifier = Modifier
                    .height(34.dp)
                    .clip(CircleShape)
                    .background(ZoraTokens.SurfaceContainerHigh)
                    .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
                    .clickable { onSuggestionClick(suggestion) }
                    .padding(horizontal = 14.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = suggestion,
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 12.sp,
                    color = ZoraTokens.OnSurface,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ZoraSuggestionsRowPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        ZoraSuggestionsRow(onSuggestionClick = {})
    }
}

