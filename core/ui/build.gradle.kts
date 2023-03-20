@Suppress("DSL_SCOPE_VIOLATION") // scope violation issue: work around suggested from: https://github.com/gradle/gradle/issues/22797
plugins {
    id("ytemplate.android.library.compose")
    id("ytemplate.android.library")
    id("ytemplate.android.library.jacoco")
}

android {
    namespace = "ytemplate.android.ui"
}

dependencies {
    testImplementation(project(mapOf("path" to ":core:test")))
    androidTestImplementation(project(mapOf("path" to ":core:test")))
}
