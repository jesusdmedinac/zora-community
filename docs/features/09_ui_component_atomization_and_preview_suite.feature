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

  Scenario: Align UserChatBubble with Component Library specification in zora-community.pen
    Given the User Message Bubble specification (node S9vmE in M3 and BBb2K in HIG) in design/zora-community.pen
    When rendering UserChatBubble with SurfaceContainerHigh fill, 18dp corner radius, and 10x16dp padding
    Then the bubble aligns to the right edge with 48dp leading inset
    And the message text is styled in OnSurface color and FontBody typography
    And headless offscreen preview screenshots card_chat_user_bubble and card_chat_user_bubble_hig are verified

  Scenario: Align AssistantChatBubble with containerless design specification in zora-community.pen
    Given the Assistant Chat directive ("Containerless: sin burbuja gris, directo sobre $canvas") in design/zora-community.pen
    When rendering AssistantChatBubble without container backgrounds, clips, or borders
    Then the response text renders directly over the canvas in OnSurface color with 15sp and 22sp line height
    And generative UI cards and ZoraAssistantActionBar align organically underneath
    And headless offscreen preview screenshots card_chat_assistant_message and card_chat_assistant_message_hig are verified

  Scenario: Align ZoraAssistantActionBar with Component Library specification in zora-community.pen
    Given the Assistant Action Bar specification (node iqjWy in M3 and WIZZ0 in HIG) in design/zora-community.pen
    When rendering ZoraAssistantActionBar with 32x32dp circular action buttons and 10dp gap
    Then the action bar displays 6 micro-actions: copy, audio, thumbs up, thumbs down, share, and more
    And emojis are strictly prohibited and replaced with crisp multiplatform vector drawables
    And headless offscreen preview screenshots card_assistant_action_bar and card_assistant_action_bar_hig are verified

  Scenario: Align ActionButton with transparent background and OnSurface typography matching AssistantChatBubble
    Given the ActionButton component used in ZoraAssistantActionBar
    When rendering ActionButton with 32x32dp circular touch target and transparent background
    Then the action icon tint is aligned with AssistantChatBubble text color in OnSurface
    And the button provides circular ripple indication on interaction
    And headless offscreen preview screenshots card_action_button_m3 and card_action_button_hig are verified

  Scenario: Animated copy-to-checkmark transition, clipboard integration, and bottom toast notification
    Given the assistant message action bar in active chat
    When the user clicks the copy action button
    Then the message text is copied to the system clipboard
    And the copy icon animates smoothly to a checkmark with positive confirmation
    And a floating Dark Titanium toast slides in from the bottom with localized text
    And after a duration the checkmark reverts to the copy icon and the toast slides down
    And headless offscreen preview screenshots card_toast_m3, card_toast_hig, and 01d_copilot_home_active_toast are verified

  Scenario: Align ZoraShiftedDrawerLayout and atomic navigation drawer components with zora-community.pen
    Given the navigation drawer structure defined in frames VeQvH and RktHH of zora-community.pen
    When rendering ZoraShiftedDrawerLayout with 280dp navigation drawer and shifted screen scrim card
    Then DrawerHeader displays the core spectrum ignition glyph and crisp circular close button
    And NewChatButton displays the plus vector icon with Primary tint and SurfaceContainerHigh background
    And DrawerMenuItem renders package, wrench, and fuel vector icons with pill status badges
    And DrawerRecentChatsList lists recent diagnostic consultations with text ellipsis
    And DrawerUserFooter renders the top shader fade strip, avatar with 0.5dp border, and settings action button
    And all emoji characters are eliminated and replaced with platform-adaptive vector drawables
    And headless offscreen preview screenshots 01b_shifted_drawer_open, 01b_shifted_drawer_open_hig, and atomic drawer cards are verified

  Scenario: Align ZoraDrawerHeader with canonical ignition glyph and accessible close button
    Given the drawer header defined in node Q0RPQ of zora-community.pen
    When rendering ZoraDrawerHeader with 24dp ignition glyph and 36dp circular close button
    Then the canonical ZoraIgnitionGlyph is displayed with subtle breathing scale animation
    And the brand title "Zora" is rendered in FontHeadline 18sp bold
    And the close button provides circular ripple feedback and semantic Role.Button accessibility
    And headless offscreen preview screenshots card_drawer_header_m3 and card_drawer_header_hig are verified

  Scenario: Align ZoraNewChatButton with dynamic press feedback and parameterized slot contract
    Given the new chat action button defined in node ay6yS of zora-community.pen
    When rendering ZoraNewChatButton with 44dp height and full capsule corner radius
    Then the button scales smoothly to 0.98x with border and container color transition on press
    And the leading icon displays the plus vector drawable tinted in Primary amber
    And the label is rendered in FontBody 14sp semibold with OnSurface fill
    And the component provides semantic Role.Button accessibility and backward compatibility alias
    And headless offscreen preview screenshots card_drawer_new_chat_button_m3 and card_drawer_new_chat_button_hig are verified

  Scenario: Align ZoraDrawerMenuItem with active selection state and press micro-interactions
    Given the drawer navigation menu items defined in nodes m2wOA, kzDef, and HeQx3 of zora-community.pen
    When rendering ZoraDrawerMenuItem with 44dp capsule container and 12dp horizontal padding
    Then the item supports resting, pressed, and active selected states
    And resting state displays OnSurface or OnSurfaceVariant with transparent container fill
    And selected state transitions to SurfaceContainerHigh background and semibold label styling
    And pressing the item triggers an animated scale micro-interaction to 0.985x
    And upcoming features render a pill badge with SurfaceContainerHigh background and Primary amber label
    And the component provides semantic Role.Button accessibility and backward compatibility alias
    And headless offscreen preview screenshots card_drawer_menu_item_m3, card_drawer_menu_item_hig, card_drawer_menu_item_selected_m3, and card_drawer_menu_item_selected_hig are verified

  Scenario: Navigate to Zora Pro Paywall from drawer mechanic and gas stations menu items
    Given the shifted drawer open with upcoming Pro menu items drawer_menu_mechanic and drawer_menu_gas_stations
    When the user clicks on the Mechanic or Gas Stations navigation row
    Then the drawer closes automatically
    And the application navigates to ZoraRoute.ProPaywall with the corresponding sourceReason
    And the Pro Paywall renders the Autonomous Multi-Agent Network and subscription options

  Scenario: Align ZoraDrawerUserFooter with pinned layout, shader fade, and interactive gear settings button
    Given the pinned user footer defined in node cBBbL of zora-community.pen
    When rendering ZoraDrawerUserFooter with 28dp top shader fade and exact padding [4, 16, 12, 16]
    Then the user avatar displays the circular 36dp container with outline border and user icon
    And the user text column renders the user name in FontBody 14sp semibold and account tier in Primary 11sp medium
    And the settings button responds to press events with 0.92x scale damping and 25-degree gear rotation
    And the component provides semantic Role.Button accessibility and backward compatibility alias
    And headless offscreen preview screenshots card_drawer_user_footer_m3 and card_drawer_user_footer_hig are verified

  Scenario: Align ZoraDrawerRecentChatsList and ZoraDrawerRecentChatItem with active selection and press micro-interactions
    Given the recent chats section defined in node fnMOF of zora-community.pen
    When rendering ZoraDrawerRecentChatsList with 4dp vertical item spacing
    Then the section header renders "Recent" in FontBody 11sp semibold with Outline fill
    And each recent chat item displays the question text in FontBody 13sp with single line ellipsis truncation
    And clicking a recent question applies spring-damped 0.985x press feedback and triggers onRecentQuestionClick
    And selected recent questions transition to SurfaceContainerHigh container background and Primary label tint
    And the component provides semantic Role.Button accessibility and backward compatibility aliases
    And headless offscreen preview screenshots card_drawer_recent_chats_m3, card_drawer_recent_chats_hig, card_drawer_recent_chats_selected_m3, and card_drawer_recent_chats_selected_hig are verified














