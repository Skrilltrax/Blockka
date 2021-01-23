package dev.skrilltrax.blockka.ui.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skrilltrax.blockka.ui.compose.theme.BlockkaTheme

@Composable
fun BlockkaApp() {
  BlockkaTheme {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
      Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomDrawer() },
        floatingActionButton = { FAB() },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.End,
      ) {

      }
    }
  }
}

@Composable
fun TopBar() {
  TopAppBar(elevation = 0.dp) {
    Text(
      text = "Current Location",
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.h6,
      modifier = Modifier
        .align(Alignment.CenterVertically)
        .fillMaxWidth(),
    )
  }
}

@Composable
fun FAB() {
  FloatingActionButton(onClick = { /*TODO*/ }) {
    IconButton(onClick = {}) {
      Icon(Icons.Filled.Add)
    }
  }
}

@Composable
fun BottomDrawer() {
  BottomAppBar(cutoutShape = CircleShape) {
    IconButton(onClick = { /* doSomething() */ }) {
      Icon(Icons.Filled.Menu)
    }
    // The actions should be at the end of the BottomAppBar
  }
}

@Composable
@Preview
fun BlockkaAppPreview() {
  BlockkaApp()
}
