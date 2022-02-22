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

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
        create("coreLibs") {
            from(files("../vs-core/core-libs.versions.toml"))
        }
    }
}

includeBuild("../vs-core")

include(":core:adb")
include(":desktop")
