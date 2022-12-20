plugins {
    val buildVersion = ytemplate.android.build.BuildPlugins
    id ("com.android.application") version buildVersion.PLUGIN_VERSION apply false
    id ("com.android.library") version buildVersion.PLUGIN_VERSION apply false
    id ("org.jetbrains.kotlin.android") version buildVersion.KOTLIN_VERSION apply false
}