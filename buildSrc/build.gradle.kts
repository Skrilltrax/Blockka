plugins {
  `kotlin-dsl`
}

repositories {
  google()
  gradlePluginPortal()
  mavenCentral()
}

gradlePlugin {
  plugins {
    register("blockka") {
      id = "blockka-plugin"
      implementationClass = "BlockkaPlugin"
    }
  }
}

dependencies {
  implementation("com.android.tools.build:gradle:7.0.0-alpha15")
  implementation("com.google.dagger:hilt-android-gradle-plugin:2.35.1")
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
  implementation("com.squareup.sqldelight:gradle-plugin:1.5.0")
}
