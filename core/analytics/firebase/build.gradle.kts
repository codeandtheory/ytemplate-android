plugins {
    id("ytemplate.android.firebase.test")
    id("ytemplate.android.firebase")
    id("org.jetbrains.kotlin.android")
    id("ytemplate.android.library.jacoco")
}
android {
    namespace = "ytemplate.android.core.analytics.firebase"
}

dependencies {
    implementation(project(mapOf("path" to ":core:analytics:analyticsLib")))
}
