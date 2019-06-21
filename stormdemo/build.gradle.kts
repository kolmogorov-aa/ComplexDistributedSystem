import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

group = "ru.ulmc.school"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven("http://clojars.org/repo/")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
dependencies {
    //api("org.apache.storm:storm-core:2.0.0")

    implementation(project(":api"))

    implementation("com.twitter:carbonite:1.5.0")
    implementation("org.apache.storm:storm-core:2.0.0")
    implementation("org.apache.activemq:artemis-jms-client:2.6.4")
    implementation("javax.jms:javax.jms-api:2.0.1")
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.slf4j:slf4j-api:1.7.14")
    implementation("ch.qos.logback:logback-classic:1.1.3")

    compileOnly("org.projectlombok:lombok:1.18.8")
    annotationProcessor ("org.projectlombok:lombok:1.18.8")
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}