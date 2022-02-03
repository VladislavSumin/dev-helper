rootProject.name = "dev-helper"
pluginManagement {
    includeBuild("../vs-core/buildScript")

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
