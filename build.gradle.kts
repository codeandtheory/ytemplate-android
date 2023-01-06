
plugins {
    val buildVersion = ytemplate.android.build.BuildPlugins
    id("com.google.dagger.hilt.android") version buildVersion.HILT_PLUGIN apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}
