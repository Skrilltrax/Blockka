/*
 * Copyright © 2014-2020 The Android Password Store Authors. All Rights Reserved.
 * SPDX-License-Identifier: GPL-3.0-only
 */

import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.internal.plugins.AppPlugin
import com.android.build.gradle.internal.plugins.LibraryPlugin
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import com.squareup.sqldelight.gradle.SqlDelightExtension
import com.squareup.sqldelight.gradle.SqlDelightPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.KaptExtension




class BlockkaPlugin : Plugin<Project> {

  override fun apply(project: Project) {
    project.configureForAllProjects()

    if (project.isRoot) {
      project.configureForRootProject()
    }

    project.plugins.all {
      when (this) {
        is JavaPlugin,
        is JavaLibraryPlugin -> {
          project.tasks.withType<JavaCompile> {
            options.compilerArgs.add("-Xlint:unchecked")
            options.isDeprecation = true
          }
        }
        is LibraryPlugin -> {
          project.extensions.getByType<TestedExtension>().configureCommonAndroidOptions()
          project.extensions.getByType<TestedExtension>().registerSourcesJarTask(project)
          project.afterEvaluate {
            project.extensions.getByType<PublishingExtension>().configureMavenPublication(project)
          }
        }
        is AppPlugin -> {
          project.extensions.getByType<BaseAppModuleExtension>()
            .configureAndroidApplicationOptions(project)
          project.extensions.getByType<BaseAppModuleExtension>().configureBuildSigning(project)
          project.extensions.getByType<TestedExtension>().configureCommonAndroidOptions()
        }
        is Kapt3GradleSubplugin -> {
          project.extensions.getByType<KaptExtension>().configureKapt()
        }
        is SpotlessPlugin -> {
          project.extensions.getByType<SpotlessExtension>().configureSpotless()
        }
        is SqlDelightPlugin -> {
          project.extensions.getByType<SqlDelightExtension>().configureDatabase()
        }
      }
    }
  }
}

private val Project.isRoot get() = this == this.rootProject
