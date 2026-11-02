package com.jesusdmedinac.zora.ui.navigation

import androidx.compose.material3.Text
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Navigation3ApiTest {
    @Test
    fun testZoraRouteBackStackNavigationFlow() {
        val backStack = NavBackStack<ZoraRoute>(ZoraRoute.Splash)
        assertEquals(1, backStack.size)
        assertEquals(ZoraRoute.Splash, backStack.first())

        // Boot complete -> Navigate to Copilot Home
        backStack.clear()
        backStack.add(ZoraRoute.CopilotHome)
        assertEquals(1, backStack.size)
        assertEquals(ZoraRoute.CopilotHome, backStack.first())

        // Navigate to Onboarding Chat with initial symptom
        backStack.add(ZoraRoute.OnboardingChat(initialSymptom = "Frenos rechinan"))
        assertEquals(2, backStack.size)
        val current = backStack.last() as ZoraRoute.OnboardingChat
        assertEquals("Frenos rechinan", current.initialSymptom)
        assertEquals("Honda Civic 2018", current.vehicleModel)

        // Navigate to Garage Vault
        backStack.add(ZoraRoute.GarageVault)
        assertEquals(3, backStack.size)
        assertEquals(ZoraRoute.GarageVault, backStack.last())

        // Pop back to Chat
        val popped = backStack.removeAt(backStack.lastIndex)
        assertEquals(ZoraRoute.GarageVault, popped)
        assertEquals(2, backStack.size)
        assertEquals(current, backStack.last())

        // Navigate to Pro Paywall from drawer mechanic item
        backStack.add(ZoraRoute.ProPaywall(sourceReason = "drawer_menu_mechanic"))
        assertEquals(3, backStack.size)
        val mechanicPaywall = backStack.last() as ZoraRoute.ProPaywall
        assertEquals("drawer_menu_mechanic", mechanicPaywall.sourceReason)

        // Pop back
        backStack.removeAt(backStack.lastIndex)
        assertEquals(2, backStack.size)

        // Navigate to Pro Paywall from drawer gas stations item
        backStack.add(ZoraRoute.ProPaywall(sourceReason = "drawer_menu_gas_stations"))
        assertEquals(3, backStack.size)
        val gasPaywall = backStack.last() as ZoraRoute.ProPaywall
        assertEquals("drawer_menu_gas_stations", gasPaywall.sourceReason)
    }

    @Test
    fun testZoraRouteEntryProviderResolution() {
        val provider = entryProvider<ZoraRoute> {
            entry<ZoraRoute.Splash> {
                Text("Splash Screen")
            }
            entry<ZoraRoute.CopilotHome> {
                Text("Copilot Home")
            }
            entry<ZoraRoute.OnboardingChat> { route ->
                Text("Chat: ${route.initialSymptom}")
            }
            entry<ZoraRoute.GarageVault> {
                Text("Garage Vault")
            }
            entry<ZoraRoute.ProPaywall> { route ->
                Text("Pro Paywall: ${route.sourceReason}")
            }
        }

        val splashEntry = provider(ZoraRoute.Splash)
        assertEquals(ZoraRoute.Splash.toString(), splashEntry.contentKey)

        val homeEntry = provider(ZoraRoute.CopilotHome)
        assertEquals(ZoraRoute.CopilotHome.toString(), homeEntry.contentKey)

        val chatRoute = ZoraRoute.OnboardingChat(initialSymptom = "DTC P0300")
        val chatEntry = provider(chatRoute)
        assertEquals(chatRoute.toString(), chatEntry.contentKey)

        val garageEntry = provider(ZoraRoute.GarageVault)
        assertEquals(ZoraRoute.GarageVault.toString(), garageEntry.contentKey)

        val paywallRoute = ZoraRoute.ProPaywall(sourceReason = "drawer_menu_mechanic")
        val paywallEntry = provider(paywallRoute)
        assertEquals(paywallRoute.toString(), paywallEntry.contentKey)
    }
}
