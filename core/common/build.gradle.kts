import ytemplate.android.jacoco.addExclusion
import ytemplate.android.jacoco.setModuleTestCoverageLimits

plugins {
    id("ytemplate.android.library")
    id("ytemplate.android.library.jacoco")
    id("ytemplate.android.hilt")
}

private val excludedFiles = mutableSetOf(
    "**/ytemplate/android/core/common/model/*",
    "**/ytemplate/android/core/common/di/*",
    "**/ytemplate/android/core/common/AppResult.*"
)
private val limits = mutableMapOf(
    "instruction" to 0.0,
    "branch" to 0.0,
    "line" to 0.0,
    "complexity" to 0.0,
    "method" to 0.0,
    "class" to 0.0
)
addExclusion(excludedFiles)
setModuleTestCoverageLimits(limits)

android {
    namespace = "ytemplate.android.core.common"
}
