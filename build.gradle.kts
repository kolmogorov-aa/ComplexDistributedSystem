plugins {
    java
}

group = "ru.ulmc.school"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}