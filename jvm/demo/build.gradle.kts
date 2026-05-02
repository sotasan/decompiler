plugins {
    id("java")
    alias(libs.plugins.spotless)
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

spotless {
    java {
        palantirJavaFormat()
    }
}
