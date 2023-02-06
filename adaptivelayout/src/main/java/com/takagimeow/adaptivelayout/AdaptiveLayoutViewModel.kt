package com.takagimeow.adaptivelayout

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AdaptiveLayoutViewModel (
    contextProvider: ContextProvider,
    val navController: NavHostController,
    val topLevelDestinations: List<AdaptiveLayoutTopLevelDestination>,
): ViewModel() {

    private val topLevelDestinationRoutes = topLevelDestinations.map { it.destination }

    val shouldShowBottomBar: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route in topLevelDestinationRoutes

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    fun isTopLevelDestination(): Boolean {
        return navController.currentBackStackEntry?.destination?.route.let { route ->
            topLevelDestinations.any {
                it.destination == route
            }
        }
    }


    // Check and monitor folding state.
    val devicePostureFlow = WindowInfoTracker.getOrCreate(contextProvider.activity)
        .windowLayoutInfo(contextProvider.activity)
        .flowWithLifecycle(contextProvider.activity.lifecycle) // Supported since lifecycle-viewmodel-compose:2.5.1
        .map { layoutInfo ->
            val foldingFeature =
                layoutInfo.displayFeatures
                    .filterIsInstance<FoldingFeature>()
                    .firstOrNull()
            when {
                isBookPosture(foldingFeature) ->
                    DevicePosture.BookPosture(foldingFeature.bounds)
                isSeparating(foldingFeature) ->
                    DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)
                else -> DevicePosture.NormalPosture
            }
        }
        .stateIn(
            scope = contextProvider.activity.lifecycleScope,
            started = SharingStarted.Eagerly,
            initialValue = DevicePosture.NormalPosture
        )

    fun navigate(
        destination: AdaptiveLayoutNavigationDestination,
        route: String? = null,
        from: NavBackStackEntry? = try {
            navController.currentBackStackEntry
        } catch (err: Exception) {
            null
        }
    ) {
        if (from != null && from.lifecycleIsResumed()) {
            navController.navigate(route ?: destination.destination) {
                // Ensure that at most one copy of a particular destination is placed at the top of the back stack.
                launchSingleTop = true
            }
        }
    }

    fun navigateAndPopUp(
        destination: AdaptiveLayoutNavigationDestination,
        route: String? = null,
        from: NavBackStackEntry? = null
    ) {
        val currentBackStackEntry = from ?: try {
            navController.currentBackStackEntry
        } catch (err: Exception) {
            null
        }
        if (currentBackStackEntry != null && currentBackStackEntry.lifecycleIsResumed()) {
            val newRoute = when (destination) {
                is AdaptiveLayoutTopLevelDestination -> {
                    destination.destination
                }

                else -> {
                    route ?: destination.route
                }
            }
            when (newRoute) {
                currentBackStackEntry.destination.route -> {
                    /* 何もしない */
                }

                else -> {
                    navController.navigate(newRoute) {
                        // Pop to first destination in graph to avoid stacking large number of destinations on back stack when selecting tabs
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Ensure that at most one copy of a particular destination is placed at the top of the back stack.
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    fun upPress() {
        navController.popBackStack()
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
