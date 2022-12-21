plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace ="co.yml.projectsetp"
    val androidSDK = ytemplate.android.build.AndroidSdk
    compileSdk =  androidSDK.COMPILE_SDK

    defaultConfig {
        applicationId="ytemplate.android"
        minSdk = androidSDK.MIN_SDK
        targetSdk = androidSDK.TARGET_SDK
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release"){
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    implementation(androidLib.HILT_ANDROID)
    kapt(androidLib.HILT_COMPILER)
    implementation(androidLib.HILT_NAV_COMPOSE)

    testImplementation (testLib.JUNIT)
    androidTestImplementation (androidLib.JUNIT_ANDROID)
    androidTestImplementation (androidLib.ESPRESSO_TEST)
    androidTestImplementation (androidLib.COMPOSE_UI_TESTING)
    debugImplementation (androidLib.COMPOSE_TOOLING_TESTING)
    debugImplementation (androidLib.COMPOSE_UI_MANIFEST_TEST)
}