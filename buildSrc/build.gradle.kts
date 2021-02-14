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
  implementation(Plugins.agp)
  implementation(Plugins.agpBuilder)
  implementation(Plugins.agpBuilderModel)
  implementation(Plugins.agpLintModel)
  implementation(Plugins.hilt)
  implementation(Plugins.kotlin)
  implementation(Plugins.sqlDelight)
}
