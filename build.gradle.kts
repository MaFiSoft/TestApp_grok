buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.5.2") // Android Gradle Plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0") // Kotlin
        classpath("com.google.devtools.ksp:ksp-gradle-plugin:2.0.0-1.0.24") // KSP
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
