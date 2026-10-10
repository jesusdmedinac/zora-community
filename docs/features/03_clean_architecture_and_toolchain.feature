Feature: Clean Architecture and Declarative Toolchain Setup
  As a mobile software engineer inspecting or extending Zora Community
  I want a modular Clean Architecture setup powered by the declarative Kotlin Toolchain
  So that I can build and run on Android, iOS, and Desktop with zero Gradle configuration debt

  Scenario: Build project using declarative module.yaml
    Given the project root contains "module.yaml" configured with Kotlin Toolchain 0.12+
    When the multiplatform project is assembled
    Then targets "android", "iosArm64", "iosSimulatorArm64", and "jvm" compile successfully
    And zero imperative Gradle build scripts are required for module dependency wiring

  Scenario: Maintain unidirectional MVI state flow with ViewModel and StateFlow
    Given the vehicle and parts presentation layers
    When a user interaction event is dispatched through the MVI intent channel
    Then the state updates deterministically through StateFlow
    And the UI observes the state reactively using Jetpack Compose Multiplatform

  Scenario: Inject multiplatform dependencies with Koin
    Given the shared dependency graph is initialized
    When Koin modules are loaded on app startup
    Then repositories, use cases, and view models are resolved cleanly across all platforms
