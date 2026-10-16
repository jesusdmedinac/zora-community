package com.jesusdmedinac.zora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jesusdmedinac.zora.ui.ZoraApp
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant

/**
 * Android Application Runner for Zora Community Edition.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ZoraApp(platformVariant = ZoraPlatformVariant.MATERIAL3)
        }
    }
}
