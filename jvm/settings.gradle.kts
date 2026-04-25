@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.fabricmc.net")
    }
}

rootProject.name = "Decompiler"

include("agent")

include("demo")
