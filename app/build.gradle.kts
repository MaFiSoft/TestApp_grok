// Timestamp: 2025-05-23 12:00:00 CEST
// /app/build.gradle.kts
plugins {
    id("com.android.application") version "8.6.0" // Neu: 8.6.0 für Kompatibilität
    id("org.jetbrains.kotlin.android") version "2.0.0"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    id("com.google.devtools.ksp") version "2.0.0-1.0.24"
}

android {
    namespace = "com.example.testapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/kotlin")
            kotlin.srcDirs("src/main/kotlin")
        }
        getByName("debug") {
            java.srcDirs("src/debug/kotlin")
            kotlin.srcDirs("src/debug/kotlin")
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.18"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2024.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
}

ksp {
    arg("room.schemaLocation", "${projectDir}/schemas")
    arg("room.verbose", "true")
    arg("room.generateKotlin", "true")
}

tasks.register("verifyRoomSchema") {
    doLast {
        val schemaDir = file("${projectDir}/schemas")
        val schemaFile = file("${projectDir}/schemas/1.json")
        println("Schema directory: ${schemaDir.path}")
        println("Schema file: ${schemaFile.path}")
        if (schemaDir.exists()) {
            println("Schema directory exists")
            if (schemaFile.exists() && schemaFile.length() > 0) {
                println("Schema file ${schemaFile.path} exists and is not empty")
                println("Schema file size: ${schemaFile.length()} bytes")
            } else {
                println("Warning: Schema file ${schemaFile.path} is missing or empty")
            }
        } else {
            println("Warning: Schema directory ${schemaDir.path} does not exist")
        }
    }
}
