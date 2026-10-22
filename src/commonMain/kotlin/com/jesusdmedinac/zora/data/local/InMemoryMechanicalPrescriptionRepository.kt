package com.jesusdmedinac.zora.data.local

import com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescription
import com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescriptionEntity
import com.jesusdmedinac.zora.domain.model.prescription.PrescriptionSyncState
import com.jesusdmedinac.zora.domain.repository.MechanicalPrescriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class InMemoryMechanicalPrescriptionRepository(
    initialEntities: List<MechanicalPrescriptionEntity> = emptyList(),
) : MechanicalPrescriptionRepository {
    private val store = MutableStateFlow<Map<String, MechanicalPrescriptionEntity>>(
        initialEntities.associateBy { it.symptomId }
    )
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun insertLocalTriage(entity: MechanicalPrescriptionEntity): Result<Unit> {
        val current = store.value.toMutableMap()
        current[entity.symptomId] = entity
        store.value = current
        return Result.success(Unit)
    }

    override suspend fun markOptInPending(
        symptomId: String,
        optInTimestampMs: Long,
        updatedAtMs: Long
    ): Result<Unit> {
        val current = store.value.toMutableMap()
        val existing = current[symptomId] ?: return Result.failure(NoSuchElementException(symptomId))
        current[symptomId] = existing.copy(
            syncState = PrescriptionSyncState.OPT_IN_PENDING_NETWORK,
            optInGrantedAtMs = optInTimestampMs,
            updatedAtMs = updatedAtMs
        )
        store.value = current
        return Result.success(Unit)
    }

    override suspend fun updateWithCloudPrescription(
        symptomId: String,
        prescription: MechanicalPrescription,
        updatedAtMs: Long
    ): Result<Unit> {
        val current = store.value.toMutableMap()
        val existing = current[symptomId] ?: return Result.failure(NoSuchElementException(symptomId))
        val serialized = json.encodeToString(prescription)
        current[symptomId] = existing.copy(
            syncState = PrescriptionSyncState.SYNCED_CLOUD_VERIFIED,
            oemPartNumber = prescription.oemPart.oemNumber,
            excludedImpactZones = prescription.excludedDonorImpactZones,
            serializedPrescriptionJson = serialized,
            generativeUiPayloadJson = prescription.generativeUiSchemaJson,
            updatedAtMs = updatedAtMs
        )
        store.value = current
        return Result.success(Unit)
    }

    override suspend fun selectById(symptomId: String): MechanicalPrescriptionEntity? {
        return store.value[symptomId]
    }

    override suspend fun selectPendingCloudSync(): List<MechanicalPrescriptionEntity> {
        return store.value.values
            .filter { it.syncState == PrescriptionSyncState.OPT_IN_PENDING_NETWORK }
            .sortedBy { it.optInGrantedAtMs ?: Long.MAX_VALUE }
    }

    override fun observeByVehicle(vehicleId: String): Flow<List<MechanicalPrescriptionEntity>> {
        return store.map { map ->
            map.values.filter { it.vehicleId == vehicleId }.sortedByDescending { it.createdAtMs }
        }
    }

    override suspend fun revokeCloudConsent(symptomId: String, updatedAtMs: Long): Result<Unit> {
        val current = store.value.toMutableMap()
        val existing = current[symptomId] ?: return Result.failure(NoSuchElementException(symptomId))
        current[symptomId] = existing.copy(
            syncState = PrescriptionSyncState.REVOKED_BY_USER,
            updatedAtMs = updatedAtMs
        )
        store.value = current
        return Result.success(Unit)
    }

    override suspend fun purgeById(symptomId: String): Result<Unit> {
        val current = store.value.toMutableMap()
        current.remove(symptomId)
        store.value = current
        return Result.success(Unit)
    }
}
