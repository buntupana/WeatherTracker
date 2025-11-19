plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.panabuntu.weathertracker.feature.forecast_hourly.data"
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
}

dependencies {

    // Modules
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    testImplementation(project(":core:testing"))
    implementation(project(":feature:forecast_hourly:domain"))

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    // Networking
    implementation(platform(libs.io.ktor.bom))
    implementation(libs.bundles.ktor)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // Local Tests
    testImplementation(libs.bundles.local.tests)

    // Instrumented Tests
    androidTestImplementation(libs.bundles.instrumented.tests)
}