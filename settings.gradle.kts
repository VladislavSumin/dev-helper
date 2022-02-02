rootProject.name = "dev-helper"
pluginManagement {
    includeBuild("../vs-core/buildScript")

    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

includeBuild("../vs-core")
