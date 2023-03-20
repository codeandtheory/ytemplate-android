@Suppress("DSL_SCOPE_VIOLATION") // scope violation issue: work around suggested from: https://github.com/gradle/gradle/issues/22797
plugins {
    id("ytemplate.android.library")
    id("ytemplate.android.hilt")
}

android {
    namespace = "ytemplate.android.domain"
}

dependencies {
    implementation(project(mapOf("path" to ":core:common")))

    testImplementation(project(mapOf("path" to ":core:test")))
    implementation(versionCatalogLibs.androidx.test.monitor)
    androidTestImplementation(versionCatalogLibs.bundles.test)
    androidTestImplementation(versionCatalogLibs.coroutine.test)
    androidTestImplementation(versionCatalogLibs.hilt.test)
    androidTestImplementation(project(mapOf("path" to ":core:test")))
    androidTestImplementation(kotlin("test"))
}