Feature: Contextual Parts Feed and Visual Component Engine
  As a vehicle owner searching for automotive components
  I want a contextual parts feed tailored to my vehicle with salvage yard options and visual engine blueprints
  So that I can verify part fitment, save money with used parts, and locate components in my engine bay

  Scenario: Filter parts feed by guaranteed vehicle fitment and category chips
    Given the active vehicle is "2018 Honda Civic EX 2.0L K20C2"
    When the driver navigates to the Parts screen
    Then the vehicle selector pill displays "Fitment Active · Precision Match"
    And filter chips are available for "All", "Brakes", "Fluids & Filters", "Suspension", and "Junkyard Network"
    And selecting "Brakes" displays "Akebono Ultra-Premium Ceramic Front Brake Pads" with badge "Direct Fit Guarantee" and OEM Interchange "#45022-TBA-A01"

  Scenario: Highlight Yonke salvage yard parts with donor VIN and savings calculation
    Given the driver selects the "Junkyard Network" category chip
    When the parts feed updates
    Then salvage items display the badge "Verified Salvage · Yonke Network"
    And the item displays the donor vehicle VIN and mileage "Donor VIN: 1HGFC2...7391 (41k mi)"
    And the item highlights price "$85.00" with savings calculation "Save 65% vs $240 New"
    And distance and grade are shown as "9.2 mi away · Grade A Condition"

  Scenario: Generate on-demand 3D component visual blueprint with monthly quota
    Given the driver has used 1 of 3 free visual generations this month
    When the driver requests a visual blueprint for "DENSO ALTERNATOR"
    Then the engine renders a 3D cutaway wireframe of the engine bay with neon locator pin
    And the component tag indicates "DENSO ALTERNATOR · Engine Bay (Front-Right Offset)"
    And the quota counter updates to "2 of 3 Used"
    And the disclaimer states "Note: AI-generated visual reference for illustration and component location only."

  Scenario: Gate fleet-wide community insights behind explicit Cloud AI Opt-In
    Given the driver is currently operating in "Local-Only" session mode
    When the driver views the Community Insights section
    Then the section displays "Private Local Session Active" with locked failure trend stats
    And the question of the week preview displays blurred crowd statistics
    And an explanation notes that telemetry exchange requires explicit opt-in
    And clicking "Review Cloud AI Opt-In Settings" opens the privacy preferences modal
