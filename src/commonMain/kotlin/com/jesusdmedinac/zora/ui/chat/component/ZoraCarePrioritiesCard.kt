package com.jesusdmedinac.zora.ui.chat.component

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
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import zora_community.generated.resources.care_priorities_title
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class CarePriorityBadgeType {
    EMERALD_HIGH_PRIORITY,
    VIOLET_MAX_SAVINGS,
}

data class CarePriorityItem(
    val title: String,
    val description: String,
    val badgeText: String,
    val badgeType: CarePriorityBadgeType,
)

/**
 * Recommended Care Priorities cards with semantic two-word status badges.
 * Conforms to Anti-AI slop: no artificial percentage match numbers.
 */
@Composable
fun ZoraCarePrioritiesCard(
    modifier: Modifier = Modifier,
    items: List<CarePriorityItem> = defaultCarePriorityItems(),
    onItemClick: ((CarePriorityItem) -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(ZoraTokens.ShapeLg)
            .background(ZoraTokens.SurfaceContainer)
            .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeLg)
            .padding(14.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.care_priorities_title),
                fontFamily = ZoraTokens.FontBody,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = ZoraTokens.Primary,
            )

            items.forEach { item ->
                val badgeBg = when (item.badgeType) {
                    CarePriorityBadgeType.EMERALD_HIGH_PRIORITY -> Color(0xFF003822)
                    CarePriorityBadgeType.VIOLET_MAX_SAVINGS -> Color(0xFF380062)
                }
                val badgeTextBorder = when (item.badgeType) {
                    CarePriorityBadgeType.EMERALD_HIGH_PRIORITY -> Color(0xFF10B981)
                    CarePriorityBadgeType.VIOLET_MAX_SAVINGS -> Color(0xFFD1BCFF)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ZoraTokens.ShapeMd)
                        .background(ZoraTokens.SurfaceContainerLow)
                        .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeMd)
                        .padding(12.dp),
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = item.title,
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = ZoraTokens.OnSurface,
                            )

                            // Two-word semantic badge
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(badgeBg)
                                    .border(1.dp, badgeTextBorder.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 2.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = item.badgeText,
                                    fontFamily = ZoraTokens.FontMono,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = badgeTextBorder,
                                    letterSpacing = 0.5.sp,
                                )
                            }
                        }

                        Text(
                            text = item.description,
                            fontFamily = ZoraTokens.FontBody,
                            fontSize = 12.sp,
                            color = ZoraTokens.OnSurfaceVariant,
                            lineHeight = 16.sp,
                        )
                    }
                }
            }
        }
    }
}

fun defaultCarePriorityItems(): List<CarePriorityItem> = listOf(
    CarePriorityItem(
        title = "Preventive Maintenance",
        description = "Calibración de telemetría CAN-Bus y monitoreo de fluido de transmisión HCF-2 para 2.0L i-VTEC.",
        badgeText = "HIGH PRIORITY",
        badgeType = CarePriorityBadgeType.EMERALD_HIGH_PRIORITY,
    ),
    CarePriorityItem(
        title = "Junkyard & Used Parts",
        description = "Búsqueda en yonkes de Tijuana/Otay con matriz de donantes compatibles y exclusión de cuadrante de colisión.",
        badgeText = "MAX SAVINGS",
        badgeType = CarePriorityBadgeType.VIOLET_MAX_SAVINGS,
    ),
)

@Preview
@Composable
private fun ZoraCarePrioritiesCardPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        ZoraCarePrioritiesCard()
    }
}

