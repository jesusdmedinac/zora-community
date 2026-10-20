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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesusdmedinac.zora.ui.splash.component.ZoraIgnitionGlyph
import com.jesusdmedinac.zora.ui.theme.ZoraIcons
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zora_community.generated.resources.Res
import zora_community.generated.resources.drawer_menu_close

/**
 * Brand header for the shifted navigation drawer with canonical ignition glyph and close button.
 * Synchronized with nodes SKSWB and Q0RPQ from `zora-community.pen`.
 */
@Composable
fun ZoraDrawerHeader(
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "Zora",
    animatedGlyph: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Brand Group (node ay6Mb): Ignition Glyph & Brand Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            ZoraIgnitionGlyph(
                size = 24.dp,
                animated = animatedGlyph,
            )

            Text(
                text = title,
                fontFamily = ZoraTokens.FontHeadline,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = ZoraTokens.OnSurface,
            )
        }

        // Close Drawer Button (node YjNpC)
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(ZoraTokens.SurfaceContainerLow)
                .clickable(
                    role = Role.Button,
                    onClick = onCloseDrawer,
                ),
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
private fun ZoraDrawerHeaderPreview() {
    ZoraTheme {
        ZoraDrawerHeader(
            onCloseDrawer = {},
            animatedGlyph = false,
        )
    }
}
