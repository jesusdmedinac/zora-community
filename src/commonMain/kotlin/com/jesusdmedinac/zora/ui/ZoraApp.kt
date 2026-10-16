package com.jesusdmedinac.zora.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import com.jesusdmedinac.zora.ui.chat.OnboardingChatScreen
import com.jesusdmedinac.zora.ui.garage.GarageVaultScreen
import com.jesusdmedinac.zora.ui.home.CopilotHomeScreen
import com.jesusdmedinac.zora.ui.navigation.NavDisplay
import com.jesusdmedinac.zora.ui.navigation.ZoraRoute
import com.jesusdmedinac.zora.ui.parts.PartsFeedScreen
import com.jesusdmedinac.zora.ui.paywall.ZoraProPaywallScreen
import com.jesusdmedinac.zora.ui.splash.ZoraSplashScreen
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTheme

/**
 * Root Composable for Zora Automotive Intelligence.
 * Powered by Jetpack Navigation 3 (`NavBackStack`, `entryProvider`, `NavDisplay`).
 */
@Composable
fun ZoraApp(
    platformVariant: ZoraPlatformVariant = ZoraPlatformVariant.MATERIAL3,
    modifier: Modifier = Modifier,
) {
    ZoraTheme(platformVariant = platformVariant) {
        val backStack = remember { NavBackStack<ZoraRoute>(ZoraRoute.Splash) }

        val entryProvider = remember(backStack) {
            entryProvider<ZoraRoute> {
                entry<ZoraRoute.Splash> {
                    ZoraSplashScreen(
                        onIgnitionComplete = {
                            backStack.clear()
                            backStack.add(ZoraRoute.CopilotHome)
                        }
                    )
                }

                entry<ZoraRoute.CopilotHome> {
                    CopilotHomeScreen(
                        onNavigateToChat = { symptom ->
                            backStack.add(ZoraRoute.OnboardingChat(initialSymptom = symptom))
                        },
                        onNavigateToGarage = {
                            backStack.add(ZoraRoute.GarageVault)
                        },
                    )
                }

                entry<ZoraRoute.OnboardingChat> { route ->
                    OnboardingChatScreen(
                        initialSymptom = route.initialSymptom,
                        vehicleModel = route.vehicleModel,
                        onBack = {
                            if (backStack.size > 1) {
                                backStack.removeAt(backStack.lastIndex)
                            }
                        },
                    )
                }

                entry<ZoraRoute.GarageVault> {
                    GarageVaultScreen(
                        onBack = {
                            if (backStack.size > 1) {
                                backStack.removeAt(backStack.lastIndex)
                            }
                        },
                        onUpgradeToPro = {
                            backStack.add(ZoraRoute.ProPaywall(sourceReason = "Garage Slot Limit"))
                        },
                    )
                }

                entry<ZoraRoute.PartsFeed> {
                    PartsFeedScreen(
                        onBack = {
                            if (backStack.size > 1) {
                                backStack.removeAt(backStack.lastIndex)
                            }
                        },
                    )
                }

                entry<ZoraRoute.ProPaywall> {
                    ZoraProPaywallScreen(
                        onClose = {
                            if (backStack.size > 1) {
                                backStack.removeAt(backStack.lastIndex)
                            }
                        },
                    )
                }
            }
        }

        NavDisplay(
            backstack = backStack,
            modifier = modifier.fillMaxSize(),
            entryProvider = entryProvider,
        )
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun ZoraAppPreview() {
    ZoraApp()
}

