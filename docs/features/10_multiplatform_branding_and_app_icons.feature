Feature: Multiplatform Branding and App Icons
  As an automotive mobile and multiplatform developer
  I want all declared targets (Android, iOS, Desktop JVM, WebAssembly) to display the official Zora icon
  So that the application has consistent, high-fidelity branding across every runner

  Scenario: Display Zora adaptive launcher icon on Android
    Given the Android res directory has ic_launcher and ic_launcher_round mipmaps
    When AndroidManifest.xml configures the application icon
    Then android:icon is set to "@mipmap/ic_launcher"
    And android:roundIcon is set to "@mipmap/ic_launcher_round"

  Scenario: Display Zora 1024x1024 AppIcon in iOS xcassets
    Given the iOS xcassets directory contains AppIcon.appiconset
    When the iOS runner builds with Xcode
    Then app-icon-1024.png and Contents.json provide universal, dark, and tinted app icons

  Scenario: Configure Zora window icon on Desktop JVM runner
    Given Compose Multiplatform resources bundle zora_icon.xml
    When the Desktop main function initializes the application Window
    Then the window icon painter is configured with the Zora vector drawable

  Scenario: Mount Zora favicons and touch icons on WebAssembly Wasm runner
    Given the wasmJsMain resources directory contains favicon and touch icons
    When index.html is loaded by the browser
    Then link tags reference favicon.ico, favicon.svg, and apple-touch-icon.png
