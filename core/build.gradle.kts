import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    namespace = "com.github.af2905.movieland.core"

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        // Load keystore properties
        val keystorePropertiesFile = rootProject.file("keystore.properties")
        val keystoreProperties = Properties().apply {
            load(FileInputStream(keystorePropertiesFile))
        }

        buildConfigField("String", "THE_MOVIE_DATABASE_API_KEY", keystoreProperties["THE_MOVIE_DATABASE_API_KEY"].toString())
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
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

    buildFeatures {
        buildConfig = true
        dataBinding = true
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    // Project dependencies
    implementation(project(":util"))

    // Libraries from version catalog
    implementation(libs.kotlin.stdlib)
    implementation(libs.core.ktx)

    implementation(libs.dagger.android.support)
    implementation(libs.hilt.common)
    kapt(libs.dagger.compiler)
    kapt(libs.dagger.android.processor)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.work.manager)
    implementation(libs.timber)
    implementation(libs.navigation.fragment)
    implementation(libs.glide)
    implementation(libs.material)
    implementation(libs.swiperefreshlayout)
    implementation(libs.recyclerview)
    implementation(libs.shimmer)

    testImplementation(libs.junit)
    implementation(libs.junit.ext)

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

    // Datastore
    implementation(libs.datastore.preferences)
    implementation(libs.datastore)
}