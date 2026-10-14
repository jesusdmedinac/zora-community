package com.jesusdmedinac.zora.domain.model.prescription

import kotlinx.serialization.Serializable

@Serializable
data class MechanicalPrescriptionEntity(
    val symptomId: String,
    val vehicleId: String,
    val vinTechnicalPrefix: String,
    val activeScopeCorridor: String = "Tijuana/Otay",
    val preferredCondition: String = "OEM_USED",
    val rawSymptomDescription: String,
    val dtcCodesJson: String = "[]",
    val localPreliminaryHypothesis: String,
    val syncState: PrescriptionSyncState = PrescriptionSyncState.LOCAL_TRIAGE_ONLY,
    val optInGrantedAtMs: Long? = null,
    val oemPartNumber: String? = null,
    val excludedImpactZones: List<VehicleImpactQuadrant> = emptyList(),
    val serializedPrescriptionJson: String? = null,
    val generativeUiPayloadJson: String? = null,
    val createdAtMs: Long,
    val updatedAtMs: Long
)
