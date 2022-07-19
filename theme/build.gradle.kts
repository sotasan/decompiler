plugins {
    java
    id("org.siouan.frontend-jdk11") version "6.0.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

frontend {
    nodeVersion.set("16.16.0")
    yarnEnabled.set(true)
    yarnVersion.set("1.22.19")
    assembleScript.set("build")
}

repositories {
    mavenCentral()
}

tasks {
    jar {
        dependsOn("assembleFrontend")
    }

    clean {
        delete(".yarn", "node", "node_modules", ".yarnrc.yml")
    }
}