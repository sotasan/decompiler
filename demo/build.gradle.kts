plugins {
    id("java")
    alias(libs.plugins.spotless)
}

spotless {
    java {
        palantirJavaFormat()
    }
}

tasks.compileJava { options.release = 26 }
