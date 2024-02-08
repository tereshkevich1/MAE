plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt") version "1.9.22"
}

android {
    namespace = "com.example.calculator"
    compileSdk = 34

    dataBinding{
        enable = true
    }

    defaultConfig {
        applicationId = "com.example.calculator"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}
val activity_version = "1.8.2"
val androidXTestVersion = "1.5.0"
val mockitoVersion = "5.10.0"
val mockitoKotlinVersion = "5.2.1"
val mockkVersion = "1.13.9"
dependencies {
    implementation("androidx.activity:activity-ktx:$activity_version")
    implementation("org.mariuszgromada.math:MathParser.org-mXparser:5.2.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.databinding:databinding-runtime:8.2.2")
    implementation("androidx.test.ext:junit-ktx:1.1.5")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("androidx.test:core:$androidXTestVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}