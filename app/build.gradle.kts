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
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  composeOptions {
    kotlinCompilerExtensionVersion = Dependencies.COMPOSE_VERSION
  }
}


dependencies {

  implementation(Dependencies.AndroidX.activityCompose)
  implementation(Dependencies.AndroidX.activityKtx)
  implementation(Dependencies.AndroidX.appCompat)
  implementation(Dependencies.AndroidX.coreKtx)
  implementation(Dependencies.AndroidX.fragmentKtx)
  implementation(Dependencies.AndroidX.material)
  implementation(Dependencies.AndroidX.recyclerviewSelection)

  implementation(Dependencies.AndroidX.Compose.compiler)
  implementation(Dependencies.AndroidX.Compose.material)
  implementation(Dependencies.AndroidX.Compose.materialIconsCore)
  implementation(Dependencies.AndroidX.Compose.materialIconsExtended)
  implementation(Dependencies.AndroidX.Compose.runtime)
  implementation(Dependencies.AndroidX.Compose.ui)
  implementation(Dependencies.AndroidX.Compose.uiTooling)

  implementation(Dependencies.AndroidX.Lifecycle.commonJava8)
  implementation(Dependencies.AndroidX.Lifecycle.process)
  implementation(Dependencies.AndroidX.Lifecycle.runtimeKtx)
  implementation(Dependencies.AndroidX.Lifecycle.viewmodelKtx)

  implementation(Dependencies.AndroidX.Navigation.compose)
  implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
  implementation(Dependencies.AndroidX.Navigation.uiKtx)

  implementation(Dependencies.AndroidX.Hilt.dagger)
  implementation(Dependencies.AndroidX.Hilt.hiltLifecycleViewmodel)
  implementation(Dependencies.AndroidX.Hilt.hiltNavigation)

  implementation(Dependencies.ThirdParty.accompanist)
  implementation(Dependencies.ThirdParty.coil)
  implementation(Dependencies.ThirdParty.timber)
  implementation(Dependencies.ThirdParty.SQLDelight.androidDriver)
  implementation(Dependencies.ThirdParty.SQLDelight.coroutinesExtension)

  testImplementation(Dependencies.Testing.junit)
  testImplementation(Dependencies.Testing.AndroidX.runner)
  testImplementation(Dependencies.ThirdParty.SQLDelight.jvmDriver)

  androidTestImplementation(Dependencies.Testing.AndroidX.Ext.junit)

  kapt(Dependencies.AndroidX.Hilt.daggerCompiler)
  kapt(Dependencies.AndroidX.Hilt.daggerHiltCompiler)
}
