package dev.skrilltrax.blockka.ui.compose.list.common

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skrilltrax.blockka.ui.compose.list.ListDestination
import dev.skrilltrax.blockka.ui.compose.theme.BlockkaTheme

@Composable
fun BlockkaTopAppBar(currentListDestination: ListDestination) {
  val displayName = stringResource(currentListDestination.displayName)

  TopBar(displayName)
}

@VisibleForTesting
@Composable
fun TopBar(text: String) {
  TopAppBar(elevation = 0.dp, backgroundColor = MaterialTheme.colors.background) {
    Text(
      text = text,
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.h6,
      modifier = Modifier
        .align(Alignment.CenterVertically)
        .fillMaxWidth(),
    )
  }
}

@Composable
@Preview
fun TopAppBarPreview() {
  BlockkaTheme {
    BlockkaTopAppBar(currentListDestination = ListDestination.startDestination)
  }
}
