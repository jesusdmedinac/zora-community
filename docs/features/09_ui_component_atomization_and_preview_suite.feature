Feature: UI Component Atomization and Preview Suite
  As a mobile engineer designing vehicle UI components
  I want each UI element to be an atomized, independently previewable Composable function
  So that components can be tested in isolation and rendered in Android Studio Previews without runtime missing class errors

  Scenario: Decompose monolithic screens into atomic components under component packages with zero private composables
    Given monolithic screen implementations across splash, chat, garage, parts, and paywall
    When decomposing each screen into modular architectural components
    Then every extracted component composable has public visibility and resides in its domain components package
    And the screen composable orchestrates the extracted components cleanly

  Scenario: Equip all atomic and screen composable functions with dedicated @Preview composables wrapped in ZoraTheme
    Given all extracted UI components and screen composables
    When authoring preview functions for design-time rendering
    Then each component is equipped with a dedicated @Preview composable function
    And each preview wraps the target component in ZoraTheme with default preview parameters

  Scenario: Configure compose.components.uiToolingPreview and verify JVM test compilation
    Given multiplatform Compose dependencies in commonMain
    When adding compose.components.uiToolingPreview to commonMain
    Then preview annotations compile across all targets including commonMain and jvmTest

  Scenario: Configure compose.uiTooling debug runtime adapter for Android Studio layout preview rendering
    Given Android Studio design-tools requires ComposeViewAdapter to inflate and render previews
    When adding compose.uiTooling to the debugImplementation classpath
    Then the Android debug variant compiles with androidx.compose.ui.tooling.ComposeViewAdapter available
    And Android Studio successfully inflates and renders @Preview composables without ClassNotFoundException

  Scenario: Render headless offscreen screenshots of all core screen previews and atomic UI cards
    Given the Compose Desktop offscreen rendering engine ImageComposeScene
    When rendering the atomized preview composables in a simulated mobile viewport
    Then high-resolution PNG screenshots are exported for all primary screens and component cards
    And screenshots are saved for visual verification and artifact presentation

  Scenario: Audit ZoraIgnitionGlyph against zora-community.pen and backport atomic component to Component Library
    Given the atomic Composable function ZoraIgnitionGlyph
    When auditing geometry, 135° Core Spectrum gradient, and ambient halo against zora-community.pen
    Then 100% visual parity is confirmed across nominal coordinates and token hexes
    And Card - Zora Ignition Glyph is backported and registered into M3 and HIG Component Library frames in zora-community.pen

  Scenario: Align ZoraIgnitionGlyph with canonical 4-triangle square geometry and bounded Core Spectrum gradient
    Given the canonical 4-triangle square emblem ic_zora_transparent in design/zora-community.pen
    When rendering the vector paths in DrawScope with bounded 135° Core Spectrum gradient
    Then the procedural Canvas matches the 4-triangle geometry and aspect ratio
    And offscreen preview screenshots confirm sharp vector rendering across targets

  Scenario: Adaptive ZoraPromptComposer with dynamic voicewaves and send action swap
    Given the floating prompt composer component ZoraPromptComposer
    When the prompt text input is empty
    Then the right action button displays the adaptive voiceWaves icon
    And clicking the action button initiates live voice call mode
    When the user types at least one character of prompt text
    Then the right action button swaps immediately to the adaptive send icon
    And clicking the action button sends the prompt message
    And live voice call mode is strictly locked and cannot be activated

  Scenario: Align CopilotPromptHero with Centered Welcome Hero in zora-community.pen and backport to Component Library
    Given the Centered Welcome Hero specification (node IP21f in M3 and J062l in HIG) in design/zora-community.pen
    When rendering CopilotPromptHero with the 96dp ZoraIgnitionGlyph and 24sp Bold ignition headline
    Then 100% visual and token parity is achieved across M3 and HIG viewports
    And Card - Copilot Prompt Hero is backported into M3 and HIG Component Library frames in zora-community.pen
    And headless offscreen preview screenshots card_copilot_prompt_hero and card_copilot_prompt_hero_hig are verified

  Scenario: In-place conversational chat stream and zero-state conditional switching in Copilot Home
    Given a driver on the Copilot Home screen
    When the conversational message list is empty
    Then the screen presents the Centered Welcome Hero and quick suggestions row
    When the driver selects a suggestion chip or sends a prompt text
    Then the Centered Welcome Hero and suggestions row are hidden
    And the conversational chat stream (CopilotChatList) renders the user bubble and copilot assistant reply
    And sending new messages automatically scrolls to the bottom
    And triggering a new chat resets the message list and restores the welcome hero



