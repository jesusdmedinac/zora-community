plugins {
    kotlin("multiplatform") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
    id("app.cash.sqldelight") version "2.1.0"
}

kotlin {
    jvm {
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        nodejs()
    }
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
            implementation("app.cash.sqldelight:runtime:2.1.0")
            implementation("app.cash.sqldelight:coroutines-extensions:2.1.0")
            implementation("app.cash.sqldelight:primitive-adapters:2.1.0")
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.1")
        }
        jvmMain.dependencies {
            implementation("app.cash.sqldelight:sqlite-driver:2.1.0")
        }
        jvmTest.dependencies {
            implementation("app.cash.sqldelight:sqlite-driver:2.1.0")
        }
        getByName("wasmJsMain").dependencies {
            implementation("app.cash.sqldelight:web-worker-driver:2.1.0")
        }
        getByName("iosArm64Main").dependencies {
            implementation("app.cash.sqldelight:native-driver:2.1.0")
        }
        getByName("iosSimulatorArm64Main").dependencies {
            implementation("app.cash.sqldelight:native-driver:2.1.0")
        }
    }
}

sqldelight {
    databases {
        create("MechanicalPrescriptionVaultDatabase") {
            packageName.set("com.jesusdmedinac.zora.data.local")
            generateAsync.set(true)
        }
    }
}
