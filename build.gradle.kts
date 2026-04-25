plugins { alias(libs.plugins.spotless) }

allprojects { group = "moe.sota" }

spotless { kotlinGradle { ktfmt().kotlinlangStyle() } }
