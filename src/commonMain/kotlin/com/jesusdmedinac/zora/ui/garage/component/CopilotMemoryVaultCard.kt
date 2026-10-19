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
import com.jesusdmedinac.zora.ui.garage.model.CopilotMemoryItem
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import zora_community.generated.resources.memory_vault_local_assurance
import zora_community.generated.resources.memory_vault_title
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * On-device copilot memory vault tracking 3 capacity slots with granular deletion.
 */
@Composable
fun CopilotMemoryVaultCard(
    memories: List<CopilotMemoryItem>,
    onDeleteMemory: (CopilotMemoryItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val percentage = if (memories.isEmpty()) 0 else kotlin.math.round((memories.size.toDouble() / 3.0) * 100).toInt()
    val meterText = "${memories.size} of 3 Free Slots ($percentage%)"

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
                text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.memory_vault_title),
                fontFamily = ZoraTokens.FontBody,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = ZoraTokens.Primary,
            )
            Text(
                text = meterText,
                fontFamily = ZoraTokens.FontMono,
                fontSize = 11.sp,
                color = if (memories.size == 3) ZoraTokens.StatusWarning else ZoraTokens.Primary,
            )
        }

        // Local-first Indicator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(ZoraTokens.ShapeSm)
                .background(ZoraTokens.SurfaceContainerLow)
                .padding(horizontal = 10.dp, vertical = 6.dp),
        ) {
            Text(
                text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.memory_vault_local_assurance),
                fontFamily = ZoraTokens.FontBody,
                fontSize = 11.sp,
                color = ZoraTokens.StatusSuccess,
            )
        }

        // Memory items
        memories.forEach { memory ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ZoraTokens.ShapeMd)
                    .background(ZoraTokens.SurfaceContainerLow)
                    .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeMd)
                    .padding(12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = memory.category,
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ZoraTokens.OnSurface,
                            )
                            Text(
                                text = "• ${memory.source}",
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 10.sp,
                                color = ZoraTokens.OnSurfaceVariant,
                            )
                        }
                        Text(
                            text = memory.detail,
                            fontFamily = ZoraTokens.FontBody,
                            fontSize = 12.sp,
                            color = ZoraTokens.OnSurfaceVariant,
                            lineHeight = 16.sp,
                        )
                    }

                    // Delete Action
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(ZoraTokens.SurfaceContainerHigh)
                            .clickable { onDeleteMemory(memory) },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "✕",
                            color = ZoraTokens.StatusError,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CopilotMemoryVaultCardPreview() {
    ZoraTheme {
        CopilotMemoryVaultCard(
            memories = com.jesusdmedinac.zora.ui.garage.model.defaultCopilotMemories(),
            onDeleteMemory = {},
        )
    }
}
