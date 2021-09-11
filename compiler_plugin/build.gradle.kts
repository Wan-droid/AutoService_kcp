plugins {
    kotlin("jvm")
}

//java {
//    sourceCompatibility = JavaVersion.VERSION_1_7
//    targetCompatibility = JavaVersion.VERSION_1_7
//}
val kotlinBaseVersion = "1.6.0-dev-2458"
dependencies {

    implementation(kotlin("stdlib"))
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")
//    kapt("com.google.auto.service:auto-service:1.0-rc7")
    compileOnly("com.google.auto.service:auto-service-annotations:1.0-rc7")

    testImplementation(kotlin("test-junit"))
    testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.4.1")
    testCompileOnly("com.google.auto.service:auto-service-annotations:1.0-rc7")
}