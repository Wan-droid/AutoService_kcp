plugins {
    kotlin("jvm")
}

dependencies {

    implementation(kotlin("stdlib"))
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    compileOnly("com.google.auto.service:auto-service-annotations:1.0-rc7")

    testImplementation(kotlin("test-junit"))
    testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.4.1")
    testCompileOnly("com.google.auto.service:auto-service-annotations:1.0-rc7")
}