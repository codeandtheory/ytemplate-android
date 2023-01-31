plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("jacoco-reports")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace ="ytemplate.android"
    val androidSDK = ytemplate.android.build.AndroidSdk
    compileSdk =  androidSDK.COMPILE_SDK

    defaultConfig {
        applicationId="ytemplate.android"
        minSdk = androidSDK.MIN_SDK
        targetSdk = androidSDK.TARGET_SDK
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "ytemplate.android.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release"){
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled  = false
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose =  true
    }
    composeOptions {
        kotlinCompilerExtensionVersion  = ytemplate.android.build.BuildPlugins.COMPOSE_COMPILER
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    androidTestImplementation(project(mapOf("path" to ":app")))
    val androidLib = ytemplate.android.build.AndroidLib
    val testLib = ytemplate.android.build.TestLib
    implementation (androidLib.CORE_KTX)
    implementation (androidLib.RUNTIME_KTX)

    implementation (androidLib.COMPOSE_ACTIVITY)
    implementation (androidLib.COMPOSE_UI)
    implementation (androidLib.COMPOSE_PREVIEW)
    implementation (androidLib.COMPOSE_MATERIAL)
    implementation(androidLib.COMPOSE_VIEW_MODEL)
    implementation(androidLib.COMPOSE_NAV)
    androidTestImplementation(androidLib.COMPOSE_NAV_TEST)
    implementation(androidLib.HILT_ANDROID)
    kapt(androidLib.HILT_COMPILER)
    implementation(androidLib.HILT_NAV_COMPOSE)
    //Hilt instrumented test
    androidTestImplementation(androidLib.HILT_TEST)
    kaptAndroidTest(androidLib.HILT_COMPILER)

    //coroutine
    implementation(androidLib.COROUTINE)
    testImplementation(androidLib.COROUTINE_TEST)
    testImplementation(androidLib.COROUTINE_TURBINE)
    //Room
    implementation(androidLib.ROOM_RUNTIME)
    annotationProcessor(androidLib.ROOM_COMPILER)
    kapt(androidLib.ROOM_COMPILER)
    implementation(androidLib.ROOM_KTX)
    testImplementation(androidLib.ROOM_TEST)
    //Ktor
    implementation(androidLib.KTOR_CORE)
    implementation(androidLib.KTOR_CLIENT)
    implementation(androidLib.KTOR_CONTENT_NEGOTIATION)
    implementation(androidLib.KTOR_SERILIZATION)
    implementation(androidLib.KTOR_CLIENT_LOGGING)
    implementation(androidLib.KOTLINX_SERILIZATION)
    testImplementation(androidLib.KTOR_CLIENT_MOCK)
    androidTestImplementation(androidLib.KTOR_CLIENT_MOCK)

    testImplementation (testLib.JUNIT)
    androidTestImplementation (androidLib.JUNIT_ANDROID)
    androidTestImplementation (androidLib.ESPRESSO_TEST)
    androidTestImplementation (androidLib.COMPOSE_UI_TESTING)
    debugImplementation (androidLib.COMPOSE_TOOLING_TESTING)
    debugImplementation (androidLib.COMPOSE_UI_MANIFEST_TEST)

}