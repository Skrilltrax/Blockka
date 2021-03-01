package dev.skrilltrax.blockka.ui.compose

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skrilltrax.blockka.ui.compose.theme.BlockkaTheme

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
        onNavigationItemSelected = onNavigationItemSelected,
      )
      Spacer(
        modifier = Modifier
          .fillMaxWidth()
          .height(8.dp)
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
  val interactionSource = remember { MutableInteractionSource() }
  val pressInteraction = remember { PressInteraction.Press(Offset.Zero) }

  if (isSelected) {
    interactionSource.tryEmit(pressInteraction)
  } else {
    interactionSource.tryEmit(PressInteraction.Release(pressInteraction))
  }

  Surface(
    shape = MaterialTheme.shapes.small,
    color = Color.Transparent,
    contentColor = MaterialTheme.colors.secondaryVariant,
    modifier = Modifier.indication(
      interactionSource = interactionSource,
      indication = LocalIndication.current,
    )
  ) {
    Row(
      modifier = modifier
        .fillMaxWidth()
        .clickable(
          onClick = { onNavigationItemSelected(destination) },
          interactionSource = MutableInteractionSource(),
          indication = null
        )
        .height(48.dp)
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

@Preview(showBackground = true)
@Composable
fun PreviewNavigationSheet() {
  BlockkaTheme {
    NavigationModalSheet(
      currentDestination = Destination.startDestination,
      ModalBottomSheetState(ModalBottomSheetValue.HalfExpanded),
      {},
      {})
  }
}
