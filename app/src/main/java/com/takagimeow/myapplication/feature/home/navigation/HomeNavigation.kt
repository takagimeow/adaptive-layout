package com.takagimeow.myapplication.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.takagimeow.adaptivelayout.AdaptiveLayoutNavigationDestination
import com.takagimeow.myapplication.feature.home.HomeRoute

object HomeDestination : AdaptiveLayoutNavigationDestination {
    override val route = "home_route"
    override val destination = "home_destination"
}

fun NavGraphBuilder.homeGraph(
    isListAndDetail: Boolean,
    nestedGraphs: NavGraphBuilder.() -> Unit = {},
) {
    navigation(
        route = HomeDestination.route,
        startDestination = HomeDestination.destination,
    ) {
        composable(
            route = HomeDestination.destination,
        ) { navBackStackEntry ->
            HomeRoute(
                isListAndDetail = isListAndDetail,
            )
        }
        nestedGraphs()
    }
}