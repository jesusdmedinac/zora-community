package com.jesusdmedinac.zora.ui.parts.component

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
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.parts_fitment_active

/**
 * Top navigation bar for the parts catalog featuring vehicle fitment verification indicator.
 */
@Composable
fun PartsTopBar(
    activeVehicle: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
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

            // Vehicle Fitment Pill
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(ZoraTokens.SurfaceContainerHigh)
                    .border(1.dp, ZoraTokens.StatusSuccess.copy(alpha = 0.5f), CircleShape)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(ZoraTokens.StatusSuccess)
                    )
                    Text(
                        text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.parts_fitment_active),
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ZoraTokens.OnSurface,
                    )
                }
            }

            Box(modifier = Modifier.size(36.dp))
        }

        // Subtitle with active vehicle name
        Text(
            text = activeVehicle,
            fontFamily = ZoraTokens.FontHeadline,
            fontSize = 13.sp,
            color = ZoraTokens.Primary,
            modifier = Modifier.padding(horizontal = ZoraTokens.SpacingMd, vertical = 2.dp),
        )
    }
}

@Preview
@Composable
private fun PartsTopBarPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        PartsTopBar(
            activeVehicle = "2018 Honda Civic EX 2.0L K20C2",
            onBack = {},
        )
    }
}
