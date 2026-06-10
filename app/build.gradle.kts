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
    implementation("com.google.android.flexbox:flexbox:3.0.0")
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.github.bumptech.glide:glide:4.16.0")


    // ✅ RETROFIT — untuk API Berita
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // ✅ DOTS INDICATOR — untuk OnBoarding
    implementation("com.tbuonomo:dotsindicator:5.1.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}