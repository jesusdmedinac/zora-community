Feature: Multi-Agent Paywall and Freemium Governance
  As a vehicle driver considering upgrading to Zora Pro
  I want a clear breakdown of the multi-agent network, pro superpowers, and flexible subscription plans
  So that I can unlock autonomous agents, unlimited garage slots, and camera diagnostics with transparent pricing

  Scenario: Present autonomous multi-agent network status and nodes
    Given the driver opens the Zora Pro paywall
    When the multi-agent network section is displayed
    Then the Junkyard & Salvage Agent is shown with status "ACTIVE NOW" and cluster "YardMesh v4.2"
    And the Live Fuel & Gas Station Agent is shown with status "COMING SOON · INCLUDED"
    And the Certified Mechanic Agent is shown with status "COMING SOON · INCLUDED"
    And the badge affirms "Local-First · Zero Leak"

  Scenario: Display Pro superpowers matrix
    Given the driver reviews the Pro Superpowers grid
    When the 2x2 matrix is rendered
    Then the driver sees "Camera Visual Diagnosis" for instant AI analysis of leaks and warning lights
    And the driver sees "Unlimited Multi-Car Garage" to register all household vehicles
    And the driver sees "Infinite Copilot Memory" to preserve full maintenance trails and preferences
    And the driver sees "Unlimited Visual Renders" for unrestricted component location blueprints

  Scenario: Toggle between annual and monthly subscription pricing
    Given the driver is reviewing membership plans on the paywall
    When the driver inspects the pricing options
    Then the Annual Membership is selected by default showing "$79.99 / year ($6.66/month)" with badge "SAVE 33% · BEST VALUE"
    And the Monthly Membership displays "$9.99 / month" with flexible cancellation
    When the driver selects the Monthly Membership option
    Then the monthly radio button is checked and the annual radio button is unselected

  Scenario: Trigger paywall when exceeding free limits
    Given a free-tier driver attempts to perform a pro-only action:
      | action                          | free_limit |
      | Add second vehicle to garage    | 1 car max  |
      | Request 4th visual image render | 3 per mo   |
      | Snap engine photo with camera   | Pro only   |
    When the action is triggered
    Then the application presents the Zora Pro paywall modal
    And the primary action button displays "Start 7-Day Free Trial" with subtitle "7 days free · Cancel anytime · Secured via App Store"
