package dev.skrilltrax.blockka.ui.compose

import androidx.compose.foundation.Interaction
import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp

@Composable
fun NavigationModalSheet(
  currentDestination: Destination,
  modalBottomSheetState: ModalBottomSheetState,
  onNavigationItemSelected: (Destination) -> Unit = {},
  content: @Composable () -> Unit,
) {

  ModalBottomSheetLayout(
    sheetContent = { NavigationSheetContent(currentDestination, onNavigationItemSelected) },
    sheetState = modalBottomSheetState,
    sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    sheetContentColor = MaterialTheme.colors.secondary,
    content = { content() },
  )
}

@Composable
fun NavigationSheetContent(
  currentDestination: Destination,
  onNavigationItemSelected: (Destination) -> Unit = {},
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 24.dp)
  ) {
    Destination.values().forEachIndexed { pos, destination ->
      NavigationSheetItem(
        destination = destination,
        isSelected = destination == currentDestination,
        modifier = if (pos == 0) Modifier else Modifier.padding(top = 8.dp),
        onNavigationItemSelected = onNavigationItemSelected,
      )
    }
  }
}

@Composable
fun NavigationSheetItem(
  destination: Destination,
  isSelected: Boolean,
  modifier: Modifier = Modifier,
  onNavigationItemSelected: (Destination) -> Unit = {},
) {
  val interactionState = remember { InteractionState() }

  if (isSelected) {
    interactionState.addInteraction(Interaction.Pressed, Offset.Zero)
  } else {
    interactionState.removeInteraction(Interaction.Pressed)
  }

  Surface(
    shape = MaterialTheme.shapes.small,
    color = Color.Transparent,
    contentColor = MaterialTheme.colors.secondaryVariant,
    elevation = 0.dp,
  ) {
    Row(
      modifier = modifier
        .fillMaxWidth()
        .selectable(
          selected = isSelected,
          onClick = { onNavigationItemSelected(destination) },
          interactionState = interactionState,
          indication = LocalIndication.current,
        )
        .preferredHeight(48.dp)
        .padding(horizontal = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Icon(imageVector = destination.icon, contentDescription = null)
      Text(
        text = destination.displayName.toUpperCase(Locale.current),
        modifier = Modifier.padding(start = 16.dp),
        style = MaterialTheme.typography.button
      )
    }
  }
}
