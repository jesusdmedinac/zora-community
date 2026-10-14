# Project Progress Roadmap: Zora Community Edition

Centralized BDD progress tracker for `zora-community` (Open-Source Mini-Zora & KMP-CLI Template).
Adheres strictly to the 5-Step AI Planning Process (`ai-planning-process`).

---

## Milestone: Stitch Design System & Mobile UI Specification
- [x] Scenario: Configure Zora Dark Titanium design system tokens in Stitch (Project: `8449783824763659218`)
- [x] Scenario: Generate 00_splash_screen (Minimalist launch screen with official Zora origami emblem and ambient glow)
- [x] Scenario: Generate 01_empty_chat (Welcoming zero-state chat with centered Zora emblem and starter prompts)
- [x] Scenario: Generate 01_onboarding_chat (Guided Onboarding Chat with json-to-compose generative trim cards and two-word status badges)
- [x] Scenario: Generate 02_garage_telemetry_vault (Virtual Garage with 30s fluid check-in and 3/3 memory slots with deletion)
- [x] Scenario: Generate 03_contextual_parts_feed (Contextual parts catalog with Yonke salvage parts, on-demand visual blueprint, and Cloud AI Opt-In gate)
- [x] Scenario: Generate 04_zora_pro_paywall (Zora Pro Multi-Agent Network, superpowers grid, and subscription paywall)

---

## Feature 05: Clean Architecture and Modern Tech Stack (Foundation)
- [ ] Scenario: Build project using declarative module.yaml
- [ ] Scenario: Maintain unidirectional MVI state flow with ViewModel and StateFlow
- [ ] Scenario: Inject multiplatform dependencies with Koin
- [ ] Scenario: Persist vehicles and telemetry locally with SQLDelight
- [ ] Scenario: Dynamically render agent generative UI with json-to-compose runtime
- [ ] Scenario: Process structured vehicle queries with kotlin-genai and offline fallback

---

## Feature 01: Agentic Onboarding and Generative UI
- [ ] Scenario: Display clean startup splash screen
- [ ] Scenario: Welcome driver in empty chat state with quick starter chips
- [ ] Scenario: Render interactive vehicle trim selector via Generative UI
- [ ] Scenario: Present care priorities with semantic two-word badges
- [ ] Scenario: Offer on-demand visual reference generation with disclaimer

---

## Feature 02: Garage Telemetry and Copilot Memory Vault
- [ ] Scenario: Display primary vehicle showcase with mileage and health score
- [ ] Scenario: Enforce single-vehicle garage slot limit for free tier
- [ ] Scenario: Perform 30-second fluid and tire health check-in
- [ ] Scenario: Track copilot memory allocation within free 3-slot capacity
- [ ] Scenario: Delete individual copilot memory items on-demand

---

## Feature 03: Contextual Parts Feed and Visual Component Engine
- [ ] Scenario: Filter parts feed by guaranteed vehicle fitment and category chips
- [ ] Scenario: Highlight Yonke salvage yard parts with donor VIN and savings calculation
- [ ] Scenario: Generate on-demand 3D component visual blueprint with monthly quota
- [ ] Scenario: Gate fleet-wide community insights behind explicit Cloud AI Opt-In

---

## Feature 04: Multi-Agent Paywall and Freemium Governance
- [ ] Scenario: Present autonomous multi-agent network status and nodes
- [ ] Scenario: Display Pro superpowers matrix
- [ ] Scenario: Toggle between annual and monthly subscription pricing
- [ ] Scenario: Trigger paywall when exceeding free limits

---

## Feature 06: KMP CLI Template Packaging
- [ ] Scenario: Scaffold project without license requirements
- [ ] Scenario: Bundle manifest and template metadata
- [ ] Scenario: Bundle Generative UI and SQLDelight starter templates
