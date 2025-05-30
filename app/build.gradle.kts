plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.chapz.apollo"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.chapz.apollo"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        // this prevents the thousands of Compose classes to go wild with annotations
        freeCompilerArgs = listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3ExpressiveApi",
            "-Xcontext-receivers"
        )
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.activity)
    implementation(libs.compose.graphics)
    implementation(libs.compose.ui.m3)
    implementation(libs.compose.tooling)
    implementation(libs.compose.tooling.preview)
    implementation(libs.compose.junit)
    implementation(libs.compose.navigation)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.compose.material.icons)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.core)

    implementation(libs.media.exoplayer)
    implementation(libs.media.ui)
    implementation(libs.media.common)
    implementation(libs.media.session)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.coil.compose)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)

    //androidTestImplementation(libs.mockk.android)
}