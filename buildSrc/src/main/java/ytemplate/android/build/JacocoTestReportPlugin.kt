package ytemplate.android.build

import com.android.build.gradle.BaseExtension
import groovy.xml.XmlSlurper
import groovy.xml.slurpersupport.NodeChild
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.register
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import java.io.File
import java.util.*
import kotlin.math.roundToInt

class JacocoTestReportPlugin : Plugin<Project> {
    private val Project.android: BaseExtension
        get() = extensions.findByName("android") as? BaseExtension
            ?: error("Not an Android module: $name")

    private val Project.jacoco: JacocoPluginExtension
        get() = extensions.findByName("jacoco") as? JacocoPluginExtension
            ?: error("Not a Jacoco module: $name")
    private val excludedFiles = mutableSetOf(
        // data binding
        "android/databinding/**/*.class",
        "**/android/databinding/*Binding.class",
        "**/android/databinding/*",
        "**/androidx/databinding/*",
        "**/BR.*",
        // android
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        // butterKnife
        "**/*\$ViewInjector*.*",
        "**/*\$ViewBinder*.*",
        // dagger
        "**/*_MembersInjector.class",
        "**/Dagger*Component.class",
        "**/Dagger*Component\$Builder.class",
        "**/*Module_*Factory.class",
        "**/di/module/*",
        "**/*_Factory*.*",
        "**/*Module*.*",
        "**/*Dagger*.*",
        "**/*Hilt*.*",
        // kotlin
        "**/*MapperImpl*.*",
        "**/*\$ViewInjector*.*",
        "**/*\$ViewBinder*.*",
        "**/BuildConfig.*",
        "**/*Component*.*",
        "**/*BR*.*",
        "**/Manifest*.*",
        "**/*\$Lambda$*.*",
        "**/*Companion*.*",
        "**/*Module*.*",
        "**/*Dagger*.*",
        "**/*Hilt*.*",
        "**/*MembersInjector*.*",
        "**/*_MembersInjector.class",
        "**/*_Factory*.*",
        "**/*_Provide*Factory*.*",
        "**/*Extensions*.*",
        // sealed and data classes
        "**/*\$Result.*",
        "**/*\$Result$*.*"
    )

    private val limits = mutableMapOf(
        "instruction" to 0.0,
        "branch" to 0.0,
        "line" to 0.0,
        "complexity" to 0.0,
        "method" to 0.0,
        "class" to 0.0
    )


    override fun apply(target: Project) {
        with(target) {
            plugins.run {
                apply("jacoco")
            }
            extra.set("limits", limits)
            setupJacocoPlugin()
            dependencies {
                "implementation"("org.jacoco:org.jacoco.core:0.8.7")
            }
        }

    }

    private fun Project.setupJacocoPlugin() {
        val buildTypes = android.buildTypes.map { type -> type.name }
        var productFlavors = android.productFlavors.map { flavor -> flavor.name }

        if (productFlavors.isEmpty()) {
            productFlavors = productFlavors + ""
        }
        productFlavors.forEach { flavorName ->
            buildTypes.forEach { buildTypeName ->
                val sourceName: String
                val sourcePath: String
                if (flavorName.isEmpty()) {
                    sourceName = buildTypeName
                    sourcePath = buildTypeName
                } else {
                    sourceName = "${flavorName}${buildTypeName.capitalized()}"
                    sourcePath = "${flavorName}/${buildTypeName}"
                }
                val testTaskName = "test${sourceName.capitalized()}UnitTest"
                addTestCoverageTask(testTaskName, sourceName, sourcePath, flavorName, buildTypeName)
            }
        }
    }

    private fun Project.addTestCoverageTask(
        taskName: String,
        sourceName: String,
        sourcePath: String,
        flavorName: String,
        buildTypeName: String
    ) {
        tasks.register<JacocoReport>("${taskName}Coverage", ) {
            dependsOn(taskName)
            group = "Reporting"
            description = "Generate test coverage reports on the ${sourceName.capitalized()} build"
            val javaDirectories =
                fileTree("${project.buildDir}/intermediates/classes/$sourcePath") {
                    exclude(excludedFiles)
                }
            val kotlinDirectories = fileTree("${project.buildDir}/tmp/kotlin-classes/$sourcePath") {
                exclude(excludedFiles)
            }
            val coverageDirectories = listOf(
                    "src/main/java",
                    "src/$flavorName/java",
                    "src/$buildTypeName/java")

            classDirectories.setFrom(files(javaDirectories, kotlinDirectories))
            additionalClassDirs.setFrom(files(coverageDirectories))
            sourceDirectories.setFrom(files(coverageDirectories))

            reports {
                xml.required.set(true)
                html.required.set(true)
            }
            val executionDataFiles = fileTree(project.buildDir) {
                setIncludes(listOf("**/${taskName}.exec"))
            }
            executionData.setFrom(
                executionDataFiles.files
            )
            doLast {
                jacocoTestReport("${taskName}Coverage")
            }
        }
    }

    private fun Project.jacocoTestReport(taskName: String) {
        val reportDir = jacoco.reportsDirectory.asFile.get()
        val report = file("$reportDir/$taskName/${taskName}.xml")
        logger.lifecycle("Checking coverage results:$report")
        val metrics = report.extractTestCoverage()
        val limits = project.extra["limits"] as Map<String, Double>
        val failures = metrics.filter { item ->
            item.value < limits[item.key]!!
        }.map { item ->
            "-${item.key} coverage is: ${item.value}%, minimum is ${limits[item.key]}%"
        }
        if (failures.isNotEmpty()) {
            logger.quiet("======Code coverage failed=========")
            failures.forEach {
                logger.quiet(it)
            }
            logger.quiet("===========================================")
            throw  GradleException("Code coverage failed")
        } else {
            logger.quiet("======Code coverage success=========")
            metrics.forEach {
                logger.quiet("- ${it.key} coverage: ${it.value}")
            }
            logger.quiet("===========================================")
        }
    }

    private fun File.extractTestCoverage(): Map<String, Double> {
        val xmlReader = XmlSlurper().apply {
            setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
            setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
        }
        val counterNodes: List<NodeChild> = xmlReader.parse(this).parent().children()
            .filter { (it as NodeChild).name() == "counter" } as List<NodeChild>
        return counterNodes.associate { child ->
            val type = child.attributes()["type"].toString().toLowerCase(Locale.US)
            val covered = child.attributes()["covered"].toString().toDouble()
            val missed = child.attributes()["missed"].toString().toDouble()
            val percentage = ((covered / (covered + missed)) * 10000.0).roundToInt() / 100.0
            Pair(type, percentage)
        }
    }
}