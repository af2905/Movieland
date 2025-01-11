plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("androidx.navigation.safeargs")
    id("org.jetbrains.kotlin.plugin.parcelize")
}

android {
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    namespace = "com.github.af2905.movieland.detail"

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }

    buildFeatures {
        dataBinding = true
        compose = true
    }
}

dependencies {
    // Project modules
    implementation(project(":core"))
    implementation(project(":util"))

    // Libraries from version catalog
    implementation(libs.kotlin.stdlib)
    implementation(libs.core.ktx)

    implementation(libs.dagger.android.support)
    kapt(libs.dagger.compiler)
    kapt(libs.dagger.android.processor)
    implementation(libs.navigation.fragment)
    implementation(libs.material)
    implementation(libs.shimmer)
    implementation(libs.swiperefreshlayout)
    implementation(libs.recyclerview)
    implementation(libs.monitor)
    implementation(libs.junit.ext)
    androidTestImplementation(libs.junit)

    // Jetpack Compose
    implementation(libs.activity.compose)
    implementation(libs.compose.material)
    implementation(libs.compose.animation)
    implementation(libs.compose.ui.tooling)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.compose.material.icons)
}