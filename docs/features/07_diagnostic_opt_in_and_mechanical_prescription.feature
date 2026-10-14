Feature: Local-First Triage, Ethical Value Gate, and Mechanical Prescription with Salvage Matching
  As a vehicle driver or parts sourcer in the Tijuana/Otay - San Diego border corridor
  I want to diagnose vehicle symptoms locally across any platform (Android, iOS, Desktop, or Web Wasm) and choose when to activate Cloud AI
  So that I obtain a Mechanical Prescription (OEM part number, compatible donors, and clean salvage impact quadrants) while Zora maps local yard demand

  Background:
    Given the SQLDelight 2.x multiplatform database driver is initialized on target ("android", "ios", "jvm", or "wasmJs")
    And the driver has active vehicle in the Virtual Garage:
      | vehicleId | make  | model | year | engine | fullVin           | technicalVinPrefix | odometerKm |
      | veh_01    | Honda | Civic | 2018 | 2.0L   | 2HGFC2F59JH123456 | 2HGFC2F59JH        | 114500     |
    And the active context header displays filter chips:
      | vehicleChip | corridorChip | conditionChip |
      | Civic 2018  | Tijuana/Otay | OEM Used      |

  @on_device_ai @zero_server_cost @sqldelight_local
  Scenario: Record vehicle symptom via insertLocalTriage without network traffic
    When the driver submits symptom from the bottom input capsule:
      """
      Heavy vibration on acceleration between 40 and 60 km/h with clicking noise during right turns
      """
    Then SQLDelight executes "insertLocalTriage" storing symptom in "mechanical_prescription_vault" with status "LOCAL_TRIAGE_ONLY"
    And the Ktor network client confirms 0 HTTP requests were dispatched to "zora-system"
    And the UI renders local preliminary hypothesis "Probable CV Axle failure or Right Transmission Mount wear"
    And the Ethical Value Gate is presented inviting driver to unlock Mechanical Prescription for free via anonymous Opt-In

  @cloud_opt_in @json_to_compose @copart_iaai_fitment
  Scenario: Unlock Mechanical Prescription with donor matrix and collision quadrant exclusion
    Given symptom record "sym_991" exists in SQLDelight with status "LOCAL_TRIAGE_ONLY"
    When the driver authorizes the Ethical Value Gate to request Cloud AI prescription
    Then the client transmits only the technical VIN prefix "2HGFC2F59JH" omitting private serial digits
    And upon receiving cloud response, SQLDelight executes "updateWithCloudPrescription" setting status to "SYNCED_CLOUD_VERIFIED"
    And the "json-to-compose" engine renders the Mechanical Prescription card displaying:
      | oemPartNumber | partName                       | otaySalvagePriceMxn | dealerPriceMxn | communitySuccessRate |
      | 44305-TBA-A01 | Front Right CV Axle Assembly   | $1,200 - $1,800     | $8,900         | 86%                  |
    And displays the "Compatible Donor Vehicles" table for yard and auction sourcing:
      | make  | model | compatibleYears | engineSpec | interchangeNote                    |
      | Honda | Civic | 2016 - 2020     | 2.0L K20C2 | Direct fitment on CVT transmission |
    And displays "Excluded Collision Quadrants" aligned with Copart and IAAI auction damage codes:
      | excludedQuadrant | auctionDamageCode | mechanicalRisk                                                  |
      | FRONT_RIGHT      | FR                | Bent axle shaft or fractured tripod joint from direct RH impact |
      | FLOOD_OR_FIRE    | WATER_BURN        | Thermal degradation or bearing water contamination              |

  @offline_first @sqldelight_outbox
  Scenario: Offline outbox queuing in garage or Web Wasm with background sync on reconnection
    Given the client device or Web Wasm browser session is offline without internet connection
    And symptom record "sym_992" exists in status "LOCAL_TRIAGE_ONLY"
    When the driver approves the Ethical Value Gate
    Then SQLDelight executes "markOptInPendingSync" updating status to "OPT_IN_PENDING_NETWORK"
    When network connectivity is restored and sync monitor executes "selectPendingCloudSync"
    Then the SyncWorker dispatches pending payload, retrieves Mechanical Prescription, and transitions record to "SYNCED_CLOUD_VERIFIED"

  @b2b_yonke_leverage @salvage_lot_matching
  Scenario: Aggregate verified demand in Tijuana/Otay for commercial yard activation
    Given 15 drivers in corridor "Tijuana/Otay" have unlocked Mechanical Prescriptions for OEM "44305-TBA-A01"
    When Ktor backend aggregates "SYNCED_CLOUD_VERIFIED" events in Layer B ("zora-system")
    Then the Zora B2B radar registers verified retained demand of "$22,500 MXN" for "Honda Civic 2016-2020" donors with no "FRONT_RIGHT" damage
    And triggers demand alerts for Otay yards holding matching inventory or bidding on "REAR_CENTER" Copart lots in San Diego
