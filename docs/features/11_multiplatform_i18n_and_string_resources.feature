Feature: Multiplatform Internationalization and String Resources
  As a global vehicle owner and driver
  I want all UI screens and components to support English and Spanish localization
  So that I can use Zora in my preferred language seamlessly across Android, iOS, Desktop, and Web

  Scenario: Define base English string resources in composeResources/values/strings.xml
    Given the Compose Multiplatform resources directory src/commonMain/composeResources/values
    When defining the canonical strings.xml with English definitions
    Then string identifiers cover splash, home, chat, garage, parts, and paywall domains
    And strings compile into type-safe Res.string accessors in commonMain

  Scenario: Define localized Spanish string resources in composeResources/values-es/strings.xml
    Given the Spanish localization directory src/commonMain/composeResources/values-es
    When defining strings.xml with matching keys translated into automotive Spanish
    Then 100% of string keys in base strings.xml have a corresponding Spanish translation
    And terminology maintains strict zero-jargon and precision automotive naming

  Scenario: Refactor UI components to consume stringResource from Res.string
    Given composables across splash, home, chat, garage, parts, and paywall
    When replacing hardcoded string literals with stringResource(Res.string.<key>)
    Then all UI labels, badges, and button text dynamically resolve from Compose Resources
    And the application compiles and passes JVM screenshot tests without regressions
