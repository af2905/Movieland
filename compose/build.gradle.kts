plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    namespace = "com.github.af2905.movieland.compose"

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
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
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)

    // Compose
    implementation(libs.activity.compose)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling)
    implementation(libs.lifecycle.viewmodel.compose)
}

/*
plugins {
    id 'com.android.library'
    id 'org.jetbrains.kotlin.android'
}

apply from: '../dependencies.gradle'

android {
    compileSdk androidCompileSdkVersion

    namespace 'com.github.af2905.movieland.compose'

    defaultConfig {
        minSdk androidMinSdkVersion
        targetSdk androidTargetSdkVersion

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles "consumer-rules.pro"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
}

dependencies {

    implementation libraries.kotlin
    implementation libraries.coreKtx
    testImplementation libraries.junit
    androidTestImplementation libraries.junit
    implementation libraries.junitExt
    
    //compose
    implementation libraries.activityCompose
    implementation libraries.composeMaterial
    implementation libraries.composeUiTooling
    implementation libraries.lifecycleViewModelCompose
}*/
