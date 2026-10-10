# Guidelines for AI Agents: Zora Community (`zora-community`)

This document defines core guidelines, engineering standards, and workflows for AI agents working within the `zora-community` repository.

## 1. Primary Language Policy: English Only
* **All documentation, source code, unit tests, inline comments, commit messages, and BDD feature files MUST be written strictly in English.**
* Do not write or commit documentation or code in Spanish unless explicitly requested for localized end-user facing translation files (i18n).

## 2. Methodology: The 5-Step AI Planning Process
This repository strictly adheres to Behavior-Driven Development (BDD) and the 5-step AI planning process:
1. **Define Idea in Natural Language:** High-level *what*, not *how*.
2. **Generate Gherkin Features & Scenarios:** Specify units of work under `docs/features/*.feature`.
3. **Persist & Sync:** Keep feature files version-controlled and synchronized with issue trackers.
4. **Maintain `PROGRESS.md`:** Keep [`PROGRESS.md`](file:///Users/jesusdmedinac/proyectos/Zora/zora-community/PROGRESS.md) continuously updated at the beginning and end of each turn.
5. **Develop Incrementally:** Implement scenario-by-scenario with 100% TDD in domain logic and atomic git commits referencing the feature.

## 3. Mission & Template Integration with `kmp-cli`
`zora-community` serves two complementary roles:
1. **Standalone Open-Source Reference App:** A fully functional, production-ready Mini-Zora vehicle search and parts catalog app running on Android, iOS, and Desktop.
2. **Official Open-Core Template for `kmp-cli`:** Serves as the base template for `kmp kit create zora-community`, available to any developer with zero license or paywall required.

## 4. Technology & Architecture Standards
* **Build System & Toolchain:** Pure declarative `module.yaml` configuration via the official [Kotlin Toolchain](https://kotlin-toolchain.org) with standard Gradle fallback.
* **100% Kotlin Foundation Alignment:**
  * **UI:** Jetpack Compose Multiplatform + Material 3 (Dark/Light themes).
  * **Architecture:** Clean Architecture + MVI/UDF (StateFlow, ViewModel from `androidx.lifecycle`).
  * **Navigation:** Jetpack Navigation Compose KMP.
  * **Dependency Injection:** Koin (`koin-compose`).
  * **Networking & Serialization:** Ktor Client 3.x + `kotlinx.serialization`.
  * **Testing:** Kotlin Test + Kotest assertions + Turbine for Flow testing.
* **Domain Purity:** `:core:domain` has zero dependencies on UI frameworks or Android SDKs.
