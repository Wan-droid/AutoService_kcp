plugins {
    id("java-gradle-plugin")
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("gradle-plugin-api"))
}

gradlePlugin {
    plugins {
        create("AutoServiceKcp") {
            id = rootProject.extra["kotlin_plugin_id"] as String
            displayName = "AutoService implement by KCP"
            description = "AutoService implement by KCP"
            implementationClass = "com.wandroid.autoservice.gradle.AutoServiceGradlePlugin"
        }
    }
}
