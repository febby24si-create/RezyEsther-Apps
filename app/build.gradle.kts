plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.rezy_esther"
    compileSdk = 36 // Disederhanakan agar stabil sesuai targetSdk Anda

    defaultConfig {
        applicationId = "com.example.rezy_esther"
        minSdk = 27
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Tambahkan blok buildFeatures ini untuk mengaktifkan View Binding
    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Tambahkan library Core Splashscreen di sini:
    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}