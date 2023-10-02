plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    kotlin("android")
}

android {
    compileSdk = 34
//    defaultConfig {
//        applicationId "edu.schoolapp"
//        minSdkVersion 23
//        targetSdkVersion 34
//        versionCode 1
//        versionName "1.0"
//    }

    defaultConfig {
        applicationId = "edu.schoolapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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

    namespace = "edu.schoolapp"
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0-alpha12")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.firebase:firebase-database:20.2.2")
    implementation("com.google.firebase:firebase-auth:22.1.2")
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.firebase:firebase-firestore:24.8.1")
    implementation("androidx.browser:browser:1.6.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}

//apply(plugin = "com.google.gms.google-services")
