plugins {
    alias(app.plugins.android.application)
    alias(app.plugins.android.ksp)
    alias(app.plugins.android.kotlin)
}

android {
    namespace = "com.mordecai.base.test"
    buildFeatures(Action {
        buildConfig = true
        viewBinding = true
    })
    defaultConfig {
        compileSdk = 35
        buildToolsVersion = "35.0.0"
        applicationId = "com.mordecai.base.test"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            setProperty("archivesBaseName", "KotlinLibBase")
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
    productFlavors {
        flavorDimensions.add("version")
        create("online") {
            dimension = "version"
            versionNameSuffix = "_online"
            buildConfigField("boolean", "IS_ONLINE_ENV", "true")
        }
        create("dev") {
            dimension = "version"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "_dev"
            buildConfigField("boolean", "IS_ONLINE_ENV", "false")
        }
    }
}

dependencies {
    implementation(project(":lib_base"))
    implementation(app.androidx.multidex)
}