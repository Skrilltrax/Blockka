package dev.skrilltrax.blockka.ui.compose

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.ui.graphics.vector.ImageVector
import dev.skrilltrax.blockka.R

enum class Destination(val route: String, @StringRes val displayName: Int, val icon: ImageVector) {
  Blocked("blocked", R.string.blocked_contacts, Icons.Default.SupervisorAccount),
  Recent("recent", R.string.recent_contacts, Icons.Default.Schedule),
  ;

  companion object {
    val startDestination = Blocked

    fun getDestinationFromRoute(route: String): Destination {
      values().forEach { destination ->
        if (destination.route == route) return destination
      }

      throw IllegalArgumentException("Cannot find destination with route{$route}")
    }
  }
}
