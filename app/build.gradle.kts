plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias (libs.plugins.kapt)
    alias(libs.plugins.room)
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "com.example.breakdown"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.breakdown"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,LICENSE.md,LICENSE-notice.md}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.datastore.android)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.transport.runtime)

    // Testing Instrumentales o UI
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.mockk.android)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // UI
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigationcompose)

    // Testing Unitarios
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.robolectric)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.truth)

    // Animacion Lottie
    implementation(libs.lottie.compose)

    // Material Icons
    implementation(libs.material.icons.extended)

    // Usar hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // Permisos
    implementation(libs.accompanist.permissions)
}