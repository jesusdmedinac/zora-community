# Project Progress Roadmap: Zora Community Edition

Centralized BDD progress tracker for `zora-community` (Open-Source Mini-Zora & KMP-CLI Template).

---

## Milestone: Stitch Design System & Mobile UI Specification
- [x] Scenario: Configure Zora Dark Titanium design system tokens in Stitch (Project: `8449783824763659218`)
- [x] Scenario: Generate 00_splash_screen (Ignition & Telemetry Boot with Local Room & Privacy badges)
- [x] Scenario: Generate 01_onboarding_chat_genui (Guided Onboarding Chat with json-to-compose generative cards)
- [ ] Scenario: Generate 02_garage_telemetry_vault (Virtual Garage with 30s fluid check-in and 3/3 memory slots)
- [ ] Scenario: Generate 03_contextual_parts_feed (Contextual parts catalog with on-demand reference images)
- [ ] Scenario: Generate 04_zora_pro_paywall (Zora Pro Multi-Agent Network and subscription paywall)

## Feature: Clean Architecture and Declarative Toolchain Setup (03)
- [ ] Scenario: Build project using declarative module.yaml
- [ ] Scenario: Maintain unidirectional MVI state flow with ViewModel and StateFlow
- [ ] Scenario: Inject multiplatform dependencies with Koin

## Feature: Vehicle Catalog and Selection (01)
- [ ] Scenario: Browse supported automotive makes
- [ ] Scenario: Filter vehicle models by selected year and make
- [ ] Scenario: Save selected active vehicle in memory and UI state

## Feature: Parts Catalog and Search (02)
- [ ] Scenario: Browse auto parts categories
- [ ] Scenario: Search parts by keyword with live debounce filtering
- [ ] Scenario: View detailed part specification and OEM interchange numbers

## Feature: KMP CLI Template Packaging (04)
- [ ] Scenario: Scaffold project without license requirements
- [ ] Scenario: Bundle manifest and template metadata
