@Suppress("DSL_SCOPE_VIOLATION") // scope violation issue: work around suggested from: https://github.com/gradle/gradle/issues/22797
plugins {
    id("ytemplate.android.library")
    id("ytemplate.android.hilt")
}

android {
    namespace = "ytemplate.android.test"
}

dependencies {
    implementation(project(mapOf("path" to ":core:common")))
    implementation(project(mapOf("path" to ":core:database")))
    implementation(project(mapOf("path" to ":core:network")))
    implementation(project(mapOf("path" to ":core:data")))
    implementation(versionCatalogLibs.ktor.client.mock)
    implementation(versionCatalogLibs.bundles.ktor)
    implementation(versionCatalogLibs.bundles.test)
    implementation(versionCatalogLibs.hilt.test)
    implementation(versionCatalogLibs.androidx.test.monitor)
    implementation(versionCatalogLibs.kotlinx.serialization)
}
