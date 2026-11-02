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

    companion object {
        fun defaultSeededEntities(): List<MechanicalPrescriptionEntity> {
            val now = 1729000000000L
            val defaults = listOf(
                "¿Cuánto aire necesitan mis llantas?" to "Para tu Honda Civic 2018, la presión recomendada es de 32 PSI en frío para las cuatro llantas.",
                "¿Qué es la bujía?" to "La bujía es el componente encargado de suministrar la chispa de encendido para inflamar la mezcla de aire y combustible en la cámara de combustión.",
                "¿Por qué rechinan mis frenos?" to "El rechinido puede deberse a cristalización en las balatas, desgaste de los indicadores acústicos o acumulación de polvo en el caliper.",
                "¿Qué significa el Check Engine?" to "La luz Check Engine indica que la computadora de abordo (ECU) ha detectado un código de falla DTC en las emisiones, transmisión o tren motriz.",
            )
            return defaults.mapIndexed { index, (question, hypothesis) ->
                val time = now - (index * 60_000L)
                MechanicalPrescriptionEntity(
                    symptomId = "seed_$index",
                    vehicleId = "honda_civic_2018",
                    vinTechnicalPrefix = "2HGFC2F5",
                    rawSymptomDescription = question,
                    localPreliminaryHypothesis = hypothesis,
                    createdAtMs = time,
                    updatedAtMs = time,
                )
            }
        }
    }
}
