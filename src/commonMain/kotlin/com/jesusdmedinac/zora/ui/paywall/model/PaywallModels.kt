package com.jesusdmedinac.zora.ui.paywall.model

enum class SubscriptionBilling {
    ANNUAL,
    MONTHLY,
}

data class AgentNode(
    val name: String,
    val status: String,
    val cluster: String,
)

data class ProSuperpower(
    val title: String,
    val description: String,
)

data class FreemiumLimit(
    val action: String,
    val freeLimit: String,
)

fun defaultAgentNodes(): List<AgentNode> = listOf(
    AgentNode(
        name = "Junkyard & Salvage Agent",
        status = "ACTIVE NOW",
        cluster = "YardMesh v4.2",
    ),
    AgentNode(
        name = "Live Fuel & Gas Station Agent",
        status = "COMING SOON · INCLUDED",
        cluster = "FuelMesh",
    ),
    AgentNode(
        name = "Certified Mechanic Agent",
        status = "COMING SOON · INCLUDED",
        cluster = "MechanicNet",
    ),
)

fun defaultProSuperpowers(): List<ProSuperpower> = listOf(
    ProSuperpower(
        title = "Camera Visual Diagnosis",
        description = "instant AI analysis of leaks and warning lights",
    ),
    ProSuperpower(
        title = "Unlimited Multi-Car Garage",
        description = "register all household vehicles",
    ),
    ProSuperpower(
        title = "Infinite Copilot Memory",
        description = "preserve full maintenance trails and preferences",
    ),
    ProSuperpower(
        title = "Unlimited Visual Renders",
        description = "unrestricted component location blueprints",
    ),
)

fun defaultFreemiumLimits(): List<FreemiumLimit> = listOf(
    FreemiumLimit("Add second vehicle to garage", "1 car max"),
    FreemiumLimit("Request 4th visual image render", "3 per mo"),
    FreemiumLimit("Snap engine photo with camera", "Pro only"),
)
