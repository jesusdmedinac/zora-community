package com.jesusdmedinac.zora.ui.splash.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.LocalZoraPlatform
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import zora_community.generated.resources.security_protocol_footer

/**
 * Security Protocol Footer:
 * Renders the local-first cryptographic assurance badge and iOS Home Indicator when applicable.
 */
@Composable
fun SecurityProtocolFooter(
    modifier: Modifier = Modifier
) {
    val platform = LocalZoraPlatform.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = if (platform == ZoraPlatformVariant.HUMAN_INTERFACE) 12.dp else 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 1. Security Protocol Badge Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Shield Checkmark Vector Icon (14x14 dp)
            Canvas(modifier = Modifier.size(14.dp)) {
                val w = size.width
                val h = size.height

                // Shield Contour
                val shieldPath = Path().apply {
                    moveTo(w * 0.5f, h * 0.08f)
                    lineTo(w * 0.90f, h * 0.25f)
                    cubicTo(w * 0.90f, h * 0.65f, w * 0.50f, h * 0.95f, w * 0.50f, h * 0.95f)
                    cubicTo(w * 0.50f, h * 0.95f, w * 0.10f, h * 0.65f, w * 0.10f, h * 0.25f)
                    close()
                }
                drawPath(
                    path = shieldPath,
                    color = ZoraTokens.StatusSuccess,
                    style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                )

                // Checkmark inside shield
                val checkPath = Path().apply {
                    moveTo(w * 0.35f, h * 0.50f)
                    lineTo(w * 0.48f, h * 0.64f)
                    lineTo(w * 0.68f, h * 0.38f)
                }
                drawPath(
                    path = checkPath,
                    color = ZoraTokens.StatusSuccess,
                    style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                )
            }

            // Security Text
            Text(
                text = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.security_protocol_footer),
                style = TextStyle(
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = ZoraTokens.Outline
                )
            )
        }

        // 2. iOS Home Indicator Capsule (134 x 5 dp) if running on HIG / iOS
        if (platform == ZoraPlatformVariant.HUMAN_INTERFACE) {
            Box(
                modifier = Modifier
                    .width(134.dp)
                    .height(5.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.85f))
            )
        }
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun SecurityProtocolFooterPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        SecurityProtocolFooter()
    }
}

