import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.gretty") version "2.3.1"
    id("com.devsoap.vaadin-flow") version "1.2"
    id("io.spring.dependency-management")
    id("org.springframework.boot") version "2.1.5.RELEASE"
    kotlin("jvm")
    kotlin("plugin.spring")
}

group = "ru.ulmc.frontend"
version = "1.0-SNAPSHOT"
description = "Frontend"

java.sourceCompatibility = JavaVersion.VERSION_1_8

vaadin {
    version = "13.+"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven("http://maven.vaadin.com/vaadin-addons")
    maven("https://maven.vaadin.com/vaadin-prereleases")
    maven("http://repo.maven.apache.org/maven2")
    maven("https://oss.sonatype.org/content/repositories/vaadin-snapshots/")
}

dependencyManagement {
    imports {
        mavenBom("io.spring.platform:platform-bom:Cairo-RELEASE")
        mavenBom("com.vaadin:vaadin-bom:13.0.8")
    }
}

dependencies {
    implementation(project(":api"))
    implementation(project(":model"))
    implementation("com.google.guava:guava:28.0-jre")
    implementation("org.springframework.boot:spring-boot-starter-artemis")
    implementation("org.messaginghub:pooled-jms")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.vaadin:vaadin")
    implementation("com.vaadin:vaadin-spring")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.apache.tomcat.embed:tomcat-embed-core:8.5.37")
    implementation("com.h2database:h2:+")
    testImplementation ("org.springframework.boot:spring-boot-starter-test:2.1.6.RELEASE")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
