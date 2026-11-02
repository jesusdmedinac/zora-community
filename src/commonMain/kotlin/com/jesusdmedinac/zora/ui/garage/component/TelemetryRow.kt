package com.jesusdmedinac.zora.ui.garage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Key-value row for displaying vehicle telemetry parameters.
 */
@Composable
fun TelemetryRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            fontFamily = ZoraTokens.FontBody,
            fontSize = 13.sp,
            color = ZoraTokens.OnSurfaceVariant,
        )
        Text(
            text = value,
            fontFamily = ZoraTokens.FontMono,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = ZoraTokens.OnSurface,
        )
    }
}

@Preview
@Composable
private fun TelemetryRowPreview() {
    ZoraTheme {
        TelemetryRow(
            label = "Engine Oil",
            value = "85% (Optimal)",
        )
    }
}
