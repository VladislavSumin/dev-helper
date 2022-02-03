rootProject.name = "dev-helper"
pluginManagement {
    includeBuild("../vs-core/build-script")
    plugins {
        id("ru.vs.empty_plugin") version "0.1.0"
    }
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

includeBuild("../vs-core")

include(":desktop")
