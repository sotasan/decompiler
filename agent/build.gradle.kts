plugins {
    id("java")
    alias(libs.plugins.spotless)
}

spotless {
    java {
        palantirJavaFormat()
    }
}

tasks {
    compileJava { options.release = 8 }

    jar {
        manifest {
            attributes(
                "Agent-Class" to "${project.group}.${rootProject.name.lowercase()}.${project.name}.Main"
            )
        }
    }
}
