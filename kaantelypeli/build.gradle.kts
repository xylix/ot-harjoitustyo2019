import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `maven-publish`
    id("org.openjfx.javafxplugin") version "0.0.9"
    id("application")
    kotlin("jvm") version "1.4.20"
}

repositories {
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("org.openjfx:javafx-controls:11.0.2")
    implementation("org.openjfx:javafx-graphics:11")
    implementation("org.openjfx:javafx-graphics:11")
    implementation("org.openjfx:javafx-graphics:11")
    implementation("org.tinylog:tinylog-api-kotlin:2.0.1")
    implementation("org.tinylog:tinylog-impl:2.0.1")
    implementation("com.konghq:unirest-java:3.3.00")
    testImplementation("junit:junit:4.13.1")
    testImplementation("org.testfx:openjfx-monocle:jdk-11+26")
    testImplementation("org.testfx:testfx-junit:4.0.15-alpha")
    testImplementation("org.hamcrest:hamcrest-core:2.1")
    testImplementation("com.github.stefanbirkner:system-rules:1.19.0")
    implementation(kotlin("stdlib-jdk8"))
}

javafx {
    version = "11"
    modules("javafx.controls", "javafx.fxml")
}

group = "kaantelypeli"
version = "0.3.1"
description = "kaantelypeli"
java.sourceCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

/* application {
    mainClass.set("kaantelypeli.ui.Main")
}
*/
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
