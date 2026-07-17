import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("application")
    alias(libs.plugins.kotlin.jvm)
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

application {
    applicationDefaultJvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
    mainClass = "${project.group}.${project.name.lowercase()}.Main"
}

val agentJar by configurations.creating { isTransitive = false }
val demoJar by configurations.creating

dependencies {
    agentJar(project(":agent"))
    demoJar(project(":demo"))

    implementation(libs.asm)
    implementation(libs.asm.util)
    implementation(libs.cfr)
    implementation(libs.flatlaf)
    implementation(libs.flatlaf.extras)
    implementation(libs.flatlaf.fonts.inter)
    implementation(libs.flatlaf.fonts.jetbrains.mono)
    implementation(libs.h2)
    implementation(libs.jd.core)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.swing)
    implementation(libs.ktorm.core)
    implementation(libs.miglayout.swing)
    implementation(libs.procyon)
    implementation(libs.rsyntaxtextarea)
    implementation(libs.vineflower)
}

spotless {
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
        archiveBaseName = project.name.lowercase()
        archiveClassifier = null
        mergeServiceFiles()
        manifest { attributes("Enable-Native-Access" to "ALL-UNNAMED") }
    }

    startScripts { dependsOn(shadowJar) }

    named<JavaExec>("run") {
        val demoFiles: FileCollection = demoJar
        inputs.files(demoFiles)
        argumentProviders.add(
            CommandLineArgumentProvider { listOf(demoFiles.singleFile.absolutePath) }
        )
    }
}
