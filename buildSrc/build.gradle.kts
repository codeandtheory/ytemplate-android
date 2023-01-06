repositories {
    mavenCentral()
    google()
}

plugins {
    `kotlin-dsl`
}
gradlePlugin {
    plugins {
        register("jacoco-reports") {
            id = "jacoco-reports"
            implementationClass = "ytemplate.android.build.JacocoTestReportPlugin"
        }

    }
}
dependencies {
    implementation("com.android.tools.build:gradle:7.3.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
    implementation("com.squareup:javapoet:1.13.0")
}