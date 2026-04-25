import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.lombok)
    alias(libs.plugins.shadow)
    alias(libs.plugins.spotless)
}

version = "0.10.0"

allprojects { group = "moe.sota" }

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin.compilerOptions { jvmTarget = JvmTarget.JVM_17 }

val agentJar by configurations.creating { isTransitive = false }

dependencies {
    agentJar(project(":agent"))

    implementation(libs.asm)
    implementation(libs.asm.util)
    implementation(libs.cfr)
    implementation(libs.flatlaf)
    implementation(libs.flatlaf.extras)
    implementation(libs.flatlaf.fonts.inter)
    implementation(libs.flatlaf.fonts.jetbrains.mono)
    implementation(libs.h2)
    implementation(libs.jd.core)
    implementation(libs.jetbrains.annotations)
    implementation(libs.koin.core)
    implementation(libs.ktorm.core)
    implementation(libs.miglayout.swing)
    implementation(libs.procyon)
    implementation(libs.rsyntaxtextarea)
    implementation(libs.vineflower)
}

spotless {
    java { palantirJavaFormat() }
    kotlin { ktfmt().kotlinlangStyle() }
    kotlinGradle { ktfmt().kotlinlangStyle() }
}

tasks {
    processResources {
        from(agentJar)
        val version = project.version
        inputs.property("version", version)
        filesMatching("application.properties") { expand("version" to version) }
    }

    jar { enabled = false }

    shadowJar {
        archiveBaseName = rootProject.name.lowercase()
        archiveClassifier = null
        manifest { attributes("Enable-Native-Access" to "ALL-UNNAMED") }
    }
}
