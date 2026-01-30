plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.services)
}

android {
    compileSdk = 36

    defaultConfig {
        applicationId = "edu.schoolapp"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.cardview)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.material)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.core)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)

    implementation(libs.play.services.auth)

    implementation(libs.androidx.browser)
    implementation(libs.mpandroidchart)

    implementation(libs.googleid)
}
