// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
  apply(from = "buildSrc/buildDependencies.gradle")
  val build: Map<Any, Any> by extra

  repositories {
    google()
    jcenter()
    mavenCentral()
  }

  dependencies {
    classpath(build.getValue("androidGradlePlugin"))
    classpath(build.getValue("hiltGradlePlugin"))
    classpath(build.getValue("kotlinGradlePlugin"))
    classpath(build.getValue("sqlDelightGradlePlugin"))

    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
  }
}

plugins {
  id("com.github.ben-manes.versions") version "0.36.0"
  `blockka-plugin`
}
