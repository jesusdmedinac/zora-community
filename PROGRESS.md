# Project Progress Roadmap: Zora Community Edition

Centralized BDD progress tracker for `zora-community` (Open-Source Mini-Zora & KMP-CLI Template).
Adheres strictly to the 5-Step AI Planning Process (`ai-planning-process`).

---

## Milestone: Stitch Design System & Mobile UI Specification
- [x] Scenario: Configure Zora Dark Titanium design system tokens in Stitch (Project: `8449783824763659218`)
- [x] Scenario: Generate 00_splash_screen (Minimalist launch screen with official Zora origami emblem and ambient glow)
- [x] Scenario: Generate 01_empty_chat (Welcoming zero-state chat with centered Zora emblem and starter prompts)
- [x] Scenario: Generate 01_onboarding_chat (Guided Onboarding Chat with json-to-compose generative trim cards and two-word status badges)
- [x] Scenario: Generate 02_garage_telemetry_vault (Virtual Garage with 30s fluid check-in and 3/3 memory slots with deletion)
- [x] Scenario: Generate 03_contextual_parts_feed (Contextual parts catalog with Yonke salvage parts, on-demand visual blueprint, and Cloud AI Opt-In gate)
- [x] Scenario: Generate 04_zora_pro_paywall (Zora Pro Multi-Agent Network, superpowers grid, and subscription paywall)

---

## Feature 05: Clean Architecture and Modern Tech Stack (Foundation)
- [ ] Scenario: Build project using declarative module.yaml
- [x] Scenario: Maintain unidirectional MVI state flow with Orbit MVI 12 (OrbitContainerHost, side effects, and collectAsState/collectSideEffect)
- [ ] Scenario: Inject multiplatform dependencies with Koin
- [ ] Scenario: Persist vehicles and telemetry locally with SQLDelight
- [ ] Scenario: Dynamically render agent generative UI with json-to-compose runtime
- [ ] Scenario: Process structured vehicle queries with kotlin-genai and offline fallback

---

## Feature 01: Agentic Onboarding and Generative UI
- [x] Scenario: Display clean startup splash screen
- [x] Scenario: Wire Jetpack Navigation 3 runtime with type-safe NavKey routes (Splash, CopilotHome, OnboardingChat, GarageVault)
- [x] Scenario: Welcome driver in empty chat state with quick starter chips and Copilot Home
- [x] Scenario: Implement Shifted Screen Scrim Card drawer transition (nodes VeQvH and RktHH)
- [x] Scenario: Render interactive vehicle engine selector card (node RFkA1)
- [x] Scenario: Present care priorities with semantic two-word badges
- [x] Scenario: Offer on-demand visual reference generation with disclaimer

---

## Feature 02: Garage Telemetry and Copilot Memory Vault
- [x] Scenario: Display primary vehicle showcase with mileage and health score
- [x] Scenario: Track copilot memory allocation within free 3-slot capacity
- [x] Scenario: Enforce single-vehicle garage slot limit for free tier
- [x] Scenario: Perform 30-second fluid and tire health check-in
- [x] Scenario: Delete individual copilot memory items on-demand

---

## Feature 03: Contextual Parts Feed and Visual Component Engine
- [x] Scenario: Filter parts feed by guaranteed vehicle fitment and category chips
- [x] Scenario: Highlight Yonke salvage yard parts with donor VIN and savings calculation
- [x] Scenario: Generate on-demand 3D component visual blueprint with monthly quota
- [x] Scenario: Gate fleet-wide community insights behind explicit Cloud AI Opt-In

---

## Feature 04: Multi-Agent Paywall and Freemium Governance
- [x] Scenario: Present autonomous multi-agent network status and nodes
- [x] Scenario: Display Pro superpowers matrix
- [x] Scenario: Toggle between annual and monthly subscription pricing
- [x] Scenario: Trigger paywall when exceeding free limits

---

## Feature 06: KMP CLI Template Packaging
- [ ] Scenario: Scaffold project without license requirements
- [ ] Scenario: Bundle manifest and template metadata
- [ ] Scenario: Bundle Generative UI and SQLDelight starter templates

---

## Feature 07: Diagnostic Triage, Value Gate, and Mechanical Prescription
- [x] Scenario: Record vehicle symptom via insertLocalTriage without network traffic
- [x] Scenario: Unlock Mechanical Prescription with donor matrix and collision quadrant exclusion
- [x] Scenario: Offline outbox queuing in garage or Web Wasm with background sync on reconnection
- [x] Scenario: Aggregate verified demand in Tijuana/Otay for commercial yard activation

---

## Feature 08: Living Foundation and Platform Runners (`docs/features/08_living_foundation_and_platform_runners.feature`)
- [x] Scenario: Launch Android mobile application runner
- [x] Scenario: Launch Desktop JVM application runner
- [x] Scenario: Launch WebAssembly Wasm browser runner
- [x] Scenario: Export iOS ComposeUIViewController entry point

---

## Feature 09: UI Component Atomization and Preview Suite (`docs/features/09_ui_component_atomization_and_preview_suite.feature`)
- [x] Scenario: Decompose monolithic screens into atomic components under component packages with zero private composables
- [x] Scenario: Equip all atomic and screen composable functions with dedicated @Preview composables wrapped in ZoraTheme
- [x] Scenario: Configure compose.components.uiToolingPreview and verify JVM test compilation
- [x] Scenario: Configure compose.uiTooling debug runtime adapter for Android Studio layout preview rendering
- [x] Scenario: Render headless offscreen screenshots of all core screen previews and atomic UI cards
- [x] Scenario: Audit ZoraIgnitionGlyph against zora-community.pen and backport atomic component to Component Library
- [x] Scenario: Align ZoraIgnitionGlyph with canonical 4-triangle square geometry and bounded Core Spectrum gradient
- [x] Scenario: Adaptive ZoraPromptComposer with dynamic voicewaves and send action swap

---

## Feature 10: Multiplatform Branding and App Icons (`docs/features/10_multiplatform_branding_and_app_icons.feature`)
- [x] Scenario: Display Zora adaptive launcher icon on Android
- [x] Scenario: Display Zora 1024x1024 AppIcon in iOS xcassets
- [x] Scenario: Configure Zora window icon on Desktop JVM runner
- [x] Scenario: Mount Zora favicons and touch icons on WebAssembly Wasm runner

---

## Feature 11: Multiplatform Internationalization and String Resources (`docs/features/11_multiplatform_i18n_and_string_resources.feature`)
- [x] Scenario: Define base English string resources in composeResources/values/strings.xml
- [x] Scenario: Define localized Spanish string resources in composeResources/values-es/strings.xml
- [x] Scenario: Refactor UI components to consume stringResource from Res.string

---

## Feature 12: Platform Adaptive System Insets and Edge Spacing (`docs/features/12_platform_adaptive_system_insets_and_edge_spacing.feature`)
- [x] Scenario: Calculate adaptive top edge spacing for Android and iOS
- [x] Scenario: Calculate adaptive bottom edge spacing for Android and iOS
- [x] Scenario: Apply adaptive edge padding across all core application screens






