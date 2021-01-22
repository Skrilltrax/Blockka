import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

/**
 * Apply default kapt configs to the [Project].
 */
internal fun KaptExtension.configureKapt() {
  javacOptions {
    option("-Adagger.fastInit=enabled")
    option("-Adagger.experimentalDaggerErrorMessages=enabled")
    option("-Xmaxerrs", 500)
    option("-Adagger.moduleBindingValidation=ERROR")
  }
}
