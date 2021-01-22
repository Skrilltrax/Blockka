import com.squareup.sqldelight.gradle.SqlDelightExtension

internal fun SqlDelightExtension.configureDatabase() {
  database("BlockkaDatabase") {
    packageName = "dev.skrilltrax.blockka.model"
    sourceFolders = listOf("sqldelight")
  }
}
