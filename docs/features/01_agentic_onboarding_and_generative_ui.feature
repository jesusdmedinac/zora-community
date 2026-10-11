Feature: Agentic Onboarding and Generative UI
  As a vehicle driver using Zora Community
  I want an AI-first conversational onboarding experience with Generative UI
  So that I can easily set up my car profile and discover care priorities without technical jargon

  Scenario: Display clean startup splash screen
    Given the driver launches the Zora mobile application
    When the startup sequence begins
    Then the minimalist splash screen displays the centered Zora origami emblem with ambient glow
    And the headline displays "Autonomous Vehicle Copilot & Parts Intelligence"
    And zero interactive buttons or navigation bars are shown during initialization
    And the app transitions to the empty chat welcome state upon completion

  Scenario: Welcome driver in empty chat state with quick starter chips
    Given the driver arrives at the chat screen before any conversation has started
    When the empty chat view is presented
    Then a centered Zora emblem is displayed with the greeting "I'm ready whenever you are"
    And quick starter prompt chips are displayed: "Set up my car", "Find a replacement part", and "Diagnose a sound or light"
    And tapping "Set up my car" initiates the vehicle setup conversation

  Scenario: Render interactive vehicle trim selector via Generative UI
    Given the driver states "I drive a 2018 Honda Civic"
    When the AI copilot processes the vehicle statement
    Then a generative trim selection card is rendered inside the chat feed
    And the card presents engine options: "2.0L 4-Cylinder (LX / EX)", "1.5L Turbo (EX-T / Touring)", and "2.0L Turbo (Type R)"
    And selecting "2.0L 4-Cylinder (LX / EX)" highlights the option with an active checkmark and violet neon border

  Scenario: Present care priorities with semantic two-word badges
    Given the driver has confirmed the vehicle trim
    When the copilot evaluates maintenance and parts needs
    Then the chat feed renders the Recommended Care Priorities cards
    And the Preventive Maintenance card displays the emerald status badge "HIGH PRIORITY"
    And the Junkyard & Used Parts card displays the violet status badge "MAX SAVINGS"
    And neither card displays artificial percentage match numbers

  Scenario: Offer on-demand visual reference generation with disclaimer
    Given the vehicle setup is established in the chat
    When the copilot proposes creating a visual reference for Garage Slot #01
    Then an on-demand generation proposal card is displayed showing quota "1 of 3 free this month"
    And a visible disclaimer states "Note: AI-generated visual reference for illustration only"
    And the user can explicitly trigger "Generate Reference Image" or choose "Skip for now"
