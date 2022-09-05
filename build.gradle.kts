plugins {
    java
    antlr
    kotlin("jvm") version "1.7.10"
    id("io.freefair.lombok") version "6.5.1"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.panteleyev.jpackageplugin") version "1.3.1"
}

group = "dev.shota"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.quiltmc.org/repository/release/")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

javafx {
    version = JavaVersion.VERSION_18.toString()
    modules("javafx.base", "javafx.controls", "javafx.graphics", "javafx.swing")
}

sourceSets {
    main {
        java {
            srcDirs(srcDirs, "build/generated-src/antlr")
        }
    }
}

dependencies {
    antlr("org.antlr:antlr4:4.11.1")
    implementation("org.antlr:antlr4-runtime:4.11.1")
    implementation("org.jetbrains:annotations:23.0.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.13.4")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4")
    implementation("org.slf4j:slf4j-simple:2.0.0")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.ow2.asm:asm:9.3")
    implementation("org.ow2.asm:asm-util:9.3")
    implementation("org.quiltmc:quiltflower:1.8.1")
    implementation("com.formdev:flatlaf:2.4")
    implementation("org.controlsfx:controlsfx:11.1.1")
    implementation("org.fxmisc.richtext:richtextfx:0.10.9")
    implementation("org.openjfx:javafx-base:${javafx.version}:win")
    implementation("org.openjfx:javafx-base:${javafx.version}:mac-aarch64")
    implementation("org.openjfx:javafx-base:${javafx.version}:linux")
    implementation("org.openjfx:javafx-controls:${javafx.version}:win")
    implementation("org.openjfx:javafx-controls:${javafx.version}:mac-aarch64")
    implementation("org.openjfx:javafx-controls:${javafx.version}:linux")
    implementation("org.openjfx:javafx-graphics:${javafx.version}:win")
    implementation("org.openjfx:javafx-graphics:${javafx.version}:mac-aarch64")
    implementation("org.openjfx:javafx-graphics:${javafx.version}:linux")
    implementation("org.openjfx:javafx-swing:${javafx.version}:win")
    implementation("org.openjfx:javafx-swing:${javafx.version}:mac-aarch64")
    implementation("org.openjfx:javafx-swing:${javafx.version}:linux")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }

    processResources {
        filesMatching("application.properties") {
            expand(project.properties)
        }
    }

    jar {
        archiveBaseName.set(project.name.toLowerCase())
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest.attributes["Main-Class"] = "${project.group}.${project.name.toLowerCase()}.Main"
        manifest.attributes["Agent-Class"] = "${project.group}.${project.name.toLowerCase()}.Agent"
        from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    }

    jpackage {
        appName = rootProject.name
        copyright = "Copyright 2022 shota"
        appDescription = "Java Decompiler Gui"
        vendor = "shota"
        input = jar.get().destinationDirectory.asFile.get().path
        mainJar = jar.get().archiveFileName.get()
        destination = "${buildDir.path}/jpackage"
        licenseFile = "${sourceSets["main"].resources.srcDirs.first().path}/license.txt"

        windows {
            icon = "${sourceSets["main"].resources.srcDirs.first().path}/logo/logo.ico"
            winMenu = true
            winDirChooser = true
            winUpgradeUuid = "880be177-032d-496e-8d92-cefa555b2c88"
            winMenuGroup = "shota"
            winShortcut = true
            winPerUserInstall = true
        }

        mac {
            icon = "${sourceSets["main"].resources.srcDirs.first().path}/logo/logo.icns"
            macPackageIdentifier = "${project.group}.${project.name}"
        }
    }

    register<Jar>("testJar") {
        archiveFileName.set("test.jar")
        manifest.attributes["Main-Class"] = "${project.group}.${project.name.toLowerCase()}.Main"
        from(sourceSets.test.get().output)
    }
}