plugins {
    id("java")
    alias(libs.plugins.spotless)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

spotless {
    java {
        palantirJavaFormat()
    }
}
