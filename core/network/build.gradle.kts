import ytemplate.android.jacoco.addExclusion

@Suppress("DSL_SCOPE_VIOLATION") // scope violation issue: work around suggested from: https://github.com/gradle/gradle/issues/22797
plugins {
    id("ytemplate.android.library")
    id("ytemplate.android.hilt")
    id("ytemplate.android.library.jacoco")
}

private val excludedFiles = mutableSetOf(
    "**/ytemplate/android/core/network/model/*",
    "**/ytemplate/android/core/network/di/*",
    "**/ytemplate/android/core/network/apis/PostApi.*",
    "**/ytemplate/android/core/network/Constants.*",
    "**/ytemplate/android/core/network/Routes.*"
)

addExclusion(excludedFiles)

android {
    namespace = "ytemplate.android.network"
}

dependencies {
    implementation(project(mapOf("path" to ":core:common")))
    // Ktor
    implementation(versionCatalogLibs.bundles.ktor)

    testImplementation(versionCatalogLibs.ktor.client.mock)
    testImplementation(project(mapOf("path" to ":core:test")))
    implementation(versionCatalogLibs.androidx.test.monitor)
    androidTestImplementation(versionCatalogLibs.bundles.test)
    androidTestImplementation(versionCatalogLibs.coroutine.test)
    androidTestImplementation(versionCatalogLibs.hilt.test)
    androidTestImplementation(project(mapOf("path" to ":core:test")))
    androidTestImplementation(kotlin("test"))
}
