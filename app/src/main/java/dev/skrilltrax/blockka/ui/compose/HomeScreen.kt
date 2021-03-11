package dev.skrilltrax.blockka.ui.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import androidx.navigation.compose.rememberNavController
import dev.skrilltrax.blockka.ui.compose.theme.BlockkaTheme
import dev.skrilltrax.blockka.ui.viewmodel.ContactViewModel
import kotlinx.coroutines.launch

@Composable
fun BlockkaApp() {
  val navController = rememberNavController()
  val bottomDrawerSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
  val contactViewModel: ContactViewModel = viewModel()

  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val route = navBackStackEntry?.arguments?.get(KEY_ROUTE) ?: Destination.startDestination.route
  check(route is String) { "route received from navBackStackEntry is not a string" }

  val currentDestination = Destination.getDestinationFromRoute(route)
  val onNavigationItemSelected: (Destination) -> Unit = {
    if (it != currentDestination) {
      navController.navigate(it.route) {
        launchSingleTop = true
        popUpTo(Destination.startDestination.route) {
          inclusive = false
        }
      }
    }
  }

  BlockkaTheme {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
      NavigationModalSheet(
        currentDestination = currentDestination,
        modalBottomSheetState = bottomDrawerSheetState,
        onNavigationItemSelected = onNavigationItemSelected,
      ) {
        BlockkaScreen(
          currentDestination = currentDestination,
          navController = navController,
          bottomDrawerSheetState = bottomDrawerSheetState
        )
      }
    }
  }
}

@Composable
fun BlockkaScreen(
  currentDestination: Destination,
  navController: NavHostController,
  bottomDrawerSheetState: ModalBottomSheetState
) {
  val coroutineScope = rememberCoroutineScope()
  val onBottomDrawerClick: () -> Unit = {
    coroutineScope.launch { bottomDrawerSheetState.show() }
  }
  val onFabClick: () -> Unit = {}

  Scaffold(
    topBar = { TopBar(currentDestination) },
    bottomBar = { BlockkaBottomDrawer(onBottomDrawerClick) },
    floatingActionButton = { BlockkaFloatingActionButton(onFabClick) },
    isFloatingActionButtonDocked = true,
    floatingActionButtonPosition = FabPosition.End,
    content = { Body(navController) },
  )
}

@Composable
fun Body(navController: NavHostController) {
  NavHost(navController = navController, startDestination = Destination.startDestination.route) {
    composable(Destination.Blocked.route) { BlockedContactsList() }
    composable(Destination.Recent.route) { RecentContactsList() }
  }
}

@Composable
fun TopBar(currentDestination: Destination) {
  TopAppBar(elevation = 0.dp, backgroundColor = MaterialTheme.colors.background) {
    Text(
      text = stringResource(currentDestination.displayName),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.h6,
      modifier = Modifier
        .align(Alignment.CenterVertically)
        .fillMaxWidth(),
    )
  }
}

@Composable
@Preview
fun BlockkaAppPreview() {
  BlockkaApp()
}
