package com.jesusdmedinac.zora.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.theme.ZoraIcons
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.Res
import zora_community.generated.resources.drawer_menu_close

/**
 * Brand header for the shifted navigation drawer with glyph and close button.
 * Synchronized with node Q0RPQ from `zora-community.pen`.
 */
@Composable
fun DrawerHeader(
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(ZoraTokens.CoreSpectrumBrush),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            }
            Text(
                text = "Zora",
                fontFamily = ZoraTokens.FontHeadline,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = ZoraTokens.OnSurface,
            )
        }

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(ZoraTokens.SurfaceContainerLow)
                .clickable(onClick = onCloseDrawer),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(ZoraIcons.close),
                contentDescription = stringResource(Res.string.drawer_menu_close),
                tint = ZoraTokens.OnSurface,
                modifier = Modifier.size(18.dp),
            )
        }
    }
}

@Preview
@Composable
private fun DrawerHeaderPreview() {
    ZoraTheme {
        DrawerHeader(onCloseDrawer = {})
    }
}
