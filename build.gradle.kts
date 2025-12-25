import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("application")
    id("java")
    id("com.gradleup.shadow") version "9.3.0"
    id("io.freefair.lombok") version "9.1.0"
    kotlin("jvm") version "2.1.21"
}

group = "moe.sota"
version = "0.10.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin.compilerOptions {
    jvmTarget = JvmTarget.JVM_17
}

application {
    applicationDefaultJvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
    mainClass = "${project.group}.${project.name.lowercase()}.Main"
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.fabricmc.net") }
}

dependencies {
    implementation("com.fifesoft:rsyntaxtextarea:3.6.0")
    implementation("com.formdev:flatlaf:3.7")
    implementation("com.formdev:flatlaf-extras:3.7")
    implementation("com.formdev:flatlaf-fonts-inter:4.1")
    implementation("com.formdev:flatlaf-fonts-jetbrains-mono:2.304")
    implementation("com.h2database:h2:2.4.240")
    implementation("com.github.java-decompiler:jd-core:v1.1.3")
    implementation("com.miglayout:miglayout-swing:11.4.2")
    implementation("io.insert-koin:koin-core:4.1.1")
    implementation("net.fabricmc:cfr:0.2.2")
    implementation("org.bitbucket.mstrobel:procyon-compilertools:0.6.0")
    implementation("org.jetbrains:annotations:26.0.2-1")
    implementation("org.ktorm:ktorm-core:4.1.1")
    implementation("org.ow2.asm:asm:9.9")
    implementation("org.ow2.asm:asm-util:9.9")
    implementation("org.vineflower:vineflower:1.11.2")
}

tasks {
    processResources {
        dependsOn(":agent:build")
        outputs.upToDateWhen { false }
        val version = project.version
        filesMatching("application.properties") {
            expand("version" to version)
        }
    }

    jar {
        enabled = false
    }

    shadowJar {
        dependsOn(distTar, distZip)
        archiveBaseName = project.name.lowercase()
        archiveClassifier = null
        manifest {
            attributes(
                "Enable-Native-Access" to "ALL-UNNAMED"
            )
        }
    }

    test {
        enabled = false
    }

    val testJarTask = register<Jar>("testJar") {
        archiveFileName = "test.jar"
        from(sourceSets.test.get().output)
    }

    named<JavaExec>("run") {
        dependsOn(testJarTask)
        args = listOf(testJarTask.get().archiveFile.get().asFile.path)
    }
}
