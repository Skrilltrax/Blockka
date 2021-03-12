package dev.skrilltrax.blockka.ui.compose.list

import androidx.compose.material.FabPosition
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.skrilltrax.blockka.ui.compose.list.common.BlockkaBottomDrawer
import dev.skrilltrax.blockka.ui.compose.list.common.BlockkaFloatingActionButton
import dev.skrilltrax.blockka.ui.compose.list.common.BlockkaNavigationSheet
import dev.skrilltrax.blockka.ui.compose.list.common.BlockkaTopAppBar
import kotlinx.coroutines.launch

@Composable
fun BlockkaListScreen() {
  // val contactViewModel: ContactViewModel = viewModel()
  val listNavController = rememberNavController()
  val currentListDestination = rememberListDestination(listNavController)

  BlockkaNavigationSheetWrapper(
    currentListDestination = currentListDestination,
    listNavController = listNavController
  )
}

@Composable
fun BlockkaNavigationSheetWrapper(
  currentListDestination: ListDestination,
  listNavController: NavHostController,
) {
  val coroutineScope = rememberCoroutineScope()
  val bottomDrawerSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
  val listActions = ListActions(listNavController)

  val onNavigationItemSelected: (ListDestination) -> Unit = {
    if (it != currentListDestination) {
      when (it) {
        ListDestination.Blocked -> listActions.blockedContactsSelected()
        ListDestination.Recent -> listActions.recentContactsSelected()
      }
    }
  }

  val onBottomDrawerClick: () -> Unit = {
    coroutineScope.launch { bottomDrawerSheetState.show() }
  }

  val onFabClick: () -> Unit = {}

  BlockkaNavigationSheet(
    currentListDestination = currentListDestination,
    modalBottomSheetState = bottomDrawerSheetState,
    onNavigationItemSelected = onNavigationItemSelected,
  ) {
    ListScreen(currentListDestination, listNavController, onBottomDrawerClick, onFabClick)
  }
}

@Composable
fun BlockkaContactSheetWrapper() {

}

@Composable
fun ListScreen(
  currentListDestination: ListDestination,
  listNavController: NavHostController,
  onBottomDrawerClick: () -> Unit,
  onFabClick: () -> Unit,
) {

  Scaffold(
    topBar = { BlockkaTopAppBar(currentListDestination) },
    bottomBar = { BlockkaBottomDrawer(onBottomDrawerClick) },
    floatingActionButton = { BlockkaFloatingActionButton(onFabClick) },
    isFloatingActionButtonDocked = true,
    floatingActionButtonPosition = FabPosition.End,
    content = { ListNavGraph(listNavController, ListDestination.startDestination) },
  )
}

@Composable
fun rememberListDestination(
  navController: NavController,
  initialDestination: ListDestination = ListDestination.startDestination
): ListDestination {
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val route = navBackStackEntry?.arguments?.get(KEY_ROUTE) ?: ListDestination.startDestination.route
  check(route is String) { "route received from navBackStackEntry is not a string" }

  return remember(route) { ListDestination.getDestinationFromRoute(route) }
}
