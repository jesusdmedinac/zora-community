package com.jesusdmedinac.zora.ui.paywall.component

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
import com.jesusdmedinac.zora.ui.paywall.model.AgentNode
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.stringResource
import zora_community.generated.resources.Res
import zora_community.generated.resources.network_title
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Status card showcasing the multi-agent autonomous network and local-first privacy guarantee.
 */
@Composable
fun AgentNetworkCard(
    agentNodes: List<AgentNode>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(ZoraTokens.ShapeLg)
            .background(ZoraTokens.SurfaceContainer)
            .border(1.dp, ZoraTokens.Primary, ZoraTokens.ShapeLg)
            .padding(14.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(Res.string.network_title),
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ZoraTokens.Primary,
                )

                // Privacy Affirmation Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF003822))
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                ) {
                    Text(
                        text = "Local-First · Zero Leak",
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = ZoraTokens.StatusSuccess,
                    )
                }
            }

            // Agent Nodes List
            agentNodes.forEach { node ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ZoraTokens.ShapeMd)
                        .background(ZoraTokens.SurfaceContainerLow)
                        .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeMd)
                        .padding(10.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = node.name,
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = ZoraTokens.OnSurface,
                            )
                            Text(
                                text = "Cluster: ${node.cluster}",
                                fontFamily = ZoraTokens.FontMono,
                                fontSize = 10.sp,
                                color = ZoraTokens.OnSurfaceVariant,
                            )
                        }

                        val isLive = node.status.startsWith("ACTIVE")
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isLive) Color(0xFF003822) else Color(0xFF25262B))
                                .padding(horizontal = 6.dp, vertical = 2.dp),
                        ) {
                            Text(
                                text = node.status,
                                fontFamily = ZoraTokens.FontMono,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isLive) ZoraTokens.StatusSuccess else ZoraTokens.OnSurfaceVariant,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AgentNetworkCardPreview() {
    ZoraTheme {
        AgentNetworkCard(
            agentNodes = com.jesusdmedinac.zora.ui.paywall.model.defaultAgentNodes(),
        )
    }
}
