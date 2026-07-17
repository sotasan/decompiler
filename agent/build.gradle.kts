import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.spotless)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin.compilerOptions { jvmTarget = JvmTarget.JVM_11 }

spotless { kotlin { ktfmt().kotlinlangStyle() } }

tasks.jar {
    manifest {
        attributes("Agent-Class" to "${project.group}.${rootProject.name.lowercase()}.${project.name}.Main")
    }
}
