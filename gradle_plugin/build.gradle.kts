plugins {
    id("java-gradle-plugin")
    kotlin("jvm")
    id("maven-publish")
    id("com.github.gmazzo.buildconfig")
}

dependencies {
    implementation(kotlin("gradle-plugin-api"))
}

buildConfig{
    packageName(project.group.toString())
    buildConfigField("String", "PLUGIN_ID", "\"${project.properties["pluginId"]}\"")
    buildConfigField("String", "GROUP_ID", "\"${project.properties["group"]}\"")
    buildConfigField("String", "ARTIFACT", "\"${project.properties["artifact"]}\"")
    buildConfigField("String", "VERSION", "\"${project.properties["version"]}\"")
}

gradlePlugin {
    plugins {
        create("AutoServiceKcp") {
            id = project.properties["pluginId"] as String
            displayName = "AutoService implement by KCP"
            description = "AutoService implement by KCP"
            implementationClass = "com.wandroid.autoservice.gradle.AutoServiceGradlePlugin"
        }
    }
}
