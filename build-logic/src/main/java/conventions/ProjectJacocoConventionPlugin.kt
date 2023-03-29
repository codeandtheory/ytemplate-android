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
            tasks.register("createMergedJacocoReport") {
                val jacocoReport = this
                group = "Reporting"
                description = "Generate test coverage reports on the debug build"
                subprojects {
                    val subproject = this
                    subproject.plugins.withType<JacocoPlugin>().configureEach {
                        if (tasks.findByName("createDemoDebugJacocoReport") != null) {
                            val moduleTask = tasks.findByName("createDemoDebugJacocoReport")
                            jacocoReport.dependsOn(moduleTask)
                        }
                    }
                }
                doLast {
                    logger.lifecycle("Making Overall coverage report")
                    addReportMergingTask()
                    val metrics = mutableMapOf<String, Map<String, Double>>()
                    val moduleLimits = mutableMapOf<String, Map<String, Double>>()
                    val failures = mutableMapOf<String, List<String>>()

                    if (!extra.has("limits")) {
                        setProjectTestCoverageLimits()
                    }
                    subprojects {
                        if (tasks.findByName("createDemoDebugJacocoReport") != null) {
                            val reportDir = jacoco.reportsDirectory.asFile.get()
                            val report =
                                file("$reportDir/createDemoDebugJacocoReport/createDemoDebugJacocoReport.xml")
                            if (report.exists()) {
                                logger.lifecycle("Checking coverage results:$report")
                                metrics[project.name] = report.extractTestCoverage()
                                moduleLimits[project.name] =
                                    project.extra["limits"] as Map<String, Double>
                            }
                        }
                    }
                    metrics.forEach { (key, metricsMap) ->
                        val extractedMetricsMap = mutableMapOf<String, Double>()
                        if (metricsMap.isNotEmpty()) {
                            val failureMap = metricsMap.filter { item ->
                                item.value < moduleLimits[key]!![item.key]!!
                            }.map { item ->
                                extractedMetricsMap[item.key] = item.value
                                "-${item.key} coverage is: ${item.value}%, minimum is ${moduleLimits[item.key]}%"
                            }
                            if (failureMap.isNotEmpty()) {
                                failures[key] = failureMap
                            }
                        }
                        moduleLimits[key] = extractedMetricsMap
                    }


                    if (failures.isNotEmpty()) {
                        logger.quiet("======Code coverage failures=========")
                        failures.forEach { entry ->
                            logger.quiet("======Module: ${entry.key}=========")
                            entry.value.forEach { logger.quiet(it) }
                        }
                        logger.quiet("===========================================")
                    }

                    if (metrics.isNotEmpty()) {
                        logger.quiet("======Code coverage success=========")
                        metrics.forEach { entry ->
                            logger.quiet("======Module: ${entry.key}=========")
                            entry.value.forEach {
                                logger.quiet("- ${it.key} coverage: ${it.value}")
                            }
                        }
                        logger.quiet("===========================================")
                    }
                }

            }


        }
    }

    private fun Project.addReportMergingTask() {
        tasks.register<JacocoReport>("MergeHTMLJacocoReports") {
            logger.quiet("======Merging HTML Reports=========")
            val javaClasses :MutableCollection<String> = mutableListOf()
            val kotlinClasses :MutableCollection<String> = mutableListOf()
            val sourceDir :MutableCollection<String> = mutableListOf()
            val coverageFiles :MutableCollection<String> = mutableListOf()
            subprojects.forEach { subProject ->
                javaClasses.add("${subProject.buildDir}/intermediates/javac/demoDebug/classes")
                kotlinClasses.add("${subProject.buildDir}/tmp/kotlin-classes/demoDebug")
                sourceDir.add( "${subProject.projectDir}/src/main/java")
                sourceDir.add( "${subProject.projectDir}/src/main/kotlin")
                sourceDir.add( "${subProject.projectDir}/src/demoDebug/java")
                coverageFiles.add("${subProject.buildDir}/outputs/unit_test_code_coverage/demoDebugUnitTest/testDemoDebugUnitTest.exec")
                coverageFiles.add("${subProject.buildDir}/outputs/code_coverage/demoDebugAndroidTest/connected/coverage.ec")
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
