package com.jesusdmedinac.zora.domain.gateway

import com.jesusdmedinac.zora.domain.model.prescription.DiagnosticOptInPayload
import com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescription
import com.jesusdmedinac.zora.domain.model.prescription.RetainedDemandSummary

interface CloudPrescriptionGateway {
    suspend fun requestPrescription(payload: DiagnosticOptInPayload): Result<MechanicalPrescription>
    fun getRequestCount(): Int
    fun getLastSubmittedPayload(): DiagnosticOptInPayload?
    fun getRetainedDemandSummary(corridor: String, oemPartNumber: String): RetainedDemandSummary
    fun reset()
}
