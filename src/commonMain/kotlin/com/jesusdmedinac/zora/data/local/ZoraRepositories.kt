package com.jesusdmedinac.zora.data.local

import com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescriptionEntity
import com.jesusdmedinac.zora.domain.repository.MechanicalPrescriptionRepository

/**
 * Global repository provider for Zora Kotlin Multiplatform components.
 */
object ZoraRepositories {
    fun createInMemoryRepository(): MechanicalPrescriptionRepository {
        val now = 1729000000000L
        val defaults = listOf(
            "¿Cuánto aire necesitan mis llantas?" to "Para tu Honda Civic 2018, la presión recomendada es de 32 PSI en frío para las cuatro llantas.",
            "¿Qué es la bujía?" to "La bujía es el componente encargado de suministrar la chispa de encendido para inflamar la mezcla de aire y combustible en la cámara de combustión.",
            "¿Por qué rechinan mis frenos?" to "El rechinido puede deberse a cristalización en las balatas, desgaste de los indicadores acústicos o acumulación de polvo en el caliper.",
            "¿Qué significa el Check Engine?" to "La luz Check Engine indica que la computadora de abordo (ECU) ha detectado un código de falla DTC en las emisiones, transmisión o tren motriz.",
        )
        val initialEntities = defaults.mapIndexed { index, (question, hypothesis) ->
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
        return InMemoryMechanicalPrescriptionRepository(initialEntities)
    }

    val mechanicalPrescriptionRepository: MechanicalPrescriptionRepository by lazy {
        createInMemoryRepository()
    }
}
