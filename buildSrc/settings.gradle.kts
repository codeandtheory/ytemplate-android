dependencyResolutionManagement {
    versionCatalogs {
        create("versionCatalogLibs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
