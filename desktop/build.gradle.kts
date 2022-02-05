plugins {
    id("ru.vs.convention.multiplatform.jvm")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(libs.vs.coroutines)
                implementation(libs.vs.di)
                implementation(libs.vs.logging.slf4j)

                implementation(project(":core:adb"))
            }
        }
    }
}
