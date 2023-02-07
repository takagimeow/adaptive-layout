package com.takagimeow.adaptivelayout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAdaptiveLayoutAppState(
    navController: NavHostController = rememberNavController(),
    topLevelDestinations: List<AdaptiveLayoutTopLevelDestination>,
) : AdaptiveLayoutAppState {
    return remember(navController, topLevelDestinations) {
        AdaptiveLayoutAppState(
            navController = navController,
            topLevelDestinations = topLevelDestinations,
        )
    }
}

@Stable
class AdaptiveLayoutAppState constructor(
    val navController: NavHostController,
    val topLevelDestinations: List<AdaptiveLayoutTopLevelDestination>,
) {

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