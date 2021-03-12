package dev.skrilltrax.blockka.ui.compose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.skrilltrax.blockka.ui.compose.list.BlockkaListScreen

object Destination {
  const val ListRoute = "DestinationListRoute"
  const val DetailRoute = "DestinationDetailRoute"
  const val SettingsRoute = "DestinationSettingsRoute"
}

@Composable
fun NavGraph(startDestination: String = Destination.ListRoute) {
  val navController = rememberNavController()


  NavHost(navController = navController, startDestination = startDestination) {
    composable(Destination.ListRoute) {
      BlockkaListScreen()
    }

    composable(Destination.DetailRoute) {

    }

    composable(Destination.SettingsRoute) {

    }
  }
}
