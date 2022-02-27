plugins {
    kotlin("jvm") version "1.6.10"
    id("org.openjfx.javafxplugin") version "0.0.12"
}

group = "net.pryoscode"
version = "0.1.0"

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
    archiveFileName.set(project.name + ".jar")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest.attributes["Implementation-Title"] = project.name
    manifest.attributes["Implementation-Version"] = project.version
    manifest.attributes["Main-Class"] = project.group.toString() + "." + project.name.toLowerCase() + ".Main"
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project("fernflower"))
}