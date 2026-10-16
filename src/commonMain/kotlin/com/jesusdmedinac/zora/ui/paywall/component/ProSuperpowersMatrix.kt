package com.jesusdmedinac.zora.ui.paywall.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.paywall.model.ProSuperpower
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.stringResource
import zora_community.generated.resources.Res
import zora_community.generated.resources.superpowers_title

/**
 * 2x2 grid matrix displaying Zora Pro features and unlocked capabilities.
 */
@Composable
fun ProSuperpowersMatrix(
    superpowers: List<ProSuperpower>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(Res.string.superpowers_title),
            fontFamily = ZoraTokens.FontBody,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = ZoraTokens.Primary,
        )

        // 2x2 Grid rendered as paired rows
        superpowers.chunked(2).forEach { rowPair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                rowPair.forEach { power ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(ZoraTokens.ShapeMd)
                            .background(ZoraTokens.SurfaceContainer)
                            .border(1.dp, ZoraTokens.OutlineVariant, ZoraTokens.ShapeMd)
                            .padding(12.dp),
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = power.title,
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ZoraTokens.OnSurface,
                            )
                            Text(
                                text = power.description,
                                fontFamily = ZoraTokens.FontBody,
                                fontSize = 11.sp,
                                color = ZoraTokens.OnSurfaceVariant,
                                lineHeight = 14.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun ProSuperpowersMatrixPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        ProSuperpowersMatrix(
            superpowers = com.jesusdmedinac.zora.ui.paywall.model.defaultProSuperpowers(),
        )
    }
}
