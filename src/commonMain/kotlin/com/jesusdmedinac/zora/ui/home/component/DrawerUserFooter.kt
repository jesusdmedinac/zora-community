package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import zora_community.generated.resources.tier_pro

/**
 * Pinned user profile footer inside navigation drawer with avatar and settings button.
 */
@Composable
fun DrawerUserFooter(
    userName: String = "Jesús Medina",
    userInitials: String = "JM",
    accountTier: String = org.jetbrains.compose.resources.stringResource(zora_community.generated.resources.Res.string.tier_pro),
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(ZoraTokens.PrimaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = userInitials,
                    color = ZoraTokens.OnPrimaryContainer,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = userName,
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ZoraTokens.OnSurface,
                )
                Text(
                    text = accountTier,
                    fontFamily = ZoraTokens.FontBody,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = ZoraTokens.Primary,
                )
            }
        }

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .clickable(onClick = onSettingsClick),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "⚙️",
                fontSize = 18.sp,
            )
        }
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun DrawerUserFooterPreview() {
    com.jesusdmedinac.zora.ui.theme.ZoraTheme {
        DrawerUserFooter(onSettingsClick = {})
    }
}

