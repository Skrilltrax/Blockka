// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
  val kotlin_version by extra("1.4.21")
  val compose_version by extra("1.0.0-alpha10")

  repositories {
    google()
    jcenter()
    mavenCentral()
  }

  dependencies {
    classpath(Plugins.agp)
    classpath(Plugins.kotlin)
    classpath(Plugins.hilt)
    classpath(Plugins.sqldelight)
    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
  }
}

plugins {
  id("com.diffplug.spotless") version "5.9.0"
  id("com.github.ben-manes.versions") version "0.36.0"

}

spotless {
  kotlin {
    target("**/*.kt")
    trimTrailingWhitespace()
    indentWithSpaces()
    endWithNewline()
  }
  kotlinGradle {
    target("**/*.gradle.kts")
    trimTrailingWhitespace()
    indentWithSpaces()
    endWithNewline()
  }
}

allprojects {
  repositories {
    google()
    jcenter()
    maven("https://www.jitpack.io")
  }
}
