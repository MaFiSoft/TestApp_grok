// /Users/builder/clone/build.gradle.kts
plugins {
    id("com.google.devtools.ksp") version "2.0.0-1.0.24" apply false
}

subprojects {
    repositories {
        google()
        mavenCentral()
    }
}
