plugins {
    alias(app.plugins.android.library)
    alias(app.plugins.android.kotlin)
    alias(app.plugins.android.ksp)
    alias(libs.plugins.buildconfig)
}

android {
    namespace = "com.mordecai.base"
    buildFeatures { viewBinding = true }
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
    api(app.androidx.navigation.ui.ktx)
    api(app.androidx.navigation.fragment.ktx)

    api(libs.mmkv)
    api(libs.kotlin.coroutines.core)
    api(libs.kotlin.stdlib)
    api(libs.moshi)
    api(libs.moshi.kotlin)
    api(libs.okhttp)
    api(libs.okhttp.logging.interceptor)
    api(libs.retrofit)
    api(libs.retrofit.converter.moshi)
    api(libs.koin.android)
    api(libs.koin.core)
    api(libs.koin.core.coroutines)
    api(libs.lottie)
    api(libs.luksiege.picture.selector)
    api(libs.refresh.layout.kernel)
    api(libs.refresh.header.material)
    api(libs.coil)

    // leakcanary高版本不支持在MinSdk 24以下的设备运行
    debugApi(libs.leakcanary.android)
    debugApi(libs.glance)
    testApi(app.junit.junit)
    androidTestApi(app.androidx.test.junit)
    androidTestApi(app.androidx.test.junit.ktx)
    androidTestApi(app.androidx.test.espresso.core)
}