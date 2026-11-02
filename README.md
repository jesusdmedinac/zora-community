# Zora Community Edition (`zora-community`)

> 🚗 **Open-Source Mini-Zora Vehicle Search & Parts Catalog built with Compose Multiplatform.**

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1+-purple.svg)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.7+-green.svg)](https://jetbrains.com/compose-multiplatform)
[![Kotlin Toolchain](https://img.shields.io/badge/Toolchain-Declarative%20YAML-orange.svg)](https://kotlin-toolchain.org)
[![Powered by json-to-compose](https://img.shields.io/badge/UI%20Engine-json--to--compose-00C853.svg)](https://github.com/jesusdmedinac/json-to-compose)
[![Scaffolded with KMP CLI](https://img.shields.io/badge/Tooling-KMP%20CLI-blueviolet.svg)](https://jesusdmedinac.github.io/KMP-CLI/)
[![CI](https://github.com/jesusdmedinac/zora-community/actions/workflows/ci.yml/badge.svg)](https://github.com/jesusdmedinac/zora-community/actions)

**Author:** Jesús Daniel Medina Cruz ([github.com/jesusdmedinac](https://github.com/jesusdmedinac))  
**Methodology:** [The 5-Step AI Planning Process](https://github.com/jesusdmedinac/zora-community/blob/main/AGENTS.md)  
**Roadmap & Progress:** [`PROGRESS.md`](PROGRESS.md)  
**Documentation Hub:** [KMP CLI Ecosystem](https://jesusdmedinac.github.io/KMP-CLI/ecosystem/zora-community/)

---

## 1. What is Zora Community?

**Zora Community** is the free, open-source foundation of the Zora automotive ecosystem. It provides a complete, production-grade cross-platform application for Android, iOS, Desktop, and Web:

- 💬 **Agentic Conversational Onboarding:** Natural vehicle setup with generative UI cards ([`json-to-compose`](https://github.com/jesusdmedinac/json-to-compose)) for interactive trim selection and semantic care priorities.
- 🚗 **Virtual Garage & Telemetry:** Active vehicle dashboard with mileage tracking, 30-second fluid/tire quick check-in, and single-vehicle free slot governance.
- 🧠 **Inspectable Copilot Memory Vault:** Local-first memory storage (3 slots in Free tier) powered by SQLDelight 2.x on SQLite with manual per-item purge capabilities.
- ⚙️ **Contextual Parts Feed:** Precision fitment parts, Yonke (salvage yard) used parts with donor VIN tracking, and on-demand 3D engine bay component blueprint generator.
- 🤖 **Multi-Agent Network & Freemium Governance:** Autonomous multi-agent paywall showcasing Junkyard, Fuel, and Mechanic agents with Pro subscription tiers.
- 🏗️ **Clean Architecture & MVI:** Strict domain separation, Koin dependency injection, `kotlin-genai` integration with offline fallback, and unidirectional state flow.
- 🛠️ **Zero Gradle Configuration Debt:** Pre-configured with the official **Kotlin Toolchain ([kotlin-toolchain.org](https://kotlin-toolchain.org))** using declarative `module.yaml` provisioning.

---

## 2. Quick Start & Local Execution

Clone and run the application locally across supported runners:

```bash
# 1. Clone the repository
git clone https://github.com/jesusdmedinac/zora-community.git
cd zora-community

# 2. Run Desktop application (macOS / Linux / Windows)
./gradlew :composeApp:run

# 3. Run all domain and multiplatform unit tests
./gradlew --no-daemon jvmTest

# 4. Generate headless Skia offscreen screenshot suite (Dark Titanium Design Audit)
./gradlew --no-daemon jvmTest --tests com.jesusdmedinac.zora.ui.PreviewScreenshotGeneratorTest
```

---

## 3. Scaffolding via `kmp-cli`

`zora-community` is the official, unrestricted open-core template bundled in **[KMP CLI](https://jesusdmedinac.github.io/KMP-CLI/)**:

```bash
# Scaffold a brand-new project instantly without any license key
kmp kit create zora-community --name MyAutoApp
```

---

## 4. GitHub Actions & Continuous Integration

This repository includes continuous integration workflows for pull request verification and release health:

```yaml
name: Zora Community CI

on:
  push:
    branches: [main]
  pull_request:

jobs:
  verify:
    name: Build, Lint & Multiplatform Tests
    runs-on: macos-latest
    steps:
      - name: Checkout repository
        uses: actions/checkout@v4

      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '21'

      - name: Run Domain & JVM Tests
        run: ./gradlew --no-daemon jvmTest

      - name: Run Skia Offscreen UI Previews
        run: ./gradlew --no-daemon jvmTest --tests com.jesusdmedinac.zora.ui.PreviewScreenshotGeneratorTest
```

---

## 5. Architecture Topology

```
zora-community/
├── composeApp/              # Shared Compose Multiplatform UI (Android, iOS, Desktop)
│   ├── src/commonMain/      # Screens, Navigation, Theme, UI State
│   ├── src/androidMain/     # Android MainActivity & application entry
│   └── src/iosMain/         # iOS UIViewController bridge
├── core/
│   ├── domain/              # Vehicle & Part domain models, UseCases, Repository interfaces
│   ├── data/                # Mock data fixtures & Ktor Client 3.x repository implementations
│   └── di/                  # Koin multiplatform module declarations
├── design/                  # Living UI design specs (zora-community.pen)
├── module.yaml              # Declarative Kotlin Toolchain configuration
└── gradle/                  # Conventional build fallback
```

---

## 6. License

Licensed under the Apache License, Version 2.0. See [`LICENSE`](LICENSE) for details.
