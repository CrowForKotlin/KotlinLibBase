plugins {
    alias(app.plugins.android.library)
    alias(app.plugins.android.kotlin)
    alias(app.plugins.android.ksp)
    alias(libs.plugins.buildconfig)
    alias(app.plugins.kotlin.compose.compiler)
}

android {
    namespace = "com.mordecai.base.compose"
    buildFeatures {
        viewBinding = true
        compose = true
    }
    defaultConfig {
        compileSdk = 35
        minSdk = 23
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
    sourceSets.named("main") {
        java.srcDirs("src/main/java")
        kotlin.srcDir("src/main/kotlin")
        jniLibs.srcDirs("libs", "jniLibs")
    }
    composeOptions { kotlinCompilerExtensionVersion = compose.versions.compiler.get() }
    composeCompiler { enableStrongSkippingMode = true }
}

dependencies {

    api(fileTree("dir" to "libs", "include" to "*.jar"))

    api(app.androidx.core)
    api(app.androidx.activity)
    api(app.androidx.appcompat)
    api(app.androidx.material)
    api(app.androidx.constraintlayout)
    api(app.androidx.lifecycle.service)
    api(app.androidx.lifecycle.runtime.ktx)
    api(app.androidx.lifecycle.livedata.ktx)
    api(app.androidx.lifecycle.viewmodel.ktx)
    api(app.androidx.datastore.preferences)
    api(app.androidx.paging.runtime.ktx)
    api(app.androidx.paging.common.ktx)
    api(app.androidx.core.splashscreen)
    api(app.androidx.room.ktx)
    api(app.androidx.room.runtime)
    api(app.androidx.swiperefreshlayout)

    api(compose.androidx.activity)
    api(compose.androidx.material3)
    api(compose.androidx.material)
    api(compose.androidx.ui.tooling)
    api(compose.androidx.ui.util)
    api(compose.androidx.material.icons.extended)
    api(compose.accompanist.systemuicontroller)

    // leakcanary高版本不支持在MinSdk 24以下的设备运行
    debugApi(libs.leakcanary.android)
    debugApi(libs.glance)
    testApi(app.junit.junit)
    androidTestApi(app.androidx.test.junit)
    androidTestApi(app.androidx.test.junit.ktx)
    androidTestApi(app.androidx.test.espresso.core)
}