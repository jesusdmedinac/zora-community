package com.jesusdmedinac.zora.di

import com.jesusdmedinac.zora.data.local.InMemoryMechanicalPrescriptionRepository
import com.jesusdmedinac.zora.data.local.MechanicalPrescriptionVaultDatabase
import com.jesusdmedinac.zora.data.local.SqlDelightMechanicalPrescriptionRepository
import com.jesusdmedinac.zora.data.local.driver.DatabaseDriverFactory
import com.jesusdmedinac.zora.data.local.driver.createMechanicalPrescriptionVaultDatabase
import com.jesusdmedinac.zora.domain.ai.KoogZoraCopilotAgent
import com.jesusdmedinac.zora.domain.ai.ZoraCopilotAgent
import com.jesusdmedinac.zora.domain.model.prescription.MechanicalPrescriptionEntity
import com.jesusdmedinac.zora.domain.repository.MechanicalPrescriptionRepository
import com.jesusdmedinac.zora.ui.home.CopilotHomeViewModel
import com.jesusdmedinac.zora.ui.home.ZoraShiftedDrawerViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val dataModule = module {
    single<MechanicalPrescriptionVaultDatabase> {
        val driverFactory: DatabaseDriverFactory = get()
        createMechanicalPrescriptionVaultDatabase(driverFactory.createDriver())
    }
    single<MechanicalPrescriptionRepository> {
        SqlDelightMechanicalPrescriptionRepository(database = get())
    }
}

val aiModule = module {
    single<ZoraCopilotAgent> {
        KoogZoraCopilotAgent(
            llmClient = getOrNull(),
        )
    }
}

val viewModelModule = module {
    viewModel {
        CopilotHomeViewModel(
            repository = getOrNull(),
            copilotAgent = getOrNull() ?: KoogZoraCopilotAgent(),
        )
    }
    viewModel {
        ZoraShiftedDrawerViewModel(repository = get())
    }
}

fun inMemoryDataModule(
    initialEntities: List<MechanicalPrescriptionEntity> = InMemoryMechanicalPrescriptionRepository.defaultSeededEntities()
): Module = module {
    single<MechanicalPrescriptionRepository> {
        InMemoryMechanicalPrescriptionRepository(initialEntities)
    }
}

val zoraAppModules: List<Module> = listOf(dataModule, aiModule, viewModelModule)

fun initKoin(
    additionalModules: List<Module> = emptyList(),
    appDeclaration: KoinAppDeclaration? = null,
): KoinApplication? {
    if (GlobalContext.getOrNull() != null) return null
    return startKoin {
        appDeclaration?.invoke(this)
        modules(zoraAppModules + additionalModules)
    }
}

fun initInMemoryKoin(
    additionalModules: List<Module> = emptyList(),
    appDeclaration: KoinAppDeclaration? = null,
): KoinApplication? {
    if (GlobalContext.getOrNull() != null) return null
    return startKoin {
        appDeclaration?.invoke(this)
        modules(listOf(inMemoryDataModule(), aiModule, viewModelModule) + additionalModules)
    }
}
