rootProject.name = "Movieland"

// Include project modules
include(":app")
include(":core")
include(":util")
include(":detail")
include(":search")
include(":profile")
include(":home")
include(":movies")
include(":people")
include(":tvshows")
include(":liked")
include(":compose")

// Plugin management for resolving plugins
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.3.1"
        id("com.android.library") version "8.3.1"
        id("org.jetbrains.kotlin.android") version "1.9.22"
        id("org.jetbrains.kotlin.kapt") version "1.9.22"
        id("org.jetbrains.kotlin.plugin.parcelize") version "1.9.22"
        id("androidx.navigation.safeargs") version "2.7.7"
    }
}

// Dependency resolution management for resolving project dependencies
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
