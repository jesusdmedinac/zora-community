package com.jesusdmedinac.zora.data.local.adapter

import app.cash.sqldelight.ColumnAdapter
import com.jesusdmedinac.zora.domain.model.prescription.VehicleImpactQuadrant

object VehicleImpactQuadrantListAdapter : ColumnAdapter<List<VehicleImpactQuadrant>, String> {
    override fun decode(databaseValue: String): List<VehicleImpactQuadrant> {
        if (databaseValue.isBlank()) return emptyList()
        return databaseValue.split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .mapNotNull { name ->
                try {
                    VehicleImpactQuadrant.valueOf(name)
                } catch (_: Exception) {
                    null
                }
            }
    }

    override fun encode(value: List<VehicleImpactQuadrant>): String {
        return value.joinToString(",") { it.name }
    }
}
