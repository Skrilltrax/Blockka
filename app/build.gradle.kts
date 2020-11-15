plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.squareup.sqldelight")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(30)
    buildFeatures.viewBinding = true

    defaultConfig {
        applicationId = "dev.skrilltrax.blockka"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

sqldelight {
    database("BlockkaDatabase") {
        packageName = "dev.skrilltrax.blockka.model"
        sourceFolders = listOf("sqldelight")
    }
}

dependencies {

    implementation(Dependencies.AndroidX.activityKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.material)

    implementation(Dependencies.AndroidX.Hilt.dagger)
    implementation(Dependencies.AndroidX.Hilt.hiltLifecycleViewmodel)

    implementation(Dependencies.ThirdParty.timber)
    implementation(Dependencies.ThirdParty.SQLDelight.androidDriver)
    implementation(Dependencies.ThirdParty.SQLDelight.coroutinesExtension)

    testImplementation(Dependencies.Testing.junit)
    testImplementation(Dependencies.ThirdParty.SQLDelight.jvmDriver)

    kapt(Dependencies.AndroidX.Hilt.daggerCompiler)
    kapt(Dependencies.AndroidX.Hilt.daggerHiltCompiler)
}
