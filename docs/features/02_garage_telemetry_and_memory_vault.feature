Feature: Garage Telemetry and Copilot Memory Vault
  As a vehicle owner using Zora Community
  I want a local-first garage dashboard with telemetry check-ins and an inspectable memory vault
  So that I can monitor my vehicle health and control what the AI remembers with zero privacy leaks

  Scenario: Display primary vehicle showcase with mileage and health score
    Given the driver has an active vehicle "2018 Honda Civic EX" registered in local storage
    When the driver navigates to the Garage screen
    Then the active vehicle card displays "Primary Vehicle" badge
    And the vehicle details show engine "2.0L 4-Cylinder · FWD Hatchback" and masked VIN "VIN: 2HGFC2F59JH02****"
    And the odometer displays "82,450 km" and overall health displays "94% Normal"

  Scenario: Enforce single-vehicle garage slot limit for free tier
    Given the driver has 1 vehicle registered under Zora Free
    When the driver views the garage slots
    Then the slot counter shows "1 of 1 Free Slot Used"
    And the second slot displays "+ Add Another Vehicle" with a locked "PRO" badge
    And tapping the locked slot prompts the driver to upgrade to Zora Pro

  Scenario: Perform 30-second fluid and tire health check-in
    Given the driver is on the Quick Health Check-in section
    When the driver updates the levels for Engine Oil, Coolant, Tire Pressure, and Battery
    Then the telemetry status shows "0W-20 Synthetic Normal Level", "Type 2 Blue Level OK", "32 PSI All Calibrated", and "12.6 Volts Healthy State"
    And the last checked timestamp updates to the current timestamp in local SQLite storage

  Scenario: Track copilot memory allocation within free 3-slot capacity
    Given the AI copilot has recorded 3 conversational memories
    When the driver inspects the Copilot Memory Vault
    Then the storage meter displays "3 of 3 Free Slots (100%)"
    And the local-first indicator confirms memory is stored strictly on-device
    And the list renders memory items:
      | category              | detail                                         | source                  |
      | Vehicle Configuration | 2018 Honda Civic 2.0L FWD (K20C2)              | Learned from Setup      |
      | Preferred Replacement | Ceramic low-dust brake pads & OEM filters      | Saved from Chat         |
      | Reported Symptom Log  | Slight front brake squeak at low parking speed | Logged via Voice Note   |

  Scenario: Delete individual copilot memory items on-demand
    Given the driver views the inspectable memory cards in the Copilot Memory Vault
    When the driver clicks the delete icon on "Reported Symptom Log"
    Then the item is immediately removed from local SQLite storage
    And the memory meter updates to "2 of 3 Free Slots (67%)"
