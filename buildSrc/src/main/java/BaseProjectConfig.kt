/*
 * Copyright © 2014-2020 The Android Password Store Authors. All Rights Reserved.
 * SPDX-License-Identifier: GPL-3.0-only
 */

import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.api.tasks.wrapper.Wrapper
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configure root project.
 * Note that classpath dependencies still need to be defined in the `buildscript` block in the top-level build.gradle.kts file.
 */
internal fun Project.configureForRootProject() {
  // register task for cleaning the build directory in the root project
  tasks.withType<Wrapper> {
    gradleVersion = "7.0.2"
    distributionType = Wrapper.DistributionType.ALL
    distributionSha256Sum = "13bf8d3cf8eeeb5770d19741a59bde9bd966dd78d17f1bbad787a05ef19d1c2d"
  }

}

/**
 * Configure all projects including the root project
 */
internal fun Project.configureForAllProjects() {
  repositories {
    google()
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
  }
  tasks.withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_1_8.toString()
      freeCompilerArgs = freeCompilerArgs + additionalCompilerArgs
      languageVersion = "1.4"
    }
  }
  tasks.withType<Test> {
    maxParallelForks = Runtime.getRuntime().availableProcessors() * 2
    testLogging {
      events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }
  }
}

/**
 * Checks if we're building a snapshot
 */
@Suppress("UnstableApiUsage")
fun Project.isSnapshot(): Boolean {
  with(project.providers) {
    val workflow = environmentVariable("GITHUB_WORKFLOW").forUseAtConfigurationTime()
    val snapshot = environmentVariable("SNAPSHOT").forUseAtConfigurationTime()
    return workflow.isPresent && snapshot.isPresent
  }
}

/**
 * Apply configurations for app module
 */
@Suppress("UnstableApiUsage")
internal fun BaseAppModuleExtension.configureAndroidApplicationOptions(project: Project) {
  val minifySwitch =
    project.providers.environmentVariable("DISABLE_MINIFY").forUseAtConfigurationTime()

  project.tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = freeCompilerArgs + additionalCompilerArgs + listOf(
        "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
      )
    }
  }
  adbOptions.installOptions("--user 0")

  buildFeatures {
    aidl = true
    buildConfig = true
    compose = false
    viewBinding = true
  }

  buildTypes {
    named("release") {
      isMinifyEnabled = !minifySwitch.isPresent
      proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
      buildConfigField("boolean", "ENABLE_DEBUG_FEATURES", "${project.isSnapshot()}")
    }
    named("debug") {
      applicationIdSuffix = ".debug"
      versionNameSuffix = "-debug"
      isMinifyEnabled = false
      buildConfigField("boolean", "ENABLE_DEBUG_FEATURES", "true")
    }
  }
}

/**
 * Apply baseline configurations for all Android projects (Application and Library).
 */
@Suppress("UnstableApiUsage")
internal fun TestedExtension.configureCommonAndroidOptions() {
  compileSdkVersion(30)

  defaultConfig {
    minSdk = 21
    targetSdk= 30
    versionCode = 1
    versionName = "1.0"

  }

  packagingOptions {
    resources {
      excludes.add("**/*.version")
      excludes.add("**/*.txt")
      excludes.add("**/*.kotlin_module")
      excludes.add("**/plugin.properties")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  testOptions.animationsDisabled = true
}
