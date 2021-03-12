package dev.skrilltrax.blockka.ui.compose.list

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import dev.skrilltrax.blockka.R
import dev.skrilltrax.blockka.ui.compose.list.blocked.BlockedContactsList
import dev.skrilltrax.blockka.ui.compose.list.recents.RecentContactsList

enum class ListDestination(
  val route: String,
  @StringRes val displayName: Int,
  val icon: ImageVector
) {
  Blocked("blocked", R.string.blocked_contacts, Icons.Default.SupervisorAccount),
  Recent("recent", R.string.recent_contacts, Icons.Default.Schedule),
  ;

  companion object {
    val startDestination = Blocked

    fun getDestinationFromRoute(route: String): ListDestination {
      values().forEach { destination ->
        if (destination.route == route) return destination
      }

      throw IllegalArgumentException("Cannot find destination with route{$route}")
    }
  }
}


@Composable
fun ListNavGraph(
  listNavController: NavHostController,
  startListDestination: ListDestination = ListDestination.startDestination
) {
  NavHost(
    navController = listNavController,
    startDestination = ListDestination.startDestination.route
  ) {
    composable(ListDestination.Blocked.route) { BlockedContactsList() }
    composable(ListDestination.Recent.route) { RecentContactsList() }
  }
}

class ListActions(listNavController: NavHostController) {

  val blockedContactsSelected: () -> Unit = {
    listNavController.navigate(ListDestination.Blocked.route) {
      launchSingleTop = true
      popUpTo(ListDestination.startDestination.route) {
        inclusive = false
      }
    }
  }

  val recentContactsSelected: () -> Unit = {
    listNavController.navigate(ListDestination.Recent.route) {
      launchSingleTop = true
      popUpTo(ListDestination.startDestination.route) {
        inclusive = false
      }
    }
  }
}
