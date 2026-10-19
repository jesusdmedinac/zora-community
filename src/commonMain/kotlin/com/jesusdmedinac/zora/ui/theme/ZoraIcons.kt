package com.jesusdmedinac.zora.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import org.jetbrains.compose.resources.DrawableResource
import zora_community.generated.resources.Res
import zora_community.generated.resources.ic_add_hig
import zora_community.generated.resources.ic_add_m3
import zora_community.generated.resources.ic_avatar_hig
import zora_community.generated.resources.ic_avatar_m3
import zora_community.generated.resources.ic_dropdown_hig
import zora_community.generated.resources.ic_dropdown_m3
import zora_community.generated.resources.ic_garage_hig
import zora_community.generated.resources.ic_garage_m3
import zora_community.generated.resources.ic_menu_hig
import zora_community.generated.resources.ic_menu_m3
import zora_community.generated.resources.ic_mic_hig
import zora_community.generated.resources.ic_mic_m3
import zora_community.generated.resources.ic_send_hig
import zora_community.generated.resources.ic_send_m3
import zora_community.generated.resources.ic_voice_waves_hig
import zora_community.generated.resources.ic_voice_waves_m3

/**
 * Universal icon set contract for Zora Automotive Intelligence.
 * Supports platform-adaptive switching between Material 3 (Google) and HIG (Apple SF Symbols).
 */
@Immutable
data class ZoraIconSet(
    val menu: DrawableResource,
    val garage: DrawableResource,
    val avatar: DrawableResource,
    val dropdown: DrawableResource,
    val add: DrawableResource,
    val mic: DrawableResource,
    val voiceWaves: DrawableResource,
    val send: DrawableResource,
)

/**
 * Official Material 3 (Google Material Symbols) icon set.
 */
val ZoraM3Icons = ZoraIconSet(
    menu = Res.drawable.ic_menu_m3,
    garage = Res.drawable.ic_garage_m3,
    avatar = Res.drawable.ic_avatar_m3,
    dropdown = Res.drawable.ic_dropdown_m3,
    add = Res.drawable.ic_add_m3,
    mic = Res.drawable.ic_mic_m3,
    voiceWaves = Res.drawable.ic_voice_waves_m3,
    send = Res.drawable.ic_send_m3,
)

/**
 * Official Human Interface Guidelines (Apple SF Symbols) icon set.
 */
val ZoraHIGIcons = ZoraIconSet(
    menu = Res.drawable.ic_menu_hig,
    garage = Res.drawable.ic_garage_hig,
    avatar = Res.drawable.ic_avatar_hig,
    dropdown = Res.drawable.ic_dropdown_hig,
    add = Res.drawable.ic_add_hig,
    mic = Res.drawable.ic_mic_hig,
    voiceWaves = Res.drawable.ic_voice_waves_hig,
    send = Res.drawable.ic_send_hig,
)

/**
 * CompositionLocal providing the active [ZoraIconSet].
 */
val LocalZoraIcons = staticCompositionLocalOf {
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

    val avatar: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.avatar

    val dropdown: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.dropdown

    val add: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.add

    val mic: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.mic

    val voiceWaves: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.voiceWaves

    val send: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.send
}
