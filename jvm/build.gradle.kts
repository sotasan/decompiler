import java.nio.file.Files
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java")
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.lombok)
    alias(libs.plugins.runtime)
    alias(libs.plugins.shadow)
    alias(libs.plugins.spotless)
}

allprojects { group = "moe.sota.decompiler" }

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

kotlin.compilerOptions { jvmTarget = JvmTarget.JVM_25 }

application { mainClass = "moe.sota.decompiler.jvm.Main" }

runtime {
    options = listOf("--strip-debug", "--no-header-files", "--no-man-pages", "--compress", "2")
    modules =
        listOf(
            "java.compiler",
            "java.desktop",
            "java.instrument",
            "java.management",
            "java.prefs",
            "java.scripting",
            "java.sql.rowset",
            "jdk.net",
            "jdk.unsupported",
        )
}

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
        archiveClassifier = null
        manifest { attributes("Enable-Native-Access" to "ALL-UNNAMED") }
    }

    named("jre") {
        notCompatibleWithConfigurationCache("badass-runtime-plugin 2.0.1")
        doLast {
            val jre = layout.buildDirectory.dir("jre").get().asFile
            jre.walkBottomUp().filter { Files.isSymbolicLink(it.toPath()) }.forEach {
                val target = it.toPath().toRealPath()
                it.delete()
                Files.copy(target, it.toPath())
            }
            jre.walkTopDown().forEach { it.setWritable(true, true) }
        }
    }
}
