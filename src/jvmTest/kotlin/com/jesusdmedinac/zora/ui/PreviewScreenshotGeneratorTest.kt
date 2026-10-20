package com.jesusdmedinac.zora.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.jetbrains.skia.EncodedImageFormat
import com.jesusdmedinac.zora.ui.chat.OnboardingChatScreen
import com.jesusdmedinac.zora.ui.chat.component.ActionButton
import com.jesusdmedinac.zora.ui.chat.component.AssistantChatBubble
import com.jesusdmedinac.zora.ui.chat.component.UserChatBubble
import com.jesusdmedinac.zora.ui.chat.component.ZoraAssistantActionBar
import com.jesusdmedinac.zora.ui.chat.component.ZoraToast
import com.jesusdmedinac.zora.ui.chat.component.rememberZoraToastState
import com.jesusdmedinac.zora.ui.garage.GarageVaultScreen
import com.jesusdmedinac.zora.ui.garage.component.CopilotMemoryVaultCard
import com.jesusdmedinac.zora.ui.garage.component.GarageSlotsCard
import com.jesusdmedinac.zora.ui.garage.component.PrimaryVehicleCard
import com.jesusdmedinac.zora.ui.garage.component.QuickHealthCheckInCard
import com.jesusdmedinac.zora.ui.garage.model.TelemetryCheckInState
import com.jesusdmedinac.zora.ui.garage.model.defaultCopilotMemories
import com.jesusdmedinac.zora.ui.home.CopilotHomeScreen
import com.jesusdmedinac.zora.ui.home.CopilotHomeScreenContent
import com.jesusdmedinac.zora.ui.home.component.CopilotChatList
import com.jesusdmedinac.zora.ui.home.component.CopilotPromptHero
import com.jesusdmedinac.zora.ui.home.component.ZoraDrawerHeader
import com.jesusdmedinac.zora.ui.home.component.DrawerMenuItem
import com.jesusdmedinac.zora.ui.home.component.DrawerRecentChatsList
import com.jesusdmedinac.zora.ui.home.component.DrawerUserFooter
import com.jesusdmedinac.zora.ui.home.component.NewChatButton
import com.jesusdmedinac.zora.ui.home.component.ZoraPromptComposer
import com.jesusdmedinac.zora.ui.home.component.ZoraShiftedDrawerLayout
import com.jesusdmedinac.zora.ui.home.model.CopilotChatMessage
import com.jesusdmedinac.zora.ui.home.model.CopilotHomeUiState
import com.jesusdmedinac.zora.ui.parts.PartsFeedScreen
import com.jesusdmedinac.zora.ui.parts.component.CommunityInsightsCard
import com.jesusdmedinac.zora.ui.parts.component.PartCard
import com.jesusdmedinac.zora.ui.parts.component.VisualBlueprintCard
import com.jesusdmedinac.zora.ui.parts.model.CommunityInsightsState
import com.jesusdmedinac.zora.ui.parts.model.VisualBlueprintState
import com.jesusdmedinac.zora.ui.parts.model.defaultPartsCatalog
import com.jesusdmedinac.zora.ui.paywall.ZoraProPaywallScreen
import com.jesusdmedinac.zora.ui.paywall.component.AgentNetworkCard
import com.jesusdmedinac.zora.ui.paywall.component.ProSuperpowersMatrix
import com.jesusdmedinac.zora.ui.paywall.component.SubscriptionPlansSelector
import com.jesusdmedinac.zora.ui.paywall.model.SubscriptionBilling
import com.jesusdmedinac.zora.ui.paywall.model.defaultAgentNodes
import com.jesusdmedinac.zora.ui.paywall.model.defaultProSuperpowers
import com.jesusdmedinac.zora.ui.splash.ZoraSplashScreen
import com.jesusdmedinac.zora.ui.splash.component.ZoraIgnitionGlyph
import com.jesusdmedinac.zora.ui.theme.ZoraPlatformVariant
import com.jesusdmedinac.zora.ui.theme.ZoraTheme
import com.jesusdmedinac.zora.ui.theme.ZoraTokens
import com.jesusdmedinac.zora.ui.theme.zoraBottomEdgePadding
import com.jesusdmedinac.zora.ui.theme.zoraTopEdgePadding
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Headless off-screen screenshot generator using Compose Desktop's ImageComposeScene.
 * Renders high-fidelity PNG screenshots for all atomized screens and key UI cards.
 */
class PreviewScreenshotGeneratorTest {

    private fun capture(
        name: String,
        width: Int = 390,
        height: Int = 844,
        platformVariant: ZoraPlatformVariant = ZoraPlatformVariant.MATERIAL3,
        content: @Composable () -> Unit,
    ) {
        val scene = ImageComposeScene(width = width, height = height, density = Density(1f))
        scene.setContent {
            val lifecycleOwner: LifecycleOwner = remember {
                object : LifecycleOwner {
                    override val lifecycle: Lifecycle = LifecycleRegistry.createUnsafe(this).apply {
                        currentState = Lifecycle.State.RESUMED
                    }
                }
            }
            val viewModelStoreOwner: ViewModelStoreOwner = remember {
                val store = ViewModelStore()
                object : ViewModelStoreOwner {
                    override val viewModelStore: ViewModelStore = store
                }
            }
            CompositionLocalProvider(
                LocalLifecycleOwner provides lifecycleOwner,
                LocalViewModelStoreOwner provides viewModelStoreOwner,
            ) {
                ZoraTheme(platformVariant = platformVariant) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(ZoraTokens.Canvas)
                    ) {
                        content()
                    }
                }
            }
        }
        // First render initializes state, second produces the settled frame
        scene.render()
        val image = scene.render()
        val data = image.encodeToData(EncodedImageFormat.PNG)
        val dir = File("build/screenshots")
        dir.mkdirs()
        val file = File(dir, "$name.png")
        file.writeBytes(data!!.bytes)
        println("Generated preview screenshot: ${file.name} (${file.length()} bytes)")
    }

    @Test
    fun captureAllScreenPreviews() {
        // 1. Splash Screen
        capture("00_splash_screen", width = 390, height = 844) {
            ZoraSplashScreen()
        }

        // 2. Copilot Home (Empty chat state with prompt composer)
        capture("01_copilot_home", width = 390, height = 844) {
            CopilotHomeScreen(
                onNavigateToChat = {},
                onNavigateToGarage = {},
            )
        }

        // 2b. Copilot Home (Active chat stream with user and assistant bubbles)
        capture("01c_copilot_home_active_chat", width = 390, height = 844) {
            CopilotHomeScreenContent(
                uiState = CopilotHomeUiState(
                    messages = listOf(
                        CopilotChatMessage.User(
                            text = "¿Por qué tiembla el volante a más de 80 km/h?",
                            id = "preview_user_1",
                        ),
                        CopilotChatMessage.Assistant(
                            text = "Para tu Honda Civic EX 2.0L: la vibración a alta velocidad suele indicar deformación en rotores de freno o desbalanceo en neumáticos delanteros.",
                            id = "preview_asst_1",
                        ),
                    ),
                ),
                onIntent = {},
            )
        }

        // 3. Shifted Screen Scrim Card Drawer (Open navigation drawer)
        capture("01b_shifted_drawer_open", width = 390, height = 844) {
            ZoraShiftedDrawerLayout(
                drawerOpen = true,
                onCloseDrawer = {},
                onNewChatClick = {},
                onYonkeClick = {},
                onRecentQuestionClick = {},
                onSettingsClick = {},
            ) {
                CopilotHomeScreen(
                    onNavigateToChat = {},
                    onNavigateToGarage = {},
                )
            }
        }

        // 4. Onboarding Chat (Interactive diagnostic triage stream)
        capture("02_onboarding_chat", width = 390, height = 844) {
            OnboardingChatScreen(
                initialSymptom = "Vibración al frenar a más de 80 km/h",
                onBack = {},
            )
        }

        // 5. Garage Vault (Primary vehicle telemetry, 30s checkin, memory slots)
        capture("03_garage_vault", width = 390, height = 844) {
            GarageVaultScreen(onBack = {})
        }

        // 6. Contextual Parts Feed (Yonke salvage network & 3D cutaway blueprint)
        capture("04_parts_feed", width = 390, height = 844) {
            PartsFeedScreen(onBack = {})
        }

        // 7. Zora Pro Paywall (Multi-agent network, superpowers grid, plan selector)
        capture("05_zora_pro_paywall", width = 390, height = 844) {
            ZoraProPaywallScreen(onClose = {})
        }
    }

    @Test
    fun captureAtomicComponentCards() {
        // Primary Vehicle Card
        capture("card_primary_vehicle", width = 390, height = 230) {
            Box(modifier = Modifier.padding(16.dp)) {
                PrimaryVehicleCard()
            }
        }

        // Quick Health Check-In Card
        capture("card_quick_health_checkin", width = 390, height = 230) {
            Box(modifier = Modifier.padding(16.dp)) {
                QuickHealthCheckInCard(
                    telemetry = TelemetryCheckInState(),
                )
            }
        }

        // Copilot Memory Vault Card
        capture("card_copilot_memory_vault", width = 390, height = 320) {
            Box(modifier = Modifier.padding(16.dp)) {
                CopilotMemoryVaultCard(
                    memories = defaultCopilotMemories(),
                    onDeleteMemory = {},
                )
            }
        }

        // Garage Slots Card
        capture("card_garage_slots", width = 390, height = 200) {
            Box(modifier = Modifier.padding(16.dp)) {
                GarageSlotsCard(
                    showProModal = false,
                    onSlotClick = {},
                )
            }
        }

        // Yonke Salvage Part Card
        capture("card_part_salvage", width = 390, height = 280) {
            Box(modifier = Modifier.padding(16.dp)) {
                val salvagePart = defaultPartsCatalog().first { it.isSalvage }
                PartCard(item = salvagePart)
            }
        }

        // Visual Blueprint Card
        capture("card_visual_blueprint", width = 390, height = 340) {
            Box(modifier = Modifier.padding(16.dp)) {
                VisualBlueprintCard(
                    blueprintState = VisualBlueprintState(quotaCount = 1),
                    onRequestBlueprint = {},
                )
            }
        }

        // Community Insights Privacy Gate Card
        capture("card_community_insights", width = 390, height = 320) {
            Box(modifier = Modifier.padding(16.dp)) {
                CommunityInsightsCard(
                    communityState = CommunityInsightsState(),
                    showOptInModal = false,
                    onOptInClick = {},
                )
            }
        }

        // Autonomous Agent Network Card
        capture("card_agent_network", width = 390, height = 280) {
            Box(modifier = Modifier.padding(16.dp)) {
                AgentNetworkCard(agentNodes = defaultAgentNodes())
            }
        }

        // Pro Superpowers Matrix
        capture("card_superpowers_matrix", width = 390, height = 240) {
            Box(modifier = Modifier.padding(16.dp)) {
                ProSuperpowersMatrix(superpowers = defaultProSuperpowers())
            }
        }

        // Subscription Plans Selector
        capture("card_subscription_selector", width = 390, height = 230) {
            Box(modifier = Modifier.padding(16.dp)) {
                SubscriptionPlansSelector(
                    selectedBilling = SubscriptionBilling.ANNUAL,
                    onSelectBilling = {},
                )
            }
        }

        // Copilot Chat List Card
        capture("card_copilot_chat_list", width = 390, height = 320) {
            Box(modifier = Modifier.padding(16.dp)) {
                CopilotChatList(
                    messages = listOf(
                        CopilotChatMessage.User(
                            text = "¿Por qué tiembla el volante a más de 80 km/h?",
                            id = "preview_user_1",
                        ),
                        CopilotChatMessage.Assistant(
                            text = "Para tu Honda Civic EX 2.0L: la vibración a alta velocidad suele indicar deformación en rotores de freno delanteros.",
                            id = "preview_asst_1",
                        ),
                    ),
                )
            }
        }

        // Chat User Message Bubble (Material 3)
        capture("card_chat_user_bubble", width = 390, height = 80, platformVariant = ZoraPlatformVariant.MATERIAL3) {
            Box(modifier = Modifier.padding(16.dp)) {
                UserChatBubble(text = "Tengo un Honda Civic 2018")
            }
        }

        // Chat User Message Bubble (HIG)
        capture("card_chat_user_bubble_hig", width = 390, height = 80, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            Box(modifier = Modifier.padding(16.dp)) {
                UserChatBubble(text = "Tengo un Honda Civic 2018")
            }
        }

        // Chat Assistant Message (Containerless Material 3)
        capture("card_chat_assistant_message", width = 390, height = 90, platformVariant = ZoraPlatformVariant.MATERIAL3) {
            Box(modifier = Modifier.padding(16.dp)) {
                AssistantChatBubble(
                    text = "Para tu Honda Civic EX 2.0L: la vibración a alta velocidad comúnmente indica desbalanceo de neumáticos o ligera deformación en rotores delanteros.",
                )
            }
        }

        // Chat Assistant Message (Containerless HIG)
        capture("card_chat_assistant_message_hig", width = 390, height = 90, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            Box(modifier = Modifier.padding(16.dp)) {
                AssistantChatBubble(
                    text = "Para tu Honda Civic EX 2.0L: la vibración a alta velocidad comúnmente indica desbalanceo de neumáticos o ligera deformación en rotores delanteros.",
                )
            }
        }

        // Assistant Action Bar (Material 3)
        capture("card_assistant_action_bar", width = 390, height = 64, platformVariant = ZoraPlatformVariant.MATERIAL3) {
            Box(modifier = Modifier.padding(16.dp)) {
                ZoraAssistantActionBar()
            }
        }

        // Assistant Action Bar (HIG)
        capture("card_assistant_action_bar_hig", width = 390, height = 64, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            Box(modifier = Modifier.padding(16.dp)) {
                ZoraAssistantActionBar()
            }
        }

        // Action Button Atomic Previews (32x32dp, transparent background, OnSurface icon tint)
        capture("card_action_button_m3", width = 80, height = 64, platformVariant = ZoraPlatformVariant.MATERIAL3) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                ActionButton(
                    icon = ZoraTheme.icons.copy,
                    contentDescription = "Copy",
                    onClick = {},
                )
            }
        }

        capture("card_action_button_hig", width = 80, height = 64, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                ActionButton(
                    icon = ZoraTheme.icons.copy,
                    contentDescription = "Copy",
                    onClick = {},
                )
            }
        }

        // Toast Previews (Floating Dark Titanium capsule)
        capture("card_toast_m3", width = 200, height = 56, platformVariant = ZoraPlatformVariant.MATERIAL3) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                ZoraToast(text = "Text copied")
            }
        }

        capture("card_toast_hig", width = 200, height = 56, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                ZoraToast(text = "Texto copiado")
            }
        }

        // Full-screen Copilot Home with active Toast notification
        capture("01d_copilot_home_active_toast", width = 390, height = 844, platformVariant = ZoraPlatformVariant.MATERIAL3) {
            CopilotHomeScreenContent(
                uiState = CopilotHomeUiState(
                    messages = listOf(
                        CopilotChatMessage.User(
                            text = "¿Por qué tiembla el volante a más de 80 km/h?",
                            id = "preview_user_1",
                        ),
                        CopilotChatMessage.Assistant(
                            text = "Para tu Honda Civic EX 2.0L: la vibración a alta velocidad suele indicar deformación en rotores de freno o desbalanceo en neumáticos delanteros.",
                            id = "preview_asst_1",
                        ),
                    ),
                ),
                onIntent = {},
                toastState = rememberZoraToastState(initialMessage = "Text copied"),
            )
        }

        // Drawer Header (M3 & HIG)
        capture("card_drawer_header_m3", width = 280, height = 60, platformVariant = ZoraPlatformVariant.MATERIAL3) {
            Box(modifier = Modifier.padding(16.dp)) {
                ZoraDrawerHeader(onCloseDrawer = {}, animatedGlyph = false)
            }
        }
        capture("card_drawer_header_hig", width = 280, height = 60, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            Box(modifier = Modifier.padding(16.dp)) {
                ZoraDrawerHeader(onCloseDrawer = {}, animatedGlyph = false)
            }
        }

        // New Chat Button (M3 & HIG)
        capture("card_drawer_new_chat_button_m3", width = 280, height = 60, platformVariant = ZoraPlatformVariant.MATERIAL3) {
            Box(modifier = Modifier.padding(16.dp)) {
                NewChatButton(onClick = {})
            }
        }
        capture("card_drawer_new_chat_button_hig", width = 280, height = 60, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            Box(modifier = Modifier.padding(16.dp)) {
                NewChatButton(onClick = {})
            }
        }

        // Drawer Menu Item (M3 & HIG)
        capture("card_drawer_menu_item_m3", width = 280, height = 60, platformVariant = ZoraPlatformVariant.MATERIAL3) {
            Box(modifier = Modifier.padding(16.dp)) {
                DrawerMenuItem(
                    icon = ZoraTheme.icons.wrench,
                    label = "Mecánico",
                    badge = "Próximamente",
                    enabled = false,
                )
            }
        }
        capture("card_drawer_menu_item_hig", width = 280, height = 60, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            Box(modifier = Modifier.padding(16.dp)) {
                DrawerMenuItem(
                    icon = ZoraTheme.icons.wrench,
                    label = "Mecánico",
                    badge = "Próximamente",
                    enabled = false,
                )
            }
        }

        // Drawer Recent Chats (M3 & HIG)
        capture("card_drawer_recent_chats_m3", width = 280, height = 200, platformVariant = ZoraPlatformVariant.MATERIAL3) {
            Box(modifier = Modifier.padding(16.dp)) {
                DrawerRecentChatsList(onRecentQuestionClick = {})
            }
        }
        capture("card_drawer_recent_chats_hig", width = 280, height = 200, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            Box(modifier = Modifier.padding(16.dp)) {
                DrawerRecentChatsList(onRecentQuestionClick = {})
            }
        }

        // Drawer User Footer (M3 & HIG)
        capture("card_drawer_user_footer_m3", width = 280, height = 100, platformVariant = ZoraPlatformVariant.MATERIAL3) {
            DrawerUserFooter(onSettingsClick = {})
        }
        capture("card_drawer_user_footer_hig", width = 280, height = 100, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            DrawerUserFooter(onSettingsClick = {})
        }
    }

    @Test
    fun captureZoraIgnitionGlyph() {
        // Canonical 96dp Ignition Glyph preview
        capture("glyph_zora_ignition_96", width = 200, height = 200) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ZoraIgnitionGlyph(
                    size = 96.dp,
                    animated = false
                )
            }
        }

        // High-definition 160dp detail inspection
        capture("glyph_zora_ignition_160", width = 300, height = 300) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ZoraIgnitionGlyph(
                    size = 160.dp,
                    animated = false
                )
            }
        }
    }

    @Test
    fun captureCopilotPromptHero() {
        // M3 Prompt Hero (node IP21f)
        capture("card_copilot_prompt_hero", width = 390, height = 260, platformVariant = ZoraPlatformVariant.MATERIAL3) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ZoraTokens.Canvas)
                    .padding(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                CopilotPromptHero(animated = false)
            }
        }

        // HIG Prompt Hero (node J062l)
        capture("card_copilot_prompt_hero_hig", width = 390, height = 260, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ZoraTokens.Canvas)
                    .padding(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                CopilotPromptHero(animated = false)
            }
        }
    }

    @Test
    fun capturePromptComposerVariants() {
        // M3 - Empty (Voice waves button)
        capture("composer_m3_voice_waves", width = 390, height = 90, platformVariant = ZoraPlatformVariant.MATERIAL3) {
            Box(modifier = Modifier.padding(16.dp)) {
                ZoraPromptComposer(
                    value = "",
                    onValueChange = {},
                    onSend = {},
                )
            }
        }

        // M3 - Populated (Send button)
        capture("composer_m3_send", width = 390, height = 90, platformVariant = ZoraPlatformVariant.MATERIAL3) {
            Box(modifier = Modifier.padding(16.dp)) {
                ZoraPromptComposer(
                    value = "¿Por qué tiembla el volante a 80 km/h?",
                    onValueChange = {},
                    onSend = {},
                )
            }
        }

        // HIG - Empty (Voice waves button)
        capture("composer_hig_voice_waves", width = 390, height = 90, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            Box(modifier = Modifier.padding(16.dp)) {
                ZoraPromptComposer(
                    value = "",
                    onValueChange = {},
                    onSend = {},
                )
            }
        }

        // HIG - Populated (Send button)
        capture("composer_hig_send", width = 390, height = 90, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            Box(modifier = Modifier.padding(16.dp)) {
                ZoraPromptComposer(
                    value = "¿Por qué tiembla el volante a 80 km/h?",
                    onValueChange = {},
                    onSend = {},
                )
            }
        }
    }

    @Test
    fun captureHigScreenPreviews() {
        // HIG Variant Screen Previews (iOS Dynamic Island 54dp + Home Indicator 24dp)
        capture("00_splash_screen_hig", width = 390, height = 844, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            ZoraSplashScreen()
        }

        capture("01_copilot_home_hig", width = 390, height = 844, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            CopilotHomeScreen(
                onNavigateToChat = {},
                onNavigateToGarage = {},
            )
        }

        capture("01b_shifted_drawer_open_hig", width = 390, height = 844, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            ZoraShiftedDrawerLayout(
                drawerOpen = true,
                onCloseDrawer = {},
                onNewChatClick = {},
                onYonkeClick = {},
                onRecentQuestionClick = {},
                onSettingsClick = {},
            ) {
                CopilotHomeScreen(
                    onNavigateToChat = {},
                    onNavigateToGarage = {},
                )
            }
        }

        capture("01c_copilot_home_active_chat_hig", width = 390, height = 844, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            CopilotHomeScreenContent(
                uiState = CopilotHomeUiState(
                    messages = listOf(
                        CopilotChatMessage.User(
                            text = "¿Por qué tiembla el volante a más de 80 km/h?",
                            id = "preview_user_1",
                        ),
                        CopilotChatMessage.Assistant(
                            text = "Para tu Honda Civic EX 2.0L: la vibración a alta velocidad suele indicar deformación en rotores de freno o desbalanceo en neumáticos delanteros.",
                            id = "preview_asst_1",
                        ),
                    ),
                ),
                onIntent = {},
            )
        }

        capture("02_onboarding_chat_hig", width = 390, height = 844, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            OnboardingChatScreen(
                initialSymptom = "Vibración al frenar a más de 80 km/h",
                onBack = {},
            )
        }

        capture("03_garage_vault_hig", width = 390, height = 844, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            GarageVaultScreen(onBack = {})
        }

        capture("04_parts_feed_hig", width = 390, height = 844, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            PartsFeedScreen(onBack = {})
        }

        capture("05_zora_pro_paywall_hig", width = 390, height = 844, platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
            ZoraProPaywallScreen(onClose = {})
        }
    }

    @Test
    fun verifyAdaptiveEdgePaddingCalculations() {
        var m3Top = 0.dp
        var m3Bottom = 0.dp
        var higTop = 0.dp
        var higBottom = 0.dp

        val sceneM3 = ImageComposeScene(width = 390, height = 844, density = Density(1f))
        sceneM3.setContent {
            ZoraTheme(platformVariant = ZoraPlatformVariant.MATERIAL3) {
                m3Top = zoraTopEdgePadding()
                m3Bottom = zoraBottomEdgePadding()
            }
        }
        sceneM3.render()

        val sceneHIG = ImageComposeScene(width = 390, height = 844, density = Density(1f))
        sceneHIG.setContent {
            ZoraTheme(platformVariant = ZoraPlatformVariant.HUMAN_INTERFACE) {
                higTop = zoraTopEdgePadding()
                higBottom = zoraBottomEdgePadding()
            }
        }
        sceneHIG.render()

        assertEquals(ZoraTokens.StatusBarHeightM3, m3Top)
        assertEquals(ZoraTokens.NavigationBarHeightM3, m3Bottom)
        assertEquals(ZoraTokens.StatusBarHeightHIG, higTop)
        assertEquals(ZoraTokens.HomeIndicatorHeightHIG, higBottom)
    }
}

