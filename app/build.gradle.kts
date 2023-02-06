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
    compileSdk =  versionCatalogLibs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId="ytemplate.android"
        minSdk = versionCatalogLibs.versions.min.sdk.get().toInt()
        targetSdk = versionCatalogLibs.versions.target.sdk.get().toInt()
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
        kotlinCompilerExtensionVersion  = versionCatalogLibs.versions.compose.compiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //Compose
    implementation (versionCatalogLibs.bundles.compose)
    androidTestImplementation(versionCatalogLibs.compose.nav.test)
    //ktx
    implementation (versionCatalogLibs.bundles.ktx)
    //hilt
    implementation(versionCatalogLibs.bundles.hilt)
    kapt(versionCatalogLibs.hilt.compiler)
    androidTestImplementation(versionCatalogLibs.hilt.test)
    kaptAndroidTest(versionCatalogLibs.hilt.compiler)
    //coroutine
    implementation(versionCatalogLibs.coroutine)
    testImplementation(versionCatalogLibs.bundles.coroutine.test)
    //Room
    implementation(versionCatalogLibs.bundles.room)
    annotationProcessor(versionCatalogLibs.room.compiler)
    kapt(versionCatalogLibs.room.compiler)
    testImplementation(versionCatalogLibs.room.test)
    //Ktor
    implementation (versionCatalogLibs.bundles.ktor)
    testImplementation(versionCatalogLibs.ktor.client.mock)
    androidTestImplementation(versionCatalogLibs.ktor.client.mock)
    //Android Testing
    androidTestImplementation (versionCatalogLibs.bundles.android.test)
    testImplementation (versionCatalogLibs.junit)
    debugImplementation (versionCatalogLibs.bundles.android.debug.test)

    androidTestImplementation(project(mapOf("path" to ":app")))

}