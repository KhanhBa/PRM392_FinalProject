plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.prm392_fp_soccer_field"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.prm392_fp_soccer_field"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }


}

dependencies {
    implementation (libs.material.v180)

    implementation (libs.firebase.auth)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.dagger)
    implementation(libs.dagger.android.support)
    implementation(libs.glide)
    implementation(libs.sdk.core)
    implementation(libs.sdk.auth)
    implementation(libs.sdk.openapi)
    implementation(libs.support.annotations)
    implementation(libs.protolite.well.known.types)
    annotationProcessor(libs.dagger.compiler)
    annotationProcessor(libs.dagger.android.processor)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.ormlite.core.v56)
    implementation(libs.j256.ormlite.jdbc)
    implementation( libs.ormlite.android.v56)
    implementation(libs.mssql.jdbc)
}