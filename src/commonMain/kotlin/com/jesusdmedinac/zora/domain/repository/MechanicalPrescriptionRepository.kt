package com.jesusdmedinac.zora.domain.repository

import com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescription
import com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescriptionEntity
import kotlinx.coroutines.flow.Flow

interface MechanicalPrescriptionRepository {
    suspend fun insertLocalTriage(entity: MechanicalPrescriptionEntity): Result<Unit>
    suspend fun markOptInPending(symptomId: String, optInTimestampMs: Long, updatedAtMs: Long): Result<Unit>
    suspend fun updateWithCloudPrescription(
        symptomId: String,
        prescription: MechanicalPrescription,
        updatedAtMs: Long
    ): Result<Unit>
    suspend fun selectById(symptomId: String): MechanicalPrescriptionEntity?
    suspend fun selectPendingCloudSync(): List<MechanicalPrescriptionEntity>
    fun observeByVehicle(vehicleId: String): Flow<List<MechanicalPrescriptionEntity>>
    suspend fun revokeCloudConsent(symptomId: String, updatedAtMs: Long): Result<Unit>
    suspend fun purgeById(symptomId: String): Result<Unit>
}
