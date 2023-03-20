enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("versionCatalogLibs") {
            from(files("./gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "YTemplate"
include(":app")
include(":core:network")
include(":core:database")
include(":core:ui")
include(":core:common")
include(":core:data")
include(":core:test")
include("feature:post")
include(":core:analytics:firebase")
include(":core:analytics:analyticsLib")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
