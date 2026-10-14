package com.jesusdmedinac.zora.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescription
import com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescriptionEntity
import com.jesusdmedinac.zora.domain.repository.MechanicalPrescriptionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SqlDelightMechanicalPrescriptionRepository(
    private val database: MechanicalPrescriptionVaultDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : MechanicalPrescriptionRepository {

    private val json = Json { ignoreUnknownKeys = true }
    private val queries = database.mechanicalPrescriptionVaultQueries

    override suspend fun insertLocalTriage(entity: MechanicalPrescriptionEntity): Result<Unit> = withContext(dispatcher) {
        runCatching {
            queries.insertLocalTriage(
                symptom_id = entity.symptomId,
                vehicle_id = entity.vehicleId,
                vin_technical_prefix = entity.vinTechnicalPrefix,
                active_scope_corridor = entity.activeScopeCorridor,
                preferred_condition = entity.preferredCondition,
                raw_symptom_description = entity.rawSymptomDescription,
                dtc_codes_json = entity.dtcCodesJson,
                local_preliminary_hypothesis = entity.localPreliminaryHypothesis,
                created_at_ms = entity.createdAtMs,
                updated_at_ms = entity.updatedAtMs
            )
        }.map { }
    }

    override suspend fun markOptInPending(
        symptomId: String,
        optInTimestampMs: Long,
        updatedAtMs: Long
    ): Result<Unit> = withContext(dispatcher) {
        runCatching {
            queries.markOptInPendingSync(
                opt_in_granted_at_ms = optInTimestampMs,
                updated_at_ms = updatedAtMs,
                symptom_id = symptomId
            )
        }.map { }
    }

    override suspend fun updateWithCloudPrescription(
        symptomId: String,
        prescription: MechanicalPrescription,
        updatedAtMs: Long
    ): Result<Unit> = withContext(dispatcher) {
        runCatching {
            val serialized = json.encodeToString(prescription)
            queries.updateWithCloudPrescription(
                oem_part_number = prescription.oemPart.oemNumber,
                excluded_impact_zones = prescription.excludedDonorImpactZones,
                serialized_prescription_json = serialized,
                generative_ui_payload_json = prescription.generativeUiSchemaJson,
                updated_at_ms = updatedAtMs,
                symptom_id = symptomId
            )
        }.map { }
    }

    override suspend fun selectById(symptomId: String): MechanicalPrescriptionEntity? = withContext(dispatcher) {
        queries.selectById(symptomId).executeAsOneOrNull()?.toEntity()
    }

    override suspend fun selectPendingCloudSync(): List<MechanicalPrescriptionEntity> = withContext(dispatcher) {
        queries.selectPendingCloudSync().executeAsList().map { it.toEntity() }
    }

    override fun observeByVehicle(vehicleId: String): Flow<List<MechanicalPrescriptionEntity>> {
        return queries.observeByVehicle(vehicleId)
            .asFlow()
            .mapToList(dispatcher)
            .map { list -> list.map { it.toEntity() } }
    }

    override suspend fun revokeCloudConsent(symptomId: String, updatedAtMs: Long): Result<Unit> = withContext(dispatcher) {
        runCatching {
            queries.revokeCloudConsent(
                updated_at_ms = updatedAtMs,
                symptom_id = symptomId
            )
        }.map { }
    }

    override suspend fun purgeById(symptomId: String): Result<Unit> = withContext(dispatcher) {
        runCatching {
            queries.purgeById(symptomId)
        }.map { }
    }

    private fun Mechanical_prescription_vault.toEntity(): MechanicalPrescriptionEntity {
        return MechanicalPrescriptionEntity(
            symptomId = symptom_id,
            vehicleId = vehicle_id,
            vinTechnicalPrefix = vin_technical_prefix,
            activeScopeCorridor = active_scope_corridor,
            preferredCondition = preferred_condition,
            rawSymptomDescription = raw_symptom_description,
            dtcCodesJson = dtc_codes_json,
            localPreliminaryHypothesis = local_preliminary_hypothesis,
            syncState = sync_state,
            optInGrantedAtMs = opt_in_granted_at_ms,
            oemPartNumber = oem_part_number,
            excludedImpactZones = excluded_impact_zones ?: emptyList(),
            serializedPrescriptionJson = serialized_prescription_json,
            generativeUiPayloadJson = generative_ui_payload_json,
            createdAtMs = created_at_ms,
            updatedAtMs = updated_at_ms
        )
    }
}
