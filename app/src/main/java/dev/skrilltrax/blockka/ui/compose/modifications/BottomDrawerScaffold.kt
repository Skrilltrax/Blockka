//package dev.skrilltrax.blockka.ui.compose
//
///*
// * Copyright 2019 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//import androidx.compose.foundation.layout.ColumnScope
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.material.BackdropScaffold
//import androidx.compose.material.BottomAppBar
//import androidx.compose.material.BottomDrawerLayout
//import androidx.compose.material.BottomDrawerState
//import androidx.compose.material.BottomDrawerValue
//import androidx.compose.material.BottomSheetScaffold
//import androidx.compose.material.DrawerDefaults
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.FloatingActionButton
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Snackbar
//import androidx.compose.material.SnackbarHost
//import androidx.compose.material.SnackbarHostState
//import androidx.compose.material.Surface
//import androidx.compose.material.TopAppBar
//import androidx.compose.material.contentColorFor
//import androidx.compose.material.rememberBottomDrawerState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.Immutable
//import androidx.compose.runtime.Providers
//import androidx.compose.runtime.Stable
//import androidx.compose.runtime.emptyContent
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.staticAmbientOf
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.layout.SubcomposeLayout
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.LayoutDirection
//import androidx.compose.ui.unit.dp
//
///**
// * State for [BottomDrawerScaffold] composable component.
// *
// * Contains basic screen state, e.g. Drawer configuration, as well as sizes of components after
// * layout has happened
// *
// * @param drawerState the drawer state
// * @param snackbarHostState instance of [SnackbarHostState] to be used to show [Snackbar]s
// * inside of the [BlockkaScreen]
// */
//@Stable
//@OptIn(ExperimentalMaterialApi::class)
//class BottomDrawerScaffoldState(
//  val drawerState: BottomDrawerState,
//  val snackbarHostState: SnackbarHostState
//)
//
///**
// * Creates a [BottomDrawerScaffoldState] with the default animation clock and memoizes it.
// *
// * @param drawerState the drawer state
// * @param snackbarHostState instance of [SnackbarHostState] to be used to show [Snackbar]s
// * inside of the [BlockkaScreen]
// */
//@Composable
//@OptIn(ExperimentalMaterialApi::class)
//fun rememberBottomDrawerScaffoldState(
//  drawerState: BottomDrawerState = rememberBottomDrawerState(BottomDrawerValue.Closed),
//  snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
//): BottomDrawerScaffoldState = remember {
//  BottomDrawerScaffoldState(drawerState, snackbarHostState)
//}
//
///**
// * The possible positions for a [FloatingActionButton] attached to a [BottomDrawerScaffold].
// */
//enum class FabPosition {
//  /**
//   * Position FAB at the bottom of the screen in the center, above the [BottomAppBar] (if it
//   * exists)
//   */
//
//  Center,
//
//  /**
//   * Position FAB at the bottom of the screen at the end, above the [BottomAppBar] (if it
//   * exists)
//   */
//  End
//}
//
///**
// * Scaffold implements the basic material design visual layout structure.
// *
// * This component provides API to put together several material components to construct your
// * screen, by ensuring proper layout strategy for them and collecting necessary data so these
// * components will work together correctly.
// *
// * For similar components that implement different layout structures, see [BackdropScaffold],
// * which uses a backdrop as the centerpiece of the screen, and [BottomSheetScaffold], which uses
// * a persistent bottom sheet as the centerpiece of the screen.
// *
// * Simple example of a Scaffold with [TopAppBar], [FloatingActionButton] and drawer:
// *
// * @sample androidx.compose.material.samples.SimpleScaffoldWithTopBar
// *
// * More fancy usage with [BottomAppBar] with cutout and docked [FloatingActionButton], which
// * animates it's shape when clicked:
// *
// * @sample androidx.compose.material.samples.ScaffoldWithBottomBarAndCutout
// *
// * To show a [Snackbar], use [SnackbarHostState.showSnackbar]. Scaffold state already
// * have [BottomDrawerScaffoldState.snackbarHostState] when created
// *
// * @sample androidx.compose.material.samples.ScaffoldWithSimpleSnackbar
// *
// * @param modifier optional Modifier for the root of the [BlockkaScreen]
// * @param bottomDrawerScaffoldState state of this scaffold widget. It contains the state of the screen, e.g.
// * variables to provide manual control over the drawer behavior, sizes of components, etc
// * @param topBar top app bar of the screen. Consider using [TopAppBar].
// * @param bottomBar bottom bar of the screen. Consider using [BottomAppBar].
// * @param snackbarHost component to host [Snackbar]s that are pushed to be shown via
// * [SnackbarHostState.showSnackbar]. Usually it's a [SnackbarHost]
// * @param floatingActionButton Main action button of your screen. Consider using
// * [FloatingActionButton] for this slot.
// * @param floatingActionButtonPosition position of the FAB on the screen. See [FabPosition] for
// * possible options available.
// * @param isFloatingActionButtonDocked whether [floatingActionButton] should overlap with
// * [bottomBar] for half a height, if [bottomBar] exists. Ignored if there's no [bottomBar] or no
// * [floatingActionButton].
// * @param drawerContent content of the Drawer sheet that can be pulled from the left side (right
// * for RTL).
// * @param drawerGesturesEnabled whether or not drawer (if set) can be interacted with via gestures
// * @param drawerShape shape of the drawer sheet (if set)
// * @param drawerElevation drawer sheet elevation. This controls the size of the shadow
// * below the drawer sheet (if set)
// * @param drawerBackgroundColor background color to be used for the drawer sheet
// * @param drawerContentColor color of the content to use inside the drawer sheet. Defaults to
// * either the matching `onFoo` color for [drawerBackgroundColor], or, if it is not a color from
// * the theme, this will keep the same value set above this Surface.
// * @param drawerScrimColor color of the scrim that obscures content when the drawer is open
// * @param backgroundColor background of the scaffold body
// * @param contentColor color of the content in scaffold body. Defaults to either the matching
// * `onFoo` color for [backgroundColor], or, if it is not a color from the theme, this will keep
// * the same value set above this Surface.
// * @param bodyContent content of your screen. The lambda receives an [PaddingValues] that should be
// * applied to the content root via Modifier.padding to properly offset top and bottom bars. If
// * you're using VerticalScroller, apply this modifier to the child of the scroller, and not on
// * the scroller itself.
// */
//@Composable
//@OptIn(ExperimentalMaterialApi::class)
//fun BottomDrawerScaffold(
//  modifier: Modifier = Modifier,
//  bottomDrawerScaffoldState: BottomDrawerScaffoldState = rememberBottomDrawerScaffoldState(),
//  topBar: @Composable () -> Unit = emptyContent(),
//  bottomBar: @Composable () -> Unit = emptyContent(),
//  snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
//  floatingActionButton: @Composable () -> Unit = emptyContent(),
//  floatingActionButtonPosition: FabPosition = FabPosition.End,
//  isFloatingActionButtonDocked: Boolean = false,
//  drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
//  drawerGesturesEnabled: Boolean = true,
//  drawerShape: Shape = MaterialTheme.shapes.large,
//  drawerElevation: Dp = DrawerDefaults.Elevation,
//  drawerBackgroundColor: Color = MaterialTheme.colors.surface,
//  drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
//  drawerScrimColor: Color = DrawerDefaults.scrimColor,
//  backgroundColor: Color = MaterialTheme.colors.background,
//  contentColor: Color = contentColorFor(backgroundColor),
//  bodyContent: @Composable (PaddingValues) -> Unit
//) {
//  val child = @Composable { childModifier: Modifier ->
//    Surface(modifier = childModifier, color = backgroundColor, contentColor = contentColor) {
//      ScaffoldLayout(
//        isFabDocked = isFloatingActionButtonDocked,
//        fabPosition = floatingActionButtonPosition,
//        topBar = topBar,
//        bodyContent = bodyContent,
//        snackbar = {
//          snackbarHost(bottomDrawerScaffoldState.snackbarHostState)
//        },
//        fab = floatingActionButton,
//        bottomBar = bottomBar
//      )
//    }
//  }
//
//  if (drawerContent != null) {
//    BottomDrawerLayout(
//      modifier = modifier,
//      drawerState = bottomDrawerScaffoldState.drawerState,
//      gesturesEnabled = drawerGesturesEnabled,
//      drawerContent = drawerContent,
//      drawerShape = drawerShape,
//      drawerElevation = drawerElevation,
//      drawerBackgroundColor = drawerBackgroundColor,
//      drawerContentColor = drawerContentColor,
//      scrimColor = drawerScrimColor,
//      bodyContent = { child(Modifier) }
//    )
//  } else {
//    child(modifier)
//  }
//}
//
///**
// * Layout for a [BlockkaScreen]'s content.
// *
// * @param isFabDocked whether the FAB (if present) is docked to the bottom bar or not
// * @param fabPosition [FabPosition] for the FAB (if present)
// * @param topBar the content to place at the top of the [BlockkaScreen], typically a [TopAppBar]
// * @param bodyContent the main 'body' of the [BlockkaScreen]
// * @param snackbar the [Snackbar] displayed on top of the [bodyContent]
// * @param fab the [FloatingActionButton] displayed on top of the [bodyContent], below the [snackbar]
// * and above the [bottomBar]
// * @param bottomBar the content to place at the bottom of the [BlockkaScreen], on top of the
// * [bodyContent], typically a [BottomAppBar].
// */
//@Composable
//private fun ScaffoldLayout(
//  isFabDocked: Boolean,
//  fabPosition: FabPosition,
//  topBar: @Composable () -> Unit,
//  bodyContent: @Composable (PaddingValues) -> Unit,
//  snackbar: @Composable () -> Unit,
//  fab: @Composable () -> Unit,
//  bottomBar: @Composable () -> Unit
//) {
//  SubcomposeLayout { constraints ->
//    val layoutWidth = constraints.maxWidth
//    val layoutHeight = constraints.maxHeight
//
//    val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
//
//    layout(layoutWidth, layoutHeight) {
//      val topBarPlaceables = subcompose(ScaffoldLayoutContent.TopBar, topBar).map {
//        it.measure(looseConstraints)
//      }
//
//      val topBarHeight = topBarPlaceables.maxByOrNull { it.height }?.height ?: 0
//
//      val snackbarPlaceables = subcompose(ScaffoldLayoutContent.Snackbar, snackbar).map {
//        it.measure(looseConstraints)
//      }
//
//      val snackbarHeight = snackbarPlaceables.maxByOrNull { it.height }?.height ?: 0
//
//      val fabPlaceables = subcompose(ScaffoldLayoutContent.Fab, fab).map {
//        it.measure(looseConstraints)
//      }
//
//      val fabWidth = fabPlaceables.maxByOrNull { it.width }?.width ?: 0
//      val fabHeight = fabPlaceables.maxByOrNull { it.height }?.height ?: 0
//
//      // FAB distance from the left of the layout, taking into account LTR / RTL
//      val fabLeftOffset = if (fabWidth != 0 && fabHeight != 0) {
//        if (fabPosition == FabPosition.End) {
//          if (layoutDirection == LayoutDirection.Ltr) {
//            layoutWidth - FabSpacing.toIntPx() - fabWidth
//          } else {
//            FabSpacing.toIntPx()
//          }
//        } else {
//          (layoutWidth - fabWidth) / 2
//        }
//      } else {
//        0
//      }
//
//      val fabPlacement = if (fabWidth != 0 && fabHeight != 0) {
//        FabPlacement(
//          isDocked = isFabDocked,
//          left = fabLeftOffset,
//          width = fabWidth,
//          height = fabHeight
//        )
//      } else {
//        null
//      }
//
//      val bottomBarPlaceables = subcompose(ScaffoldLayoutContent.BottomBar) {
//        Providers(
//          AmbientFabPlacement provides fabPlacement,
//          content = bottomBar
//        )
//      }.map { it.measure(looseConstraints) }
//
//      val bottomBarHeight = bottomBarPlaceables.maxByOrNull { it.height }?.height ?: 0
//
//      val fabOffsetFromBottom = if (fabWidth != 0 && fabHeight != 0) {
//        if (bottomBarHeight == 0) {
//          fabHeight + FabSpacing.toIntPx()
//        } else {
//          if (isFabDocked) {
//            // Total height is the bottom bar height + half the FAB height
//            bottomBarHeight + (fabHeight / 2)
//          } else {
//            // Total height is the bottom bar height + the FAB height + the padding
//            // between the FAB and bottom bar
//            bottomBarHeight + fabHeight + FabSpacing.toIntPx()
//          }
//        }
//      } else {
//        0
//      }
//
//      val snackbarOffsetFromBottom = if (snackbarHeight != 0) {
//        snackbarHeight + if (fabOffsetFromBottom != 0) {
//          fabOffsetFromBottom
//        } else {
//          bottomBarHeight
//        }
//      } else {
//        0
//      }
//
//      val bodyContentHeight = layoutHeight - topBarHeight
//
//      val bodyContentPlaceables = subcompose(ScaffoldLayoutContent.MainContent) {
//        val innerPadding = PaddingValues(bottom = bottomBarHeight.toDp())
//        bodyContent(innerPadding)
//      }.map { it.measure(looseConstraints.copy(maxHeight = bodyContentHeight)) }
//
//      // Placing to control drawing order to match default elevation of each placeable
//
//      bodyContentPlaceables.forEach {
//        it.place(0, topBarHeight)
//      }
//      topBarPlaceables.forEach {
//        it.place(0, 0)
//      }
//      snackbarPlaceables.forEach {
//        it.place(0, layoutHeight - snackbarOffsetFromBottom)
//      }
//      // The bottom bar is always at the bottom of the layout
//      bottomBarPlaceables.forEach {
//        it.place(0, layoutHeight - bottomBarHeight)
//      }
//      // Explicitly not using placeRelative here as `leftOffset` already accounts for RTL
//      fabPlaceables.forEach {
//        it.place(fabLeftOffset, layoutHeight - fabOffsetFromBottom)
//      }
//    }
//  }
//}
//
///**
// * Placement information for a [FloatingActionButton] inside a [BlockkaScreen].
// *
// * @property isDocked whether the FAB should be docked with the bottom bar
// * @property left the FAB's offset from the left edge of the bottom bar, already adjusted for RTL
// * support
// * @property width the width of the FAB
// * @property height the height of the FAB
// */
//@Immutable
//internal class FabPlacement(
//  val isDocked: Boolean,
//  val left: Int,
//  val width: Int,
//  val height: Int
//)
//
///**
// * Ambient containing a [FabPlacement] that is read by [BottomAppBar] to calculate notch location.
// */
//internal val AmbientFabPlacement = staticAmbientOf<FabPlacement?> { null }
//
//// FAB spacing above the bottom bar / bottom of the Scaffold
//private val FabSpacing = 16.dp
//
//private enum class ScaffoldLayoutContent { TopBar, MainContent, Snackbar, Fab, BottomBar }
