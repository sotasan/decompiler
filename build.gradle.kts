plugins {
    java
    kotlin("jvm") version "1.7.10"
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

dependencies {
    implementation(project(":theme"))
    implementation("org.jetbrains:annotations:23.0.0")
    implementation("com.rainerhahnekamp:sneakythrow:1.2.0")
    implementation("org.quiltmc:quiltflower:1.8.1")
    implementation("org.ow2.asm:asm:9.3")
    implementation("org.ow2.asm:asm-tree:9.3")
    implementation("org.ow2.asm:asm-util:9.3")
    implementation("com.formdev:flatlaf:2.4")
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

    jar {
        archiveBaseName.set(project.name.toLowerCase())
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest.attributes["Specification-Version"] = project.version
        manifest.attributes["Main-Class"] = "${project.group}.${project.name.toLowerCase()}.Main"
        manifest.attributes["Agent-Class"] = "${project.group}.${project.name.toLowerCase()}.Agent"
        from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    }

    jpackage {
        appName = rootProject.name
        copyright = "Copyright 2022 shota"
        appDescription = "Java Decompiler Gui"
        vendor = "shota"
        input = jar.get().destinationDirectory.get().asFile.path
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
        }
    }

    register<Jar>("testJar") {
        archiveFileName.set("test.jar")
        manifest.attributes["Main-Class"] = "${project.group}.${project.name.toLowerCase()}.Main"
        from(sourceSets.test.get().output)
    }

    clean {
        dependsOn(":theme:clean")
    }
}