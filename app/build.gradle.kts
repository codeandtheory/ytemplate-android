import ytemplate.android.jacoco.addExclusion

@Suppress("DSL_SCOPE_VIOLATION") // scope violation issue: work around suggested from: https://github.com/gradle/gradle/issues/22797
plugins {
    id("ytemplate.android.application")
    id("ytemplate.android.application.jacoco")
    id("ytemplate.android.application.compose")
    id("ytemplate.android.hilt")
}
private val excludedFiles = mutableSetOf(
    "**/ytemplate/android/MainActivity.*",
    "**/ytemplate/android/YTemplate.*"
)
addExclusion(excludedFiles)
android {
    namespace = "ytemplate.android"
    defaultConfig {
        applicationId = "ytemplate.android"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(versionCatalogLibs.hilt.nav.compose)
    implementation(versionCatalogLibs.androidx.lifecycle.viewModelCompose)

    implementation(project(mapOf("path" to ":core:ui")))
    implementation(project(mapOf("path" to ":feature:post")))

    androidTestImplementation(versionCatalogLibs.androidx.test.core)
    androidTestImplementation(versionCatalogLibs.androidx.test.core.ktx)
    androidTestImplementation(versionCatalogLibs.androidx.test.ext)
    androidTestImplementation(versionCatalogLibs.androidx.test.runner)
    androidTestImplementation(versionCatalogLibs.androidx.test.rules)

    debugImplementation("androidx.compose.ui:ui-test-manifest:${versionCatalogLibs.versions.compose}")
    androidTestImplementation(project(mapOf("path" to ":core:test")))
}
