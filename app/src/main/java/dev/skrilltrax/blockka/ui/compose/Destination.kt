package dev.skrilltrax.blockka.ui.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination(val route: String, val displayName: String, val icon: ImageVector) {
  Blocked("blocked", "Blocked Contacts", Icons.Default.SupervisorAccount),
  Recent("recent", "Recent Contacts", Icons.Default.Schedule),
  ;

  companion object {
    val startDestination = Blocked

    fun getDestinationFromRoute(route: String): Destination {
      Destination.values().forEach { destination ->
        if (destination.route == route) return destination
      }

      throw IllegalArgumentException("Cannot find destination with route{$route}")
    }
  }
}
