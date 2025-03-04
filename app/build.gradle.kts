plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("androidx.navigation.safeargs")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    buildToolsVersion = libs.versions.androidBuildTools.get()

    namespace = "com.github.af2905.movieland"

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
        versionCode = 11
        versionName = "1.6.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            isJniDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"

        freeCompilerArgs += listOf(
                "-Xopt-in=kotlinx.coroutines.FlowPreview",
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlin.contracts.ExperimentalContracts",
                "-opt-in=kotlin.RequiresOptIn"
        )
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    // Project modules
    implementation(project(":core"))
    implementation(project(":compose"))
    implementation(project(":util"))
    implementation(project(":detail"))
    implementation(project(":search"))
    implementation(project(":profile"))
    implementation(project(":home"))
    implementation(project(":liked"))
    implementation(project(":movies"))
    implementation(project(":tvshows"))

    // Libraries from version catalog
    implementation(libs.kotlin.stdlib)
    implementation(libs.core.ktx)

    implementation(libs.dagger.android.support)
    kapt(libs.dagger.compiler)
    kapt(libs.dagger.android.processor)
    implementation(libs.monitor)
    implementation(libs.junit.ext)
    androidTestImplementation(libs.junit)
    implementation(libs.navigation.fragment)
    implementation(libs.material)
    implementation(libs.shimmer)
    implementation(libs.swiperefreshlayout)
    implementation(libs.recyclerview)
    implementation(libs.work.manager)
    implementation(libs.timber)
    implementation(libs.navigation.ui)

    // Compose
    implementation(libs.compose.ui.tooling)
    implementation(libs.ui)
    implementation(libs.navigation.compose)
    implementation(libs.activity.compose)
    implementation(libs.compose.material)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    // Hilt navigation for Jetpack Compose
    implementation(libs.hilt.navigation.compose)
}