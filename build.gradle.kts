import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("application")
    id("java")
    id("com.gradleup.shadow") version "9.3.0"
    id("io.freefair.lombok") version "9.1.0"
    kotlin("jvm") version "2.1.21"
}

group = "moe.sota"
version = "0.9.9"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin.compilerOptions {
    jvmTarget = JvmTarget.JVM_17
}

application {
    mainClass = "${project.group}.${project.name.lowercase()}.MainKt"
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.fabricmc.net") }
}

dependencies {
    implementation("com.fifesoft:rsyntaxtextarea:3.5.4")
    implementation("com.formdev:flatlaf:3.5.4")
    implementation("com.formdev:flatlaf-extras:3.5.4")
    implementation("com.formdev:flatlaf-fonts-inter:4.0")
    implementation("com.formdev:flatlaf-fonts-jetbrains-mono:2.304")
    implementation("com.github.java-decompiler:jd-core:v1.1.3")
    implementation("com.miglayout:miglayout-swing:11.4.2")
    implementation("net.fabricmc:cfr:0.2.2")
    implementation("org.bitbucket.mstrobel:procyon-compilertools:0.6.0")
    implementation("org.jetbrains:annotations:26.0.2")
    implementation("org.vineflower:vineflower:1.11.1")
}

tasks {
    processResources {
        dependsOn(":agent:build")
        outputs.upToDateWhen { false }
        filesMatching("application.properties") {
            expand(project.properties)
        }
    }

    jar {
        enabled = false
    }

    shadowJar {
        dependsOn(distTar, distZip)
        archiveBaseName = project.name.lowercase()
        archiveClassifier = null
    }

    test {
        enabled = false
    }

    register<Jar>("testJar") {
        archiveFileName = "test.jar"
        from(sourceSets.test.get().output)
    }

    named<JavaExec>("run") {
        dependsOn("testJar")
        args = listOf((named("testJar").get() as Jar).archiveFile.get().asFile.path)
    }
}
