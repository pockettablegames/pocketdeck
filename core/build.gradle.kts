plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    js {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.serialization)
                implementation(libs.kotlin.serialization.json)
                implementation(libs.kotlin.coroutines.core)

                api(projects.api)
            }
        }
    }
}