Feature: Clean Architecture and Modern Tech Stack
  As a mobile software engineer inspecting or extending Zora Community
  I want a modular Clean Architecture setup powered by declarative tooling, SQLDelight, kotlin-genai, and json-to-compose
  So that I can build, test, and run on Android, iOS, Desktop, and Web Wasm with zero boilerplate and robust local-first performance

  Scenario: Build project using declarative module.yaml
    Given the project root contains "module.yaml" configured with Kotlin Toolchain 0.12+
    When the multiplatform project is assembled
    Then targets "android", "iosArm64", "iosSimulatorArm64", "jvm", and "wasmJs" compile successfully
    And zero imperative Gradle build scripts are required for module dependency wiring

  Scenario: Maintain unidirectional MVI state flow with ViewModel and StateFlow
    Given the presentation layer for chat, garage, and parts
    When a user interaction intent is dispatched through the MVI channel
    Then the state updates deterministically through StateFlow
    And the UI observes the state reactively using Jetpack Compose Multiplatform

  Scenario: Inject multiplatform dependencies with Koin
    Given the shared dependency graph is initialized
    When Koin modules are loaded on app startup
    Then repositories, use cases, SQLDelight databases, and view models are resolved cleanly across all platforms

  Scenario: Persist vehicles and telemetry locally with SQLDelight
    Given the SQLDelight database is configured with multiplatform SQLite drivers (including Web Wasm)
    When vehicles, telemetry check-ins, parts cache, and copilot memory items are persisted
    Then data is stored in the local SQLite file on the device
    And queries execute asynchronously with Kotlin Coroutines and Flows

  Scenario: Dynamically render agent generative UI with json-to-compose runtime
    Given the AI copilot generates a structured JSON payload for a UI widget
    When the `json-to-compose` runtime parses the schema
    Then native Compose Multiplatform components (cards, chips, buttons) are rendered directly in the chat feed
    And user interactions trigger callbacks that dispatch back to the MVI intent pipeline

  Scenario: Process structured vehicle queries with kotlin-genai and offline fallback
    Given a user asks a vehicle diagnostic question
    When the copilot evaluates the prompt using the `kotlin-genai` SDK
    Then the prompt uses structured schema generation with safety settings
    And if internet connectivity is unavailable, the copilot falls back cleanly to local rule-based diagnostics
