package com.jesusdmedinac.zora.ui.garage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

/**
 * Garage vehicle slots section enforcing free tier 1-slot limit with Pro upgrade prompt.
 */
@Composable
fun GarageSlotsCard(
    showProModal: Boolean,
    onSlotClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "ESPACIOS DE GARAJE",
                fontFamily = ZoraTokens.FontBody,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = ZoraTokens.Primary,
            )
            Text(
                text = "1 of 1 Free Slot Used",
                fontFamily = ZoraTokens.FontMono,
                fontSize = 11.sp,
                color = ZoraTokens.OnSurfaceVariant,
            )
        }

        // Slot 02: Locked with PRO badge
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(ZoraTokens.ShapeLg)
                .background(ZoraTokens.SurfaceContainerLow)
                .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeLg)
                .clickable(onClick = onSlotClick)
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "SLOT 02",
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 10.sp,
                        color = ZoraTokens.Outline,
                    )
                    Text(
                        text = "+ Add Another Vehicle",
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = ZoraTokens.OnSurfaceVariant,
                    )
                }
                // Locked PRO badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(ZoraTokens.SecondaryContainer)
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                ) {
                    Text(
                        text = "PRO",
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
            }
        }

        if (showProModal) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ZoraTokens.ShapeMd)
                    .background(ZoraTokens.SurfaceContainerHighest)
                    .border(1.dp, ZoraTokens.Primary, ZoraTokens.ShapeMd)
                    .padding(12.dp),
            ) {
                Text(
                    text = "Upgrade to Zora Pro to unlock unlimited garage slots and multi-vehicle telemetry.",
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 12.sp,
                    color = ZoraTokens.OnSurface,
                )
            }
        }
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun GarageSlotsCardPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        GarageSlotsCard(
            showProModal = false,
            onSlotClick = {},
        )
    }
}
