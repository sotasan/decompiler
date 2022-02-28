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
    archiveFileName.set("${project.name}-v${project.version}.jar")
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
    implementation("org.kordamp.ikonli:ikonli-core:12.3.0")
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.0")
    implementation("org.kordamp.ikonli:ikonli-feather-pack:12.3.0")
}