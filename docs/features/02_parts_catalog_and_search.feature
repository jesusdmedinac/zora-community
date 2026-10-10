Feature: Parts Catalog and Search
  As a vehicle owner searching for automotive components
  I want to search parts by category, name, or OEM number
  So that I can locate replacement parts with compatibility verification

  Scenario: Browse auto parts categories
    Given an active vehicle is selected
    When the user navigates to the parts catalog
    Then the categories grid displays "Engine", "Brakes & Traction", "Suspension & Steering", "Electrical", and "Body & Lamps"
    And each category displays an iconography indicator and item count

  Scenario: Search parts by keyword with live debounce filtering
    Given the parts catalog contains items for "2021 Honda Civic"
    When the user searches for "alternator"
    Then the search results show "OEM Alternator Assembly (135A)"
    And the part card displays OEM part number "31100-5AA-A01"
    And the card indicates "Guaranteed Direct Fit" for the active vehicle

  Scenario: View detailed part specification and OEM interchange numbers
    Given the user selects part "31100-5AA-A01"
    When the part detail modal is presented
    Then the specifications show technical parameters including amperage, voltage, and pulley grooves
    And a list of interchangeable OEM numbers is displayed for cross-referencing
