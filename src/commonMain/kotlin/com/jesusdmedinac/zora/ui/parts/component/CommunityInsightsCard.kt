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
import androidx.compose.foundation.shape.CircleShape
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
import com.jesusdmedinac.zora.ui.parts.model.CommunityInsightsState
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Privacy-gated community insights card with Cloud AI Opt-In dialog.
 */
@Composable
fun CommunityInsightsCard(
    communityState: CommunityInsightsState,
    showOptInModal: Boolean,
    onOptInClick: () -> Unit,
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
                    text = "COMMUNITY INSIGHTS & FLEET RADAR",
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ZoraTokens.Primary,
                )

                // Privacy Mode Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF380062))
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                ) {
                    Text(
                        text = communityState.sessionStatus,
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = ZoraTokens.Primary,
                    )
                }
            }

            // Blurred / Locked Stats preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ZoraTokens.ShapeMd)
                    .background(ZoraTokens.SurfaceContainerLow)
                    .padding(12.dp),
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Question of the week preview: Failure trend stats [BLURRED]",
                        fontFamily = ZoraTokens.FontMono,
                        fontSize = 11.sp,
                        color = ZoraTokens.Outline,
                    )
                    Text(
                        text = communityState.explanation,
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 11.sp,
                        color = ZoraTokens.OnSurfaceVariant,
                    )
                }
            }

            // Opt-In CTA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(CircleShape)
                    .background(ZoraTokens.SurfaceContainerHigh)
                    .border(1.dp, ZoraTokens.OutlineVariant, CircleShape)
                    .clickable(onClick = onOptInClick),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = communityState.ctaText,
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = ZoraTokens.OnSurface,
                )
            }

            if (showOptInModal) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(ZoraTokens.ShapeMd)
                        .background(ZoraTokens.SurfaceContainerHighest)
                        .border(1.dp, ZoraTokens.Primary, ZoraTokens.ShapeMd)
                        .padding(10.dp),
                ) {
                    Text(
                        text = "Privacy Preferences: Cloud AI Opt-In is disabled by default. Your telemetry remains strictly on-device until you explicitly choose to share anonymous diagnostic logs.",
                        fontFamily = ZoraTokens.FontBody,
                        fontSize = 11.sp,
                        color = ZoraTokens.OnSurface,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CommunityInsightsCardPreview() {
    ZoraTheme {
        CommunityInsightsCard(
            communityState = com.jesusdmedinac.zora.ui.parts.model.CommunityInsightsState(),
            showOptInModal = false,
            onOptInClick = {},
        )
    }
}
