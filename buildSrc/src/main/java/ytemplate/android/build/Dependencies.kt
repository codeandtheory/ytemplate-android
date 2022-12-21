package ytemplate.android.build


object BuildPlugins {
    const val KOTLIN_VERSION = "1.7.20"
    const val PLUGIN_VERSION = "7.3.1"
    const val HILT_PLUGIN = "2.44"
    const val COMPOSE_COMPILER = "1.3.2"
}

object AndroidSdk {
    const val MIN_SDK = 26
    const val TARGET_SDK = 32
    const val COMPILE_SDK = 33
}

object AndroidLib {
    private object Version{
        const val CORE_KTX = "1.9.0"
        const val ANDROIDX_LIFE_CYCLE = "2.5.1"
        const val COMPOSE_ACTIVITY="1.6.1"
        const val COMPOSE_UI = "1.3.2"
        const val COMPOSE_MATERIAL = "1.1.1"
        const val COMPOSE_NAVIGATION = "2.5.3"
        const val ANDROID_JUNIT = "1.1.4"
        const val ESPRESSO = "3.5.0"
        const val HILT = BuildPlugins.HILT_PLUGIN
        const val HILT_NAV_COMPOSE = "1.0.0"
    }
    const val CORE_KTX = "androidx.core:core-ktx:${Version.CORE_KTX}"
    const val RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.ANDROIDX_LIFE_CYCLE}"
    const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:${Version.COMPOSE_ACTIVITY}"
    const val COMPOSE_UI = "androidx.compose.ui:ui:${Version.COMPOSE_UI}"
    const val COMPOSE_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Version.COMPOSE_UI}"
    const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Version.COMPOSE_MATERIAL}"
    const val COMPOSE_VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.ANDROIDX_LIFE_CYCLE}"
    const val COMPOSE_NAV = "androidx.navigation:navigation-compose:${Version.COMPOSE_NAVIGATION}"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Version.HILT}"
    const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Version.HILT}"
    const val HILT_NAV_COMPOSE = "androidx.hilt:hilt-navigation-compose:${Version.HILT_NAV_COMPOSE}"
    //Android Testing
    const val JUNIT_ANDROID = "androidx.test.ext:junit:${Version.ANDROID_JUNIT}"
    const val ESPRESSO_TEST = "androidx.test.espresso:espresso-core:${Version.ESPRESSO}"
    const val COMPOSE_UI_TESTING = "androidx.compose.ui:ui-test-junit4:${Version.COMPOSE_UI}"
    const val COMPOSE_TOOLING_TESTING = "androidx.compose.ui:ui-tooling:${Version.COMPOSE_UI}"
    const val COMPOSE_UI_MANIFEST_TEST=  "androidx.compose.ui:ui-test-manifest:${Version.COMPOSE_UI}"
}

object TestLib {
    private object Version{
        const val JUNIT = "4.13.2"
    }
    const val JUNIT = "junit:junit:${Version.JUNIT}"
}