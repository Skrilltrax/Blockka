apply(from = "buildDependencies.gradle")
val build: Map<Any, Any> by extra

plugins {
  `kotlin-dsl`
}

repositories {
  google()
  gradlePluginPortal()
  jcenter()
  mavenCentral()

}

kotlinDslPluginOptions {
  experimentalWarning.set(false)
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
  implementation(build.getValue("androidGradlePlugin"))
  implementation(build.getValue("androidGradlePlugin_builder"))
  implementation(build.getValue("androidGradlePlugin_builderModel"))
  implementation(build.getValue("androidGradlePlugin_lintModel"))
  implementation(build.getValue("hiltGradlePlugin"))
  implementation(build.getValue("kotlinGradlePlugin"))
  implementation(build.getValue("sqlDelightGradlePlugin"))
}
