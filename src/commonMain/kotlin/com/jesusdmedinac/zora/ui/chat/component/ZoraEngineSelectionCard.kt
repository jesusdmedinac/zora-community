package com.jesusdmedinac.zora.ui.chat.component

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.stringResource
import zora_community.generated.resources.Res
import zora_community.generated.resources.action_confirm_engine
import zora_community.generated.resources.engine_selector_title
import org.jetbrains.compose.ui.tooling.preview.Preview

data class EngineOption(
    val id: String,
    val name: String,
    val specs: String,
)

/**
 * Node RFkA1: Engine Options Card from `Screen / M3 - 03 Onboarding Chat`.
 */
@Composable
fun ZoraEngineSelectionCard(
    selectedEngineId: String,
    onSelectEngine: (String) -> Unit,
    onConfirm: (String) -> Unit,
    modifier: Modifier = Modifier,
    options: List<EngineOption> = listOf(
        EngineOption(
            id = "2.0L_NA",
            name = "2.0L 4-Cilindros i-VTEC",
            specs = "Versiones LX • EX | 158 hp (Atmosférico)",
        ),
        EngineOption(
            id = "1.5L_TURBO",
            name = "1.5L Turbo DOHC",
            specs = "Versiones EX-T • Touring | 174 hp (Turbo)",
        ),
        EngineOption(
            id = "1.5L_SI",
            name = "1.5L Turbo High-Output (Si)",
            specs = "Transmisión Manual 6-vel | 205 hp",
        ),
    ),
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
            // Header
            Text(
                text = stringResource(Res.string.engine_selector_title),
                fontFamily = ZoraTokens.FontBody,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = ZoraTokens.Primary,
            )

            // Options List
            options.forEach { option ->
                val isSelected = option.id == selectedEngineId
                val cardBackground = if (isSelected) ZoraTokens.SurfaceContainerHigh else ZoraTokens.SurfaceContainerLow
                val cardBorder = if (isSelected) ZoraTokens.Primary else ZoraTokens.OutlineVariant

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ZoraTokens.ShapeMd)
                        .background(cardBackground)
                        .border(1.dp, cardBorder, ZoraTokens.ShapeMd)
                        .clickable { onSelectEngine(option.id) }
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        // Radio Indicator
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .border(2.dp, if (isSelected) ZoraTokens.Primary else ZoraTokens.OutlineVariant, CircleShape),
                            contentAlignment = Alignment.Center,
                        ) {
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(ZoraTokens.Primary)
                                )
                            }
                        }

                        // Engine details
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = option.name,
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                                color = ZoraTokens.OnSurface,
                            )
                            Text(
                                text = option.specs,
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 12.sp,
                                color = ZoraTokens.OnSurfaceVariant,
                            )
                        }
                    }
                }
            }

            // Confirm Button (CTA)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(CircleShape)
                    .background(ZoraTokens.Primary)
                    .clickable { onConfirm(selectedEngineId) },
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = stringResource(Res.string.action_confirm_engine),
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF0D0F12),
                    )
                    Text(
                        text = "→",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D0F12),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ZoraEngineSelectionCardPreview() {
    ZoraTheme {
        ZoraEngineSelectionCard(
            selectedEngineId = "2.0L_NA",
            onSelectEngine = {},
            onConfirm = {},
        )
    }
}

