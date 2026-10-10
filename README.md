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

**Zora Community** is the free, open-source foundation of the Zora automotive ecosystem. It provides a complete, production-grade cross-platform mobile application for Android, iOS, and Desktop:

- 🚗 **Vehicle Selector:** Interactive Year, Make, Model selection (e.g., 2021 Honda Civic EX 1.5L Turbo).
- ⚙️ **Parts Catalog & Search:** Fast category filtering (Engine, Brakes, Transmission, Body) and OEM part number cross-referencing.
- 🎨 **Modern Compose Multiplatform UI:** Fluid animations, responsive layout, and adaptive Material 3 theme (Dark/Light).
- 🏗️ **Clean Architecture & MVI:** Strict domain separation, Koin dependency injection, and unidirectional state flow.
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
