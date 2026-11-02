Feature: Platform Adaptive System Insets and Edge Spacing
  As a Zora driver across Android, iOS, and Desktop platforms
  I want the user interface to respect platform-specific top and bottom edge spaces
  So that headers, navigation drawers, and bottom action composers never collide with system status bars, notches, dynamic islands, or navigation gesture bars.

  Background:
    Given the Zora design system defines platform system chrome tokens in "ZoraTokens"
    And "LocalZoraPlatform" provides the active platform variant ("MATERIAL3" or "HUMAN_INTERFACE")

  Scenario: Calculate adaptive top edge spacing for Android and iOS
    Given the driver is running on "MATERIAL3" platform variant
    When the top edge padding is computed
    Then the top padding should allocate at least 28dp for the Android status bar
    And when the driver is running on "HUMAN_INTERFACE" platform variant
    Then the top padding should allocate at least 54dp for the iOS Dynamic Island header

  Scenario: Calculate adaptive bottom edge spacing for Android and iOS
    Given the driver is running on "MATERIAL3" platform variant
    When the bottom edge padding is computed
    Then the bottom padding should allocate at least 16dp for the Android navigation gesture bar
    And when the driver is running on "HUMAN_INTERFACE" platform variant
    Then the bottom padding should allocate at least 24dp for the iOS Home Indicator bar

  Scenario: Apply adaptive edge padding across all core application screens
    Given the core screens "ZoraSplashScreen", "CopilotHomeScreen", "ZoraShiftedDrawerLayout", "OnboardingChatScreen", "GarageVaultScreen", "PartsFeedScreen", and "ZoraProPaywallScreen"
    When each screen renders on its active platform
    Then the root container applies "zoraEdgePadding" to protect top and bottom edges
    And the application renders edge-to-edge with continuous dark canvas background
