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
    kotlinCompilerVersion = "1.4.21"
    kotlinCompilerExtensionVersion = Dependencies.COMPOSE_VERSION
  }
}


dependencies {

  implementation(Dependencies.AndroidX.activityKtx)
  implementation(Dependencies.AndroidX.appCompat)
  implementation(Dependencies.AndroidX.coreKtx)
  implementation(Dependencies.AndroidX.fragmentKtx)
  implementation(Dependencies.AndroidX.material)
  implementation(Dependencies.AndroidX.recyclerviewSelection)

  implementation(Dependencies.AndroidX.Compose.compiler)
  implementation(Dependencies.AndroidX.Compose.runtime)

  implementation(Dependencies.AndroidX.Lifecycle.commonJava8)
  implementation(Dependencies.AndroidX.Lifecycle.process)
  implementation(Dependencies.AndroidX.Lifecycle.runtimeKtx)
  implementation(Dependencies.AndroidX.Lifecycle.viewmodelKtx)

  implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
  implementation(Dependencies.AndroidX.Navigation.uiKtx)

  implementation(Dependencies.AndroidX.Hilt.dagger)
  implementation(Dependencies.AndroidX.Hilt.hiltLifecycleViewmodel)

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
