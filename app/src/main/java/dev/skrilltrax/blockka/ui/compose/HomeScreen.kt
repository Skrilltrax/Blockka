package dev.skrilltrax.blockka.ui.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import androidx.navigation.compose.rememberNavController
import dev.skrilltrax.blockka.ui.compose.theme.BlockkaTheme

@Composable
fun BlockkaApp() {
  val navController = rememberNavController()
  val modalBottomSheetState =
    rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val route = navBackStackEntry?.arguments?.get(KEY_ROUTE) ?: Destination.startDestination.route
  check(route is String) { "route received from navBackStackEntry is not a string" }

  val currentDestination = Destination.getDestinationFromRoute(route)

  BlockkaTheme {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
      NavigationModalSheet(
        currentDestination = currentDestination,
        modalBottomSheetState = modalBottomSheetState,
        onNavigationItemSelected = {
          navController.navigate(it.route) {
            launchSingleTop = true
            popUpTo(Destination.startDestination.route) {
              inclusive = false
            }
          }
        },
      ) {
        BlockkaScreen(navController = navController, modalBottomSheetState = modalBottomSheetState)
      }
    }
  }
}

@Composable
fun BlockkaScreen(navController: NavHostController, modalBottomSheetState: ModalBottomSheetState) {
  Scaffold(
    topBar = { TopBar(navController) },
    bottomBar = { BottomDrawer(modalBottomSheetState) },
    floatingActionButton = { FAB() },
    isFloatingActionButtonDocked = true,
    floatingActionButtonPosition = FabPosition.End,
    bodyContent = { Body(navController) },
  )
}

@Composable
fun Body(
  navController: NavHostController,
) {
  NavHost(navController = navController, startDestination = Destination.startDestination.route) {
    composable(Destination.Blocked.route) { BlockedContactsList() }
    composable(Destination.Recent.route) { RecentContactsList() }
  }
}

@Composable
fun TopBar(
  navController: NavHostController,
) {
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val route = navBackStackEntry?.arguments?.get(KEY_ROUTE) ?: Destination.startDestination.route
  check(route is String) { "route received from navBackStackEntry is not a string" }
  val destination = Destination.getDestinationFromRoute(route)

  TopAppBar(elevation = 0.dp) {
    Text(
      text = destination.displayName,
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
      Icon(Icons.Filled.Add)
  }
}

@Composable
fun BottomDrawer(modalBottomSheetState: ModalBottomSheetState) {
  BottomAppBar(cutoutShape = CircleShape) {
    IconButton(onClick = { modalBottomSheetState.show() }) {
      Icon(Icons.Filled.Menu)
    }
  }
}


@Composable
@Preview
fun BlockkaAppPreview() {
  BlockkaApp()
}
