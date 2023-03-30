package conventions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.tasks.JacocoReport
import ytemplate.android.jacoco.extractTestCoverage
import ytemplate.android.jacoco.jacoco

/**
 * Project jacoco convention plugin
 *
 * @constructor Create empty Library jacoco convention plugin
 */
class ProjectJacocoConventionPlugin : Plugin<Project> {
    private val project_level_limits = mutableMapOf(
        "instruction" to 0.0,
        "branch" to 0.0,
        "line" to 0.0,
        "complexity" to 0.0,
        "method" to 0.0,
        "class" to 0.0
    )


    fun Project.setProjectTestCoverageLimits(projectLimits: Map<String, Double>? = null) {
        if (projectLimits != null) {
            extra.set("limits", projectLimits)
        } else {
            extra.set("limits", project_level_limits)
        }
    }

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("versionCatalogLibs")
            with(pluginManager) {
                apply("jacoco")
            }
            tasks.register<JacocoReport>("MergeHTMLJacocoReports") {
                val jacocoReport = this
                group = "Reporting"
                description = "Merge all generated JacocoReport"
                logger.quiet("======Merging HTML Reports=========")
                val javaClasses: MutableCollection<String> = mutableListOf()
                val kotlinClasses: MutableCollection<String> = mutableListOf()
                val sourceDir: MutableCollection<String> = mutableListOf()
                val coverageFiles: MutableCollection<String> = mutableListOf()
                subprojects {
                    val subProject = this
                    subProject.plugins.withType<JacocoPlugin>().configureEach {
                        val moduleTask = tasks.findByName("createDemoDebugJacocoReport")
                        jacocoReport.dependsOn(moduleTask)
                        javaClasses.add("${subProject.buildDir}/intermediates/javac/demoDebug/classes")
                        kotlinClasses.add("${subProject.buildDir}/tmp/kotlin-classes/demoDebug")
                        sourceDir.add("${subProject.projectDir}/src/main/java")
                        sourceDir.add("${subProject.projectDir}/src/main/kotlin")
                        sourceDir.add("${subProject.projectDir}/src/demoDebug/java")
                        coverageFiles.add("${subProject.buildDir}/outputs/unit_test_code_coverage/demoDebugUnitTest/testDemoDebugUnitTest.exec")
                        coverageFiles.add("${subProject.buildDir}/outputs/code_coverage/demoDebugAndroidTest/connected/coverage.ec")
                    }
                }
                classDirectories.setFrom(files(javaClasses, kotlinClasses))
                additionalClassDirs.setFrom(files(sourceDir))
                sourceDirectories.setFrom(files(sourceDir))
                executionData.setFrom(files(coverageFiles))
                reports {
                    xml.required.set(true)
                    html.required.set(true)
                }
            }
        }
    }
}
