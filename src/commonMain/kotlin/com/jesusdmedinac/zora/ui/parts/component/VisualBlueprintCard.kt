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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.parts.model.VisualBlueprintState
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * On-demand 3D cutaway visual blueprint card with monthly quota tracking.
 */
@Composable
fun VisualBlueprintCard(
    blueprintState: VisualBlueprintState,
    onRequestBlueprint: () -> Unit,
    modifier: Modifier = Modifier,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "3D COMPONENT VISUAL BLUEPRINT",
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ZoraTokens.Primary,
                )
                Text(
                    text = blueprintState.quotaText,
                    fontFamily = ZoraTokens.FontMono,
                    fontSize = 10.sp,
                    color = ZoraTokens.Primary,
                )
            }

            if (blueprintState.isGenerated) {
                // 3D Cutaway Wireframe container
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(ZoraTokens.ShapeMd)
                        .background(Color(0xFF090A0D))
                        .border(1.dp, ZoraTokens.PrimaryContainer, ZoraTokens.ShapeMd)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        // Locator pin with glowing beacon
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(ZoraTokens.TertiaryContainer)
                            )
                            Text(
                                text = "📍 PIN: Front-Right Offset Loc",
                                fontFamily = ZoraTokens.FontMono,
                                fontSize = 11.sp,
                                color = ZoraTokens.Tertiary,
                            )
                        }

                        Text(
                            text = "[3D Cutaway Wireframe Rendered: Engine Bay]",
                            fontFamily = ZoraTokens.FontMono,
                            fontSize = 12.sp,
                            color = ZoraTokens.OnSurface,
                        )

                        Text(
                            text = blueprintState.componentTag,
                            fontFamily = ZoraTokens.FontMono,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = ZoraTokens.Primary,
                        )
                    }
                }
            } else {
                // Request button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .clip(CircleShape)
                        .background(ZoraTokens.SurfaceContainerHigh)
                        .border(1.dp, ZoraTokens.Primary, CircleShape)
                        .clickable(onClick = onRequestBlueprint),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Request Visual Blueprint for \"DENSO ALTERNATOR\"",
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ZoraTokens.Primary,
                    )
                }
            }

            // Disclaimer
            Text(
                text = blueprintState.disclaimer,
                fontFamily = ZoraTokens.FontBody,
                fontStyle = FontStyle.Italic,
                fontSize = 10.sp,
                color = ZoraTokens.OnSurfaceVariant,
                lineHeight = 14.sp,
            )
        }
    }
}

@Preview
@Composable
private fun VisualBlueprintCardPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        VisualBlueprintCard(
            blueprintState = com.jesusdmedinac.zora.ui.parts.model.VisualBlueprintState(),
            onRequestBlueprint = {},
        )
    }
}
