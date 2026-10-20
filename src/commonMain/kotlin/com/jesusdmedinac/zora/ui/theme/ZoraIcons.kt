package com.jesusdmedinac.zora.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import org.jetbrains.compose.resources.DrawableResource
import zora_community.generated.resources.Res
import zora_community.generated.resources.ic_add_hig
import zora_community.generated.resources.ic_add_m3
import zora_community.generated.resources.ic_audio_hig
import zora_community.generated.resources.ic_audio_m3
import zora_community.generated.resources.ic_avatar_hig
import zora_community.generated.resources.ic_avatar_m3
import zora_community.generated.resources.ic_check_hig
import zora_community.generated.resources.ic_check_m3
import zora_community.generated.resources.ic_copy_hig
import zora_community.generated.resources.ic_copy_m3
import zora_community.generated.resources.ic_dropdown_hig
import zora_community.generated.resources.ic_dropdown_m3
import zora_community.generated.resources.ic_garage_hig
import zora_community.generated.resources.ic_garage_m3
import zora_community.generated.resources.ic_menu_hig
import zora_community.generated.resources.ic_menu_m3
import zora_community.generated.resources.ic_mic_hig
import zora_community.generated.resources.ic_mic_m3
import zora_community.generated.resources.ic_more_hig
import zora_community.generated.resources.ic_more_m3
import zora_community.generated.resources.ic_retry_hig
import zora_community.generated.resources.ic_retry_m3
import zora_community.generated.resources.ic_send_hig
import zora_community.generated.resources.ic_send_m3
import zora_community.generated.resources.ic_share_hig
import zora_community.generated.resources.ic_share_m3
import zora_community.generated.resources.ic_thumbs_down_hig
import zora_community.generated.resources.ic_thumbs_down_m3
import zora_community.generated.resources.ic_thumbs_up_hig
import zora_community.generated.resources.ic_thumbs_up_m3
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
    val copy: DrawableResource,
    val check: DrawableResource,
    val audio: DrawableResource,
    val retry: DrawableResource,
    val thumbsUp: DrawableResource,
    val thumbsDown: DrawableResource,
    val share: DrawableResource,
    val more: DrawableResource,
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
    copy = Res.drawable.ic_copy_m3,
    check = Res.drawable.ic_check_m3,
    audio = Res.drawable.ic_audio_m3,
    retry = Res.drawable.ic_retry_m3,
    thumbsUp = Res.drawable.ic_thumbs_up_m3,
    thumbsDown = Res.drawable.ic_thumbs_down_m3,
    share = Res.drawable.ic_share_m3,
    more = Res.drawable.ic_more_m3,
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
    copy = Res.drawable.ic_copy_hig,
    check = Res.drawable.ic_check_hig,
    audio = Res.drawable.ic_audio_hig,
    retry = Res.drawable.ic_retry_hig,
    thumbsUp = Res.drawable.ic_thumbs_up_hig,
    thumbsDown = Res.drawable.ic_thumbs_down_hig,
    share = Res.drawable.ic_share_hig,
    more = Res.drawable.ic_more_hig,
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

    val copy: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.copy

    val check: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.check

    val audio: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.audio

    val retry: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.retry

    val thumbsUp: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.thumbsUp

    val thumbsDown: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.thumbsDown

    val share: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.share

    val more: DrawableResource
        @Composable
        @ReadOnlyComposable
        get() = LocalZoraIcons.current.more
}
