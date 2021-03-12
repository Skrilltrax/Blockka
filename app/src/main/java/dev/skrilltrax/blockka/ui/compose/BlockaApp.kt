package dev.skrilltrax.blockka.ui.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.skrilltrax.blockka.ui.compose.theme.BlockkaTheme

@Composable
fun BlockkaApp() {
  BlockkaTheme {
    Surface(color = MaterialTheme.colors.background) {
      NavGraph()
    }
  }
}

@Composable
@Preview
fun BlockkaAppPreview() {
  BlockkaApp()
}
