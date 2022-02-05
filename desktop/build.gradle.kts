plugins {
    id("ru.vs.convention.multiplatform.jvm")
    id("org.jetbrains.compose")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(libs.vs.coroutines)
                implementation(libs.vs.decompose)
                implementation(libs.vs.uikit)
                implementation(libs.vs.navigation)
                implementation(libs.vs.di)
                implementation(libs.vs.logging.slf4j)

                implementation(project(":core:adb"))
            }
        }
    }
}
