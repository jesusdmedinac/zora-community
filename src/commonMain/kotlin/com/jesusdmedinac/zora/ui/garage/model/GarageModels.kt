package com.jesusdmedinac.zora.ui.garage.model

data class CopilotMemoryItem(
    val id: String,
    val category: String,
    val detail: String,
    val source: String,
)

data class TelemetryCheckInState(
    val engineOil: String = "0W-20 Synthetic Normal Level",
    val coolant: String = "Type 2 Blue Level OK",
    val tirePressure: String = "32 PSI All Calibrated",
    val battery: String = "12.6 Volts Healthy State",
    val lastCheckedTimestamp: Long = 1729000000000L,
)

fun defaultCopilotMemories(): List<CopilotMemoryItem> = listOf(
    CopilotMemoryItem(
        id = "mem_01",
        category = "Vehicle Configuration",
        detail = "2018 Honda Civic 2.0L FWD (K20C2)",
        source = "Learned from Setup",
    ),
    CopilotMemoryItem(
        id = "mem_02",
        category = "Preferred Replacement",
        detail = "Ceramic low-dust brake pads & OEM filters",
        source = "Saved from Chat",
    ),
    CopilotMemoryItem(
        id = "mem_03",
        category = "Reported Symptom Log",
        detail = "Slight front brake squeak at low parking speed",
        source = "Logged via Voice Note",
    ),
)
