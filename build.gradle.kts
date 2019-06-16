import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    kotlin("plugin.spring") version "1.3.31"
    kotlin("jvm") version "1.3.31"
}

group = "ru.ulmc.school"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}