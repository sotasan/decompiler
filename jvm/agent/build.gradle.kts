plugins {
    id("java")
    alias(libs.plugins.lombok)
    alias(libs.plugins.spotless)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

spotless {
    java {
        palantirJavaFormat()
    }
}

tasks.jar {
    manifest {
        attributes("Agent-Class" to "${project.group}.${rootProject.name.lowercase()}.${project.name}.Main")
    }
}
