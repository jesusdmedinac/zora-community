package com.jesusdmedinac.zora.domain.model.prescription

import kotlinx.serialization.Serializable

@Serializable
enum class PrescriptionSyncState {
    LOCAL_TRIAGE_ONLY,
    OPT_IN_PENDING_NETWORK,
    SYNCED_CLOUD_VERIFIED,
    REVOKED_BY_USER
}
