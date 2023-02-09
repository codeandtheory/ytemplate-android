repositories {
    mavenCentral()
    google()
}

plugins {
    `kotlin-dsl`
}
gradlePlugin {
    plugins {
        register("jacoco-reports") {
            id = "jacoco-reports"
            implementationClass = "ytemplate.android.build.JacocoTestReportPlugin"
        }

    }
}
dependencies {
    implementation(versionCatalogLibs.bundles.build.src)
}