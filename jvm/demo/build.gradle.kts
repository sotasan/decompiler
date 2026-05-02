plugins {
    id("java")
    alias(libs.plugins.spotless)
}

java {
    sourceCompatibility = JavaVersion.VERSION_26
    targetCompatibility = JavaVersion.VERSION_26
}

spotless {
    java {
        palantirJavaFormat()
    }
}
