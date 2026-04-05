plugins {
    id("java")
    id("com.diffplug.spotless") version "8.4.0"
    id("io.freefair.lombok") version "9.1.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

spotless {
    java {
        palantirJavaFormat()
    }
}

tasks.jar {
    archiveExtension = "zip"
    manifest {
        attributes("Agent-Class" to "${project.group}.${rootProject.name.lowercase()}.${project.name}.Main")
    }
}
