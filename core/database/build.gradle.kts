import ytemplate.android.jacoco.addExclusion
import ytemplate.android.jacoco.setModuleTestCoverageLimits

@Suppress("DSL_SCOPE_VIOLATION") // scope violation issue: work around suggested from: https://github.com/gradle/gradle/issues/22797
plugins {
    id("ytemplate.android.library")
    id("ytemplate.android.hilt")
    id("ytemplate.android.library.jacoco")
}

private val excludedFiles = mutableSetOf(
    "**/ytemplate/android/core/database/model/*",
    "**/ytemplate/android/core/database/di/*",
    "**/ytemplate/android/core/database/dao/*",
    "**/ytemplate/android/core/database/room/AppDataBase_Impl*.*",
    "**/ytemplate/android/core/database/Constants.*"
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
    namespace = "ytemplate.android.database"
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core:common")))
    // Room
    implementation(versionCatalogLibs.bundles.room)
    kapt(versionCatalogLibs.room.compiler)
    implementation(versionCatalogLibs.androidx.test.monitor)
    androidTestImplementation(versionCatalogLibs.bundles.test)
    androidTestImplementation(versionCatalogLibs.coroutine.test)
    androidTestImplementation(versionCatalogLibs.hilt.test)
    androidTestImplementation(project(mapOf("path" to ":core:test")))
    testImplementation(project(mapOf("path" to ":core:test")))
    androidTestImplementation(kotlin("test"))
}
