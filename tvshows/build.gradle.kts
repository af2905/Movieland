plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("androidx.navigation.safeargs")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    namespace = "com.github.af2905.movieland.tvshows"

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
            "-Xopt-in=kotlin.contracts.ExperimentalContracts"
        )
    }

    buildFeatures {
        dataBinding = true
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    // Project modules
    implementation(project(":core"))
    implementation(project(":util"))
    implementation(project(":detail"))

    // Libraries from version catalog
    implementation(libs.dagger.android.support)
    kapt(libs.dagger.compiler)
    kapt(libs.dagger.android.processor)
    implementation(libs.navigation.fragment)
    implementation(libs.glide)
    implementation(libs.material)
    implementation(libs.swiperefreshlayout)
    implementation(libs.recyclerview)
    implementation(libs.shimmer)
    implementation(libs.monitor)
    implementation(libs.junit.ext)
    androidTestImplementation(libs.junit)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    // Hilt navigation for Jetpack Compose
    implementation(libs.hilt.navigation.compose)
}