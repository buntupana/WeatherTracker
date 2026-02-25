plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.panabuntu.weathertracker.forecast_list.data"
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

kotlin {
    compilerOptions {
        languageVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3
    }
}

dependencies {

    // Modules
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    testImplementation(project(":core:testing"))
    implementation(project(":feature:forecast_daily:domain"))

    implementation(libs.kotlinx.serialization.json)

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