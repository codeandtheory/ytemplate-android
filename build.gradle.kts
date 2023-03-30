import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
    dependencies {
        classpath(versionCatalogLibs.android.gradle.plugin)
        classpath(versionCatalogLibs.kotlin.gradle.plugin)
        classpath(versionCatalogLibs.jacoco.core)
    }
}
@Suppress("DSL_SCOPE_VIOLATION") // scope violation issue: work around suggested from: https://github.com/gradle/gradle/issues/22797
plugins {
    alias(versionCatalogLibs.plugins.kotlin.serialization) apply false
    alias(versionCatalogLibs.plugins.hilt) apply false
    alias(versionCatalogLibs.plugins.ksp) apply false
    alias(versionCatalogLibs.plugins.sonar)
    alias(versionCatalogLibs.plugins.dokka)
    alias(versionCatalogLibs.plugins.ktlint)
    id("ytemplate.android.project.jacoco")
}

sonarqube {
    val sonarProperties = readProperties(file("sonar-project.properties"))
    properties {
        property("sonar.projectKey", sonarProperties["sonar.projectKey"].toString())
        property("sonar.organization", sonarProperties["sonar.organization"].toString())
        property("sonar.host.url", sonarProperties["sonar.host.url"].toString())
        // Enable this property for local testing
        property("sonar.login", sonarProperties["sonar.login"].toString())
    }
}
subprojects {
    apply(plugin = "org.sonarqube")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
ktlint {
    version.set("0.48.2") // todo refer from version catalogs
    debug.set(true)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    reporters {
        setOf(
            org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML,
            org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE
        )
    }
    ignoreFailures.set(true)
    kotlinScriptAdditionalPaths {
        include(fileTree("scripts/"))
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

fun readProperties(propertiesFile: File) = java.util.Properties().apply {
    propertiesFile.inputStream().use { fis ->
        load(fis)
    }
}
