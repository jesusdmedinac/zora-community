package com.jesusdmedinac.zora.di

import com.jesusdmedinac.zora.data.local.InMemoryMechanicalPrescriptionRepository
import com.jesusdmedinac.zora.data.local.MechanicalPrescriptionVaultDatabase
import com.jesusdmedinac.zora.data.local.SqlDelightMechanicalPrescriptionRepository
import com.jesusdmedinac.zora.data.local.driver.DatabaseDriverFactory
import com.jesusdmedinac.zora.data.local.driver.JvmDatabaseDriverFactory
import com.jesusdmedinac.zora.domain.repository.MechanicalPrescriptionRepository
import com.jesusdmedinac.zora.ui.home.CopilotHomeViewModel
import com.jesusdmedinac.zora.ui.home.ZoraShiftedDrawerViewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class ZoraKoinModuleTest : KoinTest {

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `inMemoryDataModule and viewModelModule resolve all dependencies correctly`() {
        startKoin {
            modules(inMemoryDataModule(), viewModelModule)
        }

        val repository: MechanicalPrescriptionRepository = get()
        assertNotNull(repository)
        assertIs<InMemoryMechanicalPrescriptionRepository>(repository)

        val homeViewModel: CopilotHomeViewModel = get()
        assertNotNull(homeViewModel)

        val drawerViewModel: ZoraShiftedDrawerViewModel = get()
        assertNotNull(drawerViewModel)
    }

    @Test
    fun `production dataModule with JvmPlatformModule resolves SQLite database and repository`() {
        val testJvmModule = module {
            single<DatabaseDriverFactory> {
                JvmDatabaseDriverFactory(inMemory = true)
            }
        }

        startKoin {
            modules(dataModule, viewModelModule, testJvmModule)
        }

        val factory: DatabaseDriverFactory = get()
        assertNotNull(factory)

        val database: MechanicalPrescriptionVaultDatabase = get()
        assertNotNull(database)

        val repository: MechanicalPrescriptionRepository = get()
        assertNotNull(repository)
        assertIs<SqlDelightMechanicalPrescriptionRepository>(repository)

        val homeViewModel: CopilotHomeViewModel = get()
        assertNotNull(homeViewModel)

        val drawerViewModel: ZoraShiftedDrawerViewModel = get()
        assertNotNull(drawerViewModel)
    }
}
