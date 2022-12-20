package ytemplate.android.build


object BuildPlugins {
    const val KOTLIN_VERSION = "1.6.10"
    const val PLUGIN_VERSION = "7.3.0"
}

object AndroidSdk {
    const val MIN_SDK = 26
    const val TARGET_SDK = 32
    const val COMPILE_SDK = 32
}

object AndroidLib {
    private object Version{
        const val CORE_KTX = "1.7.0"
        const val LIFE_CYCLE_RUNTIME_KTX = "2.3.1"
        const val COMPOSE_ACTIVITY="1.3.1"
        const val COMPOSE_UI = "1.1.1"
        const val COMPOSE_MATERIAL = "1.1.1"
        const val ANDROID_JUNIT = "1.1.4"
        const val ESPRESSO = "3.5.0"

    }
    const val CORE_KTX = "androidx.core:core-ktx:${Version.CORE_KTX}"
    const val RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.LIFE_CYCLE_RUNTIME_KTX}"
    const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:${Version.COMPOSE_ACTIVITY}"
    const val COMPOSE_UI = "androidx.compose.ui:ui:${Version.COMPOSE_UI}"
    const val COMPOSE_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Version.COMPOSE_UI}"
    const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Version.COMPOSE_MATERIAL}"

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