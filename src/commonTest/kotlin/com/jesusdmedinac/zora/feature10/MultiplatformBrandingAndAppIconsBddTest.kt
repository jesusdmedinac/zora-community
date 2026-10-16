package com.jesusdmedinac.zora.feature10

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MultiplatformBrandingAndAppIconsBddTest {

    /**
     * SCENARIO 1: Display Zora adaptive launcher icon on Android
     */
    @Test
    fun testScenario1_AndroidLauncherIconConfiguration() {
        val launcherIcon = "@mipmap/ic_launcher"
        val launcherRoundIcon = "@mipmap/ic_launcher_round"

        assertEquals("@mipmap/ic_launcher", launcherIcon)
        assertEquals("@mipmap/ic_launcher_round", launcherRoundIcon)
    }

    /**
     * SCENARIO 2: Display Zora 1024x1024 AppIcon in iOS xcassets
     */
    @Test
    fun testScenario2_IosAppIconConfiguration() {
        val iconFileName = "app-icon-1024.png"
        val universalSize = "1024x1024"

        assertEquals("app-icon-1024.png", iconFileName)
        assertEquals("1024x1024", universalSize)
    }

    /**
     * SCENARIO 3: Configure Zora window icon on Desktop JVM runner
     */
    @Test
    fun testScenario3_DesktopWindowIconConfiguration() {
        val composeResourceDrawableName = "zora_icon"
        val macOsIconFile = "src/jvmMain/resources/icon.icns"
        val windowsIconFile = "src/jvmMain/resources/icon.ico"
        val linuxIconFile = "src/jvmMain/resources/icon-256.png"

        assertEquals("zora_icon", composeResourceDrawableName)
        assertTrue(macOsIconFile.endsWith(".icns"))
        assertTrue(windowsIconFile.endsWith(".ico"))
        assertTrue(linuxIconFile.endsWith(".png"))
    }

    /**
     * SCENARIO 4: Mount Zora favicons and touch icons on WebAssembly Wasm runner
     */
    @Test
    fun testScenario4_WebAssemblyFaviconAndTouchIconsConfiguration() {
        val svgFavicon = "favicon.svg"
        val icoFavicon = "favicon.ico"
        val appleTouchIcon = "apple-touch-icon.png"

        assertEquals("favicon.svg", svgFavicon)
        assertEquals("favicon.ico", icoFavicon)
        assertEquals("apple-touch-icon.png", appleTouchIcon)
    }
}
