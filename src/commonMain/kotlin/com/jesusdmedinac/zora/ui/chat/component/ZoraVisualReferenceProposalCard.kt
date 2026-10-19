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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import zora_community.generated.resources.action_generate_reference_image
import zora_community.generated.resources.action_skip
import zora_community.generated.resources.visual_reference_title
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Visual reference generation proposal card for Garage Slot #01.
 * Includes monthly quota badge, explicit AI disclaimer, and trigger/skip actions.
 */
@Composable
fun ZoraVisualReferenceProposalCard(
    onGenerate: () -> Unit,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier,
    slotName: String = "Garage Slot #01",
    quotaText: String = "1 of 3 free this month",
    disclaimerText: String = "Note: AI-generated visual reference for illustration only",
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(ZoraTokens.ShapeLg)
            .background(ZoraTokens.SurfaceContainer)
            .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeLg)
            .padding(14.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Header with Slot Name and Quota
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.visual_reference_title),
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ZoraTokens.Primary,
                )

                // Quota badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(ZoraTokens.SurfaceContainerHighest)
                        .border(1.dp, ZoraTokens.OutlineVariant, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                ) {
                    Text(
                        text = quotaText,
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = ZoraTokens.Primary,
                    )
                }
            }

            // Description
            Text(
                text = "Generar plano de representación visual de alta fidelidad para $slotName.",
                fontFamily = ZoraTokens.FontBody,
                fontSize = 13.sp,
                color = ZoraTokens.OnSurface,
                lineHeight = 18.sp,
            )

            // Visible Disclaimer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ZoraTokens.ShapeSm)
                    .background(ZoraTokens.SurfaceContainerLow)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
            ) {
                Text(
                    text = disclaimerText,
                    fontFamily = ZoraTokens.FontBody,
                    fontStyle = FontStyle.Italic,
                    fontSize = 11.sp,
                    color = ZoraTokens.OnSurfaceVariant,
                    lineHeight = 15.sp,
                )
            }

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Generate Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clip(CircleShape)
                        .background(ZoraTokens.PrimaryContainer)
                        .clickable(onClick = onGenerate),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.action_generate_reference_image),
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                }

                // Skip Button
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .clip(CircleShape)
                        .background(ZoraTokens.SurfaceContainerLow)
                        .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
                        .clickable(onClick = onSkip)
                        .padding(horizontal = 14.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.action_skip),
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = ZoraTokens.OnSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ZoraVisualReferenceProposalCardPreview() {
    ZoraTheme {
        ZoraVisualReferenceProposalCard(
            onGenerate = {},
            onSkip = {},
        )
    }
}
