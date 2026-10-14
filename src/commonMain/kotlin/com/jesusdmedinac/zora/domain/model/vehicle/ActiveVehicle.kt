package com.jesusdmedinac.zora.domain.model.vehicle

import kotlinx.serialization.Serializable

@Serializable
data class ActiveVehicle(
    val vehicleId: String,
    val make: String,
    val model: String,
    val year: Int,
    val engine: String,
    val fullVin: String,
    val technicalVinPrefix: String,
    val odometerKm: Long
)
