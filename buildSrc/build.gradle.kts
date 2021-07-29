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
  implementation("com.android.tools.build:gradle:7.1.0-alpha04")
  implementation("com.google.dagger:hilt-android-gradle-plugin:2.37")
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
  implementation("com.squareup.sqldelight:gradle-plugin:1.5.1")
}
