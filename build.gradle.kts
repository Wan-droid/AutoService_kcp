plugins {
    kotlin("jvm") version "1.5.10" apply false
    id("com.gradle.plugin-publish") version "0.12.0" apply false
    id("com.github.gmazzo.buildconfig") version "2.1.0" apply false
}

allprojects {
    group = group
}

subprojects {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }
}
