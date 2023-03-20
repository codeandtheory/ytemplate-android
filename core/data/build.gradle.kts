import ytemplate.android.jacoco.addExclusion
import ytemplate.android.jacoco.setModuleTestCoverageLimits

@Suppress("DSL_SCOPE_VIOLATION") // scope violation issue: work around suggested from: https://github.com/gradle/gradle/issues/22797
plugins {
    id("ytemplate.android.library")
    id("ytemplate.android.hilt")
    id("ytemplate.android.library.jacoco")
}

private val excludedFiles = mutableSetOf(
    "**/ytemplate/android/core/data/model/*",
    "**/ytemplate/android/core/data/di/*"
)
private val limits = mutableMapOf(
    "instruction" to 50.0,
    "branch" to 50.0,
    "line" to 50.0,
    "complexity" to 50.0,
    "method" to 50.0,
    "class" to 50.0
)
addExclusion(excludedFiles)
setModuleTestCoverageLimits(limits)

android {
    namespace = "ytemplate.android.data"
}

dependencies {
    implementation(project(mapOf("path" to ":core:database")))
    implementation(project(mapOf("path" to ":core:network")))
    implementation(project(mapOf("path" to ":core:common")))
    testImplementation(project(mapOf("path" to ":core:test")))
}
