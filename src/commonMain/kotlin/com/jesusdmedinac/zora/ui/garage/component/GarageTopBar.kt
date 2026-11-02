package com.jesusdmedinac.zora.ui.garage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.stringResource
import zora_community.generated.resources.Res
import zora_community.generated.resources.garage_telemetry_subtitle
import zora_community.generated.resources.nav_garage
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Top header for Virtual Garage screen with navigation back action.
 */
@Composable
fun GarageTopBar(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = ZoraTokens.SpacingMd),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(ZoraTokens.SurfaceContainerHigh)
                .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
                .clickable(onClick = onBack),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "←",
                color = ZoraTokens.OnSurface,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(Res.string.nav_garage),
                fontFamily = ZoraTokens.FontHeadline,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = ZoraTokens.OnSurface,
                letterSpacing = 0.5.sp,
            )
            Text(
                text = stringResource(Res.string.garage_telemetry_subtitle),
                fontFamily = ZoraTokens.FontMono,
                fontSize = 10.sp,
                color = ZoraTokens.Primary,
            )
        }

        Box(modifier = Modifier.size(36.dp))
    }
}

@Preview
@Composable
private fun GarageTopBarPreview() {
    ZoraTheme {
        GarageTopBar(onBack = {})
    }
}
