plugins {
    kotlin("jvm")
    id("maven-publish")
    id("com.github.gmazzo.buildconfig")
}

dependencies {

    implementation(kotlin("stdlib"))
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    implementation("com.google.auto.service:auto-service-annotations:1.0-rc7")

    testImplementation(kotlin("test-junit"))
    testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.4.1")
}

buildConfig{
    packageName(project.group.toString())
    buildConfigField("String", "PLUGIN_ID", "\"${project.properties["pluginId"]}\"")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.properties["group"] as String
            artifactId = project.properties["artifact"] as String
            version = project.properties["version"] as String

            from(components["java"])
        }
        repositories {
        }
    }
}