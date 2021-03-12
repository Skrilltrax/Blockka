package dev.skrilltrax.blockka.ui.compose.list.common

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.skrilltrax.blockka.ui.compose.list.ListDestination
import dev.skrilltrax.blockka.ui.compose.theme.BlockkaTheme
import java.util.Locale

@Composable
fun BlockkaNavigationSheet(
  currentListDestination: ListDestination,
  modalBottomSheetState: ModalBottomSheetState,
  onNavigationItemSelected: (ListDestination) -> Unit = {},
  content: @Composable () -> Unit,
) {

  ModalBottomSheetLayout(
    sheetContent = { NavigationSheetContent(currentListDestination, onNavigationItemSelected) },
    sheetState = modalBottomSheetState,
    sheetContentColor = MaterialTheme.colors.secondary,
    content = { content() },
  )
}

@Composable
fun NavigationSheetContent(
  currentListDestination: ListDestination,
  onNavigationItemSelected: (ListDestination) -> Unit,
) {
  Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
    ListDestination.values().forEach { destination ->
      NavigationSheetItem(
        listDestination = destination,
        isSelected = destination == currentListDestination,
        onNavigationItemSelected = onNavigationItemSelected,
      )
      Spacer(modifier = Modifier.height(8.dp))
    }
  }
}

@Composable
fun NavigationSheetItem(
  listDestination: ListDestination,
  isSelected: Boolean,
  onNavigationItemSelected: (ListDestination) -> Unit,
) {
  val interactionSource = remember { MutableInteractionSource() }
  val pressInteraction = remember { PressInteraction.Press(Offset.Zero) }

  if (isSelected) {
    interactionSource.tryEmit(pressInteraction)
  } else {
    interactionSource.tryEmit(PressInteraction.Release(pressInteraction))
  }

  val surfaceColor = Color.Transparent
  val contentColor = MaterialTheme.colors.secondaryVariant

  NavigationSheetItemUI(
    text = stringResource(id = listDestination.displayName).toUpperCase(Locale.getDefault()),
    icon = listDestination.icon,
    surfaceColor = surfaceColor,
    contentColor = contentColor,
    interactionSource = interactionSource,
    onClick = { onNavigationItemSelected(listDestination) }
  )
}

@VisibleForTesting
@Composable
fun NavigationSheetItemUI(
  text: String,
  icon: ImageVector,
  surfaceColor: Color,
  contentColor: Color,
  interactionSource: InteractionSource,
  onClick: () -> Unit,
) {
  val surfaceIndicatorModifier = Modifier
    .fillMaxWidth()
    .height(48.dp)
    .clip(MaterialTheme.shapes.small)
    .indication(
      interactionSource = interactionSource,
      indication = LocalIndication.current,
    )

  Surface(
    color = surfaceColor,
    contentColor = contentColor,
    modifier = surfaceIndicatorModifier
  ) {
    Row(
      modifier = Modifier.clickable(
        onClick = onClick,
        interactionSource = MutableInteractionSource(),
        indication = null,
      ),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = Modifier.padding(horizontal = 8.dp)
      )
      Text(
        text = text,
        modifier = Modifier.padding(horizontal = 8.dp),
        style = MaterialTheme.typography.button,
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewNavigationSheet() {
  BlockkaTheme {
    BlockkaNavigationSheet(
      currentListDestination = ListDestination.startDestination,
      modalBottomSheetState = ModalBottomSheetState(ModalBottomSheetValue.HalfExpanded),
      onNavigationItemSelected = {},
      content = {},
    )
  }
}
