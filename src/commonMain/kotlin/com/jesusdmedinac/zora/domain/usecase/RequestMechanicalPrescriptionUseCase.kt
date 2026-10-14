package com.jesusdmedinac.zora.domain.usecase

import com.jesusdmedinac.zora.domain.gateway.CloudPrescriptionGateway
import com.jesusdmedinac.zora.domain.model.prescription.*
import com.jesusdmedinac.zora.domain.model.vehicle.VinPrivacySanitizer
import com.jesusdmedinac.zora.domain.repository.MechanicalPrescriptionRepository

sealed class UnlockResult {
    data class Success(val prescription: MechanicalPrescription) : UnlockResult()
    data class QueuedOffline(val symptomId: String) : UnlockResult()
    data class Error(val message: String) : UnlockResult()
}

class RequestMechanicalPrescriptionUseCase(
    private val repository: MechanicalPrescriptionRepository,
    private val cloudGateway: CloudPrescriptionGateway
) {

    /**
     * Step 1: On-device triage with $0 server cost.
     * Evaluates local preliminary hypothesis and persists to SQLDelight with status LOCAL_TRIAGE_ONLY.
     * Dispatches ZERO network requests to CloudPrescriptionGateway.
     */
    suspend fun recordLocalTriage(
        vehicleId: String,
        fullVin: String,
        symptomDescription: String,
        dtcCodesJson: String = "[]",
        corridor: String = "Tijuana/Otay",
        condition: String = "OEM_USED",
        customSymptomId: String? = null,
        timestampMs: Long = 1728900000000L
    ): Result<MechanicalPrescriptionEntity> {
        val sanitizedPrefixResult = VinPrivacySanitizer.sanitizeToTechnicalPrefix(fullVin)
        val technicalPrefix = sanitizedPrefixResult.getOrElse {
            return Result.failure(it)
        }

        val hypothesis = generateLocalHypothesis(symptomDescription)
        val symptomId = customSymptomId ?: "sym_${timestampMs % 10000}"

        val entity = MechanicalPrescriptionEntity(
            symptomId = symptomId,
            vehicleId = vehicleId,
            vinTechnicalPrefix = technicalPrefix,
            activeScopeCorridor = corridor,
            preferredCondition = condition,
            rawSymptomDescription = symptomDescription.trim(),
            dtcCodesJson = dtcCodesJson,
            localPreliminaryHypothesis = hypothesis,
            syncState = PrescriptionSyncState.LOCAL_TRIAGE_ONLY,
            createdAtMs = timestampMs,
            updatedAtMs = timestampMs
        )

        val saveResult = repository.insertLocalTriage(entity)
        return saveResult.map { entity }
    }

    /**
     * Step 2: Ethical Value Gate unlocking.
     * Sanitizes full VIN strictly to 11 technical characters (stripping 6 private serial digits).
     * Applies Outbox pattern: immediately marks record OPT_IN_PENDING_NETWORK.
     * Attempts cloud resolution. If successful, transitions to SYNCED_CLOUD_VERIFIED.
     * If network is down, retains OPT_IN_PENDING_NETWORK and returns QueuedOffline.
     */
    suspend fun unlockMechanicalPrescription(
        symptomId: String,
        fullVin: String,
        timestampMs: Long = 1728900000000L
    ): UnlockResult {
        val existing = repository.selectById(symptomId)
            ?: return UnlockResult.Error("Symptom record $symptomId not found in local vault")

        val prefixResult = VinPrivacySanitizer.sanitizeToTechnicalPrefix(fullVin)
        val sanitizedPrefix = prefixResult.getOrElse {
            return UnlockResult.Error("VIN sanitization failed: ${it.message}")
        }

        // Outbox pattern step 1: mark pending network
        repository.markOptInPending(
            symptomId = symptomId,
            optInTimestampMs = timestampMs,
            updatedAtMs = timestampMs
        )

        val payload = DiagnosticOptInPayload(
            symptomId = symptomId,
            technicalVinPrefix = sanitizedPrefix,
            corridor = existing.activeScopeCorridor,
            conditionPreference = existing.preferredCondition,
            reportedSymptom = existing.rawSymptomDescription,
            preliminaryHypothesis = existing.localPreliminaryHypothesis,
            timestampMs = timestampMs
        )

        val cloudResponse = cloudGateway.requestPrescription(payload)

        return cloudResponse.fold(
            onSuccess = { prescription ->
                repository.updateWithCloudPrescription(
                    symptomId = symptomId,
                    prescription = prescription,
                    updatedAtMs = timestampMs
                )
                UnlockResult.Success(prescription)
            },
            onFailure = {
                // Offline fallback: keep in OPT_IN_PENDING_NETWORK outbox queue
                UnlockResult.QueuedOffline(symptomId)
            }
        )
    }

    /**
     * Step 3: Outbox queue flusher.
     * Executes when network monitor detects restored connection.
     * Fetches all records in OPT_IN_PENDING_NETWORK, dispatches them and marks SYNCED_CLOUD_VERIFIED.
     */
    suspend fun flushPendingOutboxQueue(timestampMs: Long = 1728900000000L): Int {
        val pendingList = repository.selectPendingCloudSync()
        var syncedCount = 0

        for (item in pendingList) {
            val payload = DiagnosticOptInPayload(
                symptomId = item.symptomId,
                technicalVinPrefix = item.vinTechnicalPrefix,
                corridor = item.activeScopeCorridor,
                conditionPreference = item.preferredCondition,
                reportedSymptom = item.rawSymptomDescription,
                preliminaryHypothesis = item.localPreliminaryHypothesis,
                timestampMs = timestampMs
            )

            val cloudResult = cloudGateway.requestPrescription(payload)
            cloudResult.onSuccess { prescription ->
                repository.updateWithCloudPrescription(
                    symptomId = item.symptomId,
                    prescription = prescription,
                    updatedAtMs = timestampMs
                )
                syncedCount++
            }
        }
        return syncedCount
    }

    private fun generateLocalHypothesis(symptom: String): String {
        val lower = symptom.lowercase()
        return when {
            lower.contains("vibr") || lower.contains("aceler") || lower.contains("truen") || lower.contains("turn") ->
                "Probable CV Axle failure or Right Transmission Mount wear"
            lower.contains("brak") || lower.contains("fren") || lower.contains("chirr") ->
                "Front brake ceramic pad wear or warped rotor surface"
            lower.contains("alter") || lower.contains("bater") || lower.contains("charg") ->
                "Alternator diode failure or battery voltage drop"
            else ->
                "General powertrain wear requiring physical inspection"
        }
    }
}
