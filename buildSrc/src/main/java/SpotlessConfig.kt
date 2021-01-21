import com.diffplug.gradle.spotless.KotlinExtension
import com.diffplug.gradle.spotless.KotlinGradleExtension
import com.diffplug.gradle.spotless.SpotlessExtension

internal fun SpotlessExtension.configureSpotless() {
  kotlin {
    configureKotlinFiles()
  }
  kotlinGradle {
    configureGradleFiles()
  }
}

internal fun KotlinGradleExtension.configureGradleFiles() {
  target("**/*.gradle.kts")
  trimTrailingWhitespace()
  indentWithSpaces()
  endWithNewline()
}

internal fun KotlinExtension.configureKotlinFiles() {
  target("**/*.kt")
  trimTrailingWhitespace()
  indentWithSpaces()
  endWithNewline()
}
