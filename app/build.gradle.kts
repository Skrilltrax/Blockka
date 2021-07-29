plugins {
  id("com.android.application")
  id("dagger.hilt.android.plugin")
  id("com.squareup.sqldelight")
  kotlin("android")
  kotlin("kapt")
  `blockka-plugin`
}

android {
  defaultConfig {
    applicationId = "dev.skrilltrax.blockka"
  }
}

dependencies {

  implementation(libs.androidx.activityKtx)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.coreKtx)
  implementation(libs.androidx.fragmentKtx)
  implementation(libs.androidx.material)
  implementation(libs.androidx.recyclerviewSelection)

  implementation(libs.androidx.lifecycle.common)
  implementation(libs.androidx.lifecycle.runtimeKtx)
  implementation(libs.androidx.lifecycle.viewmodelKtx)

  implementation(libs.androidx.navigation.fragmentKtx)
  implementation(libs.androidx.navigation.uiKtx)

  implementation(libs.androidx.hilt.dagger)

  implementation(libs.thirdparty.coil)
  implementation(libs.thirdparty.timber)
  implementation(libs.thirdparty.sqldelight.androidDriver)
  implementation(libs.thirdparty.sqldelight.coroutinesExtension)

  testImplementation(libs.testing.junit)
  testImplementation(libs.thirdparty.sqldelight.jvmDriver)

  androidTestImplementation(libs.bundles.androidTestDependencies)

  kapt(libs.androidx.hilt.daggerCompiler)
}
