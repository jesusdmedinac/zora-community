Feature: Living Foundation and Platform Runners
  As a cross-platform automotive intelligence engineer
  I want each declared target to wire to a verified, executable entry point
  So that the application runs natively on Android, Desktop JVM, WebAssembly (Wasm), and iOS

  Scenario: Launch Android mobile application runner
    Given the Android target is configured with namespace "com.jesusdmedinac.zora" and SDK 35
    When the Android application component initializes
    Then MainActivity inherits ComponentActivity and sets root content to ZoraApp
    And AndroidManifest.xml configures MainActivity as the launchable MAIN activity

  Scenario: Launch Desktop JVM application runner
    Given the Desktop target is configured with Compose desktop application
    When the Desktop main function executes
    Then a window is created titled "Zora Community" rendering ZoraApp
    And the application exits cleanly when closed

  Scenario: Launch WebAssembly Wasm browser runner
    Given the wasmJs target is configured with browser and canvas support
    When the web application loads index.html in a modern browser
    Then CanvasBasedWindow binds to canvas element "ComposeTarget"
    And ZoraApp is mounted within the WebAssembly canvas

  Scenario: Export iOS ComposeUIViewController entry point
    Given the iOS target is configured with framework export
    When MainViewController is instantiated from Swift or UIKit
    Then ComposeUIViewController mounts and renders ZoraApp
