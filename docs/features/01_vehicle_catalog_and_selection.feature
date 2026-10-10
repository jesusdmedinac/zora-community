Feature: Vehicle Catalog and Selection
  As a vehicle owner or mechanic using Zora Community
  I want to browse and select my vehicle by year, make, model, and engine
  So that I can see parts that are guaranteed to fit my vehicle

  Scenario: Browse supported automotive makes
    Given the vehicle catalog is loaded with certified vehicles
    When the user views the makes selector
    Then the user sees popular manufacturers including "Honda", "Toyota", "Ford", "Chevrolet", and "Nissan"
    And each make displays its vehicle count and manufacturer logo

  Scenario: Filter vehicle models by selected year and make
    Given the user has selected make "Honda" and year 2021
    When the user requests available models
    Then the list displays "Civic", "Accord", "CR-V", and "HR-V"
    And selecting "Civic" reveals engine trims including "1.5L Turbo" and "2.0L Naturally Aspirated"

  Scenario: Save selected active vehicle in memory and UI state
    Given the user selects "2021 Honda Civic EX 1.5L Turbo"
    When the vehicle selection is confirmed
    Then the active vehicle header reflects "2021 Honda Civic EX"
    And subsequent part searches automatically filter by this vehicle's compatibility profile
