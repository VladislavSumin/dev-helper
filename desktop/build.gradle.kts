plugins {
    id("convention.multiplatform.jvm")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation("ru.vs:coroutines:0.1.0")
            }
        }
    }
}
