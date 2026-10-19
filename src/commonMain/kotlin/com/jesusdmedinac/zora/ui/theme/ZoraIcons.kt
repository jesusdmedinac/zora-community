package com.jesusdmedinac.zora.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import org.jetbrains.compose.resources.DrawableResource
import zora_community.generated.resources.Res
import zora_community.generated.resources.ic_garage_hig
import zora_community.generated.resources.ic_garage_m3
import zora_community.generated.resources.ic_menu_hig
import zora_community.generated.resources.ic_menu_m3

/**
 * Universal icon set contract for Zora Automotive Intelligence.
 * Supports platform-adaptive switching between Material 3 (Google) and HIG (Apple SF Symbols).
 */
@Immutable
data class ZoraIconSet(
    val menu: DrawableResource,
    val garage: DrawableResource,
)

/**
 * Official Material 3 (Google Material Symbols) icon set.
 */
val ZoraM3Icons = ZoraIconSet(
    menu = Res.drawable.ic_menu_m3,
    garage = Res.drawable.ic_garage_m3,
)

/**
 * Official Human Interface Guidelines (Apple SF Symbols) icon set.
 */
val ZoraHIGIcons = ZoraIconSet(
    menu = Res.drawable.ic_menu_hig,
    garage = Res.drawable.ic_garage_hig,
)

/**
 * CompositionLocal providing the active [ZoraIconSet].
 */
val LocalZoraIcons = staticCompositionLocalOf<ZoraIconSet> {
    ZoraM3Icons
}

/**
 * Global accessor for Zora design system icons.
 */
object ZoraIcons {
    val menu: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.menu

    val garage: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.garage
}
