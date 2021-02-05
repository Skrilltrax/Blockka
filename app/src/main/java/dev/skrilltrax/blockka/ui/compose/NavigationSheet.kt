package dev.skrilltrax.blockka.ui.compose

import androidx.compose.foundation.AmbientIndication
import androidx.compose.foundation.Interaction
import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.indication
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun NavigationModalSheet(
  navController: NavHostController,
  modalBottomSheetState: ModalBottomSheetState,
  content: @Composable () -> Unit
) {

  ModalBottomSheetLayout(
    sheetContent = { NavigationSheetContent(navController) },
    sheetState = modalBottomSheetState,
    sheetShape = RoundedCornerShape(topLeft = 24.dp, topRight = 24.dp),
    sheetContentColor = MaterialTheme.colors.secondary,
    content = { content() },
  )
}

@Composable
fun NavigationSheetContent(navController: NavHostController) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 24.dp)
  ) {
    Destination.values().forEach {
      NavigationSheetItem(icon = it.icon, text = it.displayName, isSelected = true)
    }
  }
}

@Composable
fun NavigationSheetItem(
  icon: ImageVector,
  text: String,
  isSelected: Boolean,
  modifier: Modifier = Modifier
) {
  val interactionState = InteractionState().apply {
    if (isSelected) {
      addInteraction(Interaction.Pressed, Offset.Zero)
    }
  }

  Surface(
    shape = MaterialTheme.shapes.small,
    color = Color.Transparent,
    contentColor = MaterialTheme.colors.secondaryVariant,
    elevation = 0.dp,
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .selectable(
          selected = isSelected,
          onClick = { /*TODO*/ },
          interactionState = InteractionState()
        )
        .indication(interactionState, AmbientIndication.current())
        .preferredHeight(48.dp)
        .padding(horizontal = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Icon(imageVector = icon)
      Text(
        text = text,
        modifier = Modifier
          .padding(start = 16.dp)
          .fillMaxWidth(),
        style = MaterialTheme.typography.button
      )
    }
  }
}
