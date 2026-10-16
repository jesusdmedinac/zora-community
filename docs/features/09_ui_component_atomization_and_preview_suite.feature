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
