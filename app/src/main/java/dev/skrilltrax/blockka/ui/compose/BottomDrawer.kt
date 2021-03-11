package dev.skrilltrax.blockka.ui.compose

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.skrilltrax.blockka.R
import dev.skrilltrax.blockka.ui.compose.theme.BlockkaTheme


@Composable
fun BlockkaFloatingActionButton(onClick: () -> Unit) {
  val contentDescription = stringResource(R.string.add_contact)
  val icon = Icons.Filled.Add

  DrawerFloatingActionButton(icon, contentDescription, onClick)
}

@Composable
fun DrawerFloatingActionButton(
  icon: ImageVector,
  contentDescription: String,
  onClick: () -> Unit,
) {
  FloatingActionButton(onClick = onClick) {
    Icon(icon, contentDescription)
  }
}


@Composable
fun BlockkaBottomDrawer(onClick: () -> Unit) {
  val contentDescription = stringResource(R.string.menu_button)
  val drawerIcon = Icons.Default.Menu
  val cutoutShape = CircleShape

  BottomDrawer(
    drawerIcon = drawerIcon,
    contentDescription = contentDescription,
    cutoutShape = cutoutShape,
    onClick = onClick,
  )
}

@VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
@Composable
fun BottomDrawer(
  drawerIcon: ImageVector,
  contentDescription: String,
  cutoutShape: Shape?,
  onClick: () -> Unit,
) {
  BottomAppBar(cutoutShape = cutoutShape) {
    IconButton(onClick = onClick) {
      Icon(drawerIcon, contentDescription)
    }
  }
}

@Preview
@Composable
fun BottomDrawerPreview() {
  BlockkaTheme {
    BlockkaBottomDrawer(onClick = { /*TODO*/ })
  }
}

@Preview
@Composable
fun FloatingActionButtonPreview() {
  BlockkaTheme {
    BlockkaFloatingActionButton(onClick = { /*TODO*/ })
  }
}
