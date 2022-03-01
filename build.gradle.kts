plugins {
    kotlin("jvm") version "1.6.10"
    id("org.openjfx.javafxplugin") version "0.0.12"
}

group = "net.pryoscode"
version = "0.0.0"

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_17.toString()))
    }
}

javafx {
    version = JavaVersion.VERSION_17.toString()
    modules("javafx.controls")
}

tasks.jar {
    archiveBaseName.set(project.name.toLowerCase())
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest.attributes["Specification-Version"] = project.version
    manifest.attributes["Main-Class"] = "${project.group}.${project.name.toLowerCase()}.Main"
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project("fernflower"))
    implementation("org.fxmisc.richtext:richtextfx:0.10.7")
}