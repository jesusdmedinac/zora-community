package com.jesusdmedinac.zora.domain.model.prescription

import kotlinx.serialization.Serializable

/**
 * Maps 1:1 with Copart and IAAI auction primary/secondary damage codes
 * to cross salvage vehicles acquired in US auctions with local demand in Tijuana/Otay.
 */
@Serializable
enum class VehicleImpactQuadrant(val auctionDamageCode: String) {
    FRONT_LEFT("FL"),
    FRONT_CENTER("FR_END"),
    FRONT_RIGHT("FR"),
    SIDE_LEFT("L_SIDE"),
    SIDE_RIGHT("R_SIDE"),
    REAR_LEFT("RL"),
    REAR_CENTER("RR_END"),
    REAR_RIGHT("RR"),
    ROLLOVER("RO"),
    FLOOD_OR_FIRE("WATER_BURN");

    companion object {
        fun fromAuctionCode(code: String): VehicleImpactQuadrant? =
            entries.firstOrNull { it.auctionDamageCode.equals(code, ignoreCase = true) }
    }
}
