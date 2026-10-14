package com.jesusdmedinac.zora.data.local.adapter

import app.cash.sqldelight.ColumnAdapter
import com.jesusdmedinac.zora.domain.model.prescription.PrescriptionSyncState

object PrescriptionSyncStateAdapter : ColumnAdapter<PrescriptionSyncState, String> {
    override fun decode(databaseValue: String): PrescriptionSyncState {
        return try {
            PrescriptionSyncState.valueOf(databaseValue)
        } catch (_: Exception) {
            PrescriptionSyncState.LOCAL_TRIAGE_ONLY
        }
    }

    override fun encode(value: PrescriptionSyncState): String {
        return value.name
    }
}
