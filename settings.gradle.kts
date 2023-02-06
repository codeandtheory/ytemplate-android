pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("versionCatalogLibs") {
            from(files("gradle/libs.versions.toml"))
        }
    }
}
rootProject.name = "YTemplate"
include (":app")
