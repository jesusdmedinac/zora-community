# Zora Community Edition (`zora-community`)

> Open-Source Mini-Zora Vehicle Search & Parts Catalog built with Compose Multiplatform.

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1+-purple.svg)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.7+-green.svg)](https://jetbrains.com/compose-multiplatform)
[![Kotlin Toolchain](https://img.shields.io/badge/Toolchain-Declarative%20YAML-orange.svg)](https://kotlin-toolchain.org)

**Author:** Jesús Daniel Medina Cruz ([github.com/jesusdmedinac](https://github.com/jesusdmedinac))  
**Methodology:** [The 5-Step AI Planning Process](https://github.com/jesusdmedinac/zora-community/blob/main/AGENTS.md)  
**Roadmap & Progress:** [`PROGRESS.md`](PROGRESS.md)  

---

## 1. What is Zora Community?

**Zora Community** is the free, open-source foundation of the Zora automotive ecosystem. It provides a complete, production-grade cross-platform application for Android, iOS, Desktop, and Web:

- 💬 **Agentic Conversational Onboarding:** Natural vehicle setup with generative UI cards (`json-to-compose`) for interactive trim selection and semantic care priorities.
- 🚗 **Virtual Garage & Telemetry:** Active vehicle dashboard with mileage tracking, 30-second fluid/tire quick check-in, and single-vehicle free slot governance.
- 🧠 **Inspectable Copilot Memory Vault:** Local-first memory storage (3 slots in Free tier) powered by SQLDelight 2.x on SQLite with manual per-item purge capabilities.
- ⚙️ **Contextual Parts Feed:** Precision fitment parts, Yonke (salvage yard) used parts with donor VIN tracking, and on-demand 3D engine bay component blueprint generator.
- 🤖 **Multi-Agent Network & Freemium Governance:** Autonomous multi-agent paywall showcasing Junkyard, Fuel, and Mechanic agents with Pro subscription tiers.
- 🏗️ **Clean Architecture & MVI:** Strict domain separation, Koin dependency injection, `kotlin-genai` integration with offline fallback, and unidirectional state flow.
- 🛠️ **Zero Gradle Configuration Debt:** Pre-configured with the official **Kotlin Toolchain (`kotlin-toolchain.org`)** using declarative `module.yaml` provisioning.


---

## 2. Using via `kmp-cli`

`zora-community` is the default, unrestricted open-core template bundled in **KMP CLI**:

```bash
# Scaffold a brand-new project instantly without any license key
kmp kit create zora-community --name MyAutoApp
```

---

## 3. Architecture Topology

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
├── module.yaml              # Declarative Kotlin Toolchain configuration
└── gradle/                  # Conventional build fallback
```

---

## 4. License

Licensed under the Apache License, Version 2.0. See [`LICENSE`](LICENSE) for details.
