plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.panabuntu.weathertracker.core.testing"
    compileSdk {
        version = release(libs.versions.compile.sdk.get().toInt())
    }

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    // Modules
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:presentation"))

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.activity.compose)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // Local Tests
    testImplementation(libs.bundles.local.tests)

    // Instrumented Tests
    androidTestImplementation(libs.bundles.instrumented.tests)
}