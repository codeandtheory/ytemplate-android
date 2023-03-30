plugins {
    id("org.jetbrains.kotlin.jvm")
    id("ytemplate.android.analytics")
    id("ytemplate.android.analytics.test")
    id("ytemplate.android.library.kotlin.jacoco")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}
