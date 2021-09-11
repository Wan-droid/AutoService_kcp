buildscript {
    extra["kotlin_plugin_id"] = "com.bnorm.template.kotlin-ir-plugin"
}

plugins {
    kotlin("jvm") version "1.5.10" apply false
    id("org.jetbrains.dokka") version "1.4.32" apply false
    id("com.gradle.plugin-publish") version "0.12.0" apply false
}

allprojects {
    group = "com.wandroid.autoservice"
    version = "0.1.0-SNAPSHOT"
}

subprojects {
    repositories {
        google()
        mavenCentral()
    }
}
