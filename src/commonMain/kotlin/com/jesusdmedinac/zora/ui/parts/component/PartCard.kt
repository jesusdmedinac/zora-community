package com.jesusdmedinac.zora.ui.parts.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.jesusdmedinac.zora.ui.parts.model.PartItem
import com.jesusdmedinac.zora.ui.theme.ZoraTokens

/**
 * Atomic card displaying either new OEM/Aftermarket part or Yonke salvage yard part.
 */
@Composable
fun PartCard(
    item: PartItem,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(ZoraTokens.ShapeLg)
            .background(ZoraTokens.SurfaceContainer)
            .border(
                1.dp,
                if (item.isSalvage) ZoraTokens.SecondaryContainer else ZoraTokens.OutlineVariant,
                ZoraTokens.ShapeLg
            )
            .padding(14.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // Badge & Price Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            if (item.isSalvage) Color(0xFF380062) else Color(0xFF003822)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                ) {
                    Text(
                        text = item.badge,
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (item.isSalvage) ZoraTokens.Secondary else ZoraTokens.StatusSuccess,
                    )
                }

                // Price
                Text(
                    text = item.price,
                    fontFamily = ZoraTokens.FontMono,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = ZoraTokens.OnSurface,
                )
            }

            // Title
            Text(
                text = item.title,
                fontFamily = ZoraTokens.FontBody,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = ZoraTokens.OnSurface,
            )

            // OEM Interchange if regular part
            if (item.oemInterchange != null) {
                Text(
                    text = "OEM Interchange: ${item.oemInterchange}",
                    fontFamily = ZoraTokens.FontMono,
                    fontSize = 11.sp,
                    color = ZoraTokens.Primary,
                )
            }

            // Salvage specifics if Yonke
            if (item.isSalvage) {
                if (item.donorVinAndMileage != null) {
                    Text(
                        text = item.donorVinAndMileage,
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 11.sp,
                        color = ZoraTokens.Primary,
                    )
                }
                if (item.savingsCalculation != null) {
                    Text(
                        text = item.savingsCalculation,
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ZoraTokens.StatusSuccess,
                    )
                }
                if (item.distanceAndGrade != null) {
                    Text(
                        text = item.distanceAndGrade,
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 11.sp,
                        color = ZoraTokens.OnSurfaceVariant,
                    )
                }
            }
        }
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun PartCardPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        PartCard(
            item = com.jesusdmedinac.zora.ui.parts.model.defaultPartsCatalog().first(),
        )
    }
}
