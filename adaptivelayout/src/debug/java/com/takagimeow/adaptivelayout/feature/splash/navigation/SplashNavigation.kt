package com.takagimeow.adaptivelayout.feature.splash.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.takagimeow.adaptivelayout.AdaptiveLayoutNavigationDestination
import com.takagimeow.adaptivelayout.feature.splash.SplashRoute

object SplashDestination : AdaptiveLayoutNavigationDestination {
    override val route = "splash_route"
    override val destination = "splash_destination"
}

fun NavGraphBuilder.splashGraph(
    navigateToHome: (NavBackStackEntry) -> Unit,
    navigateToOnboarding: (NavBackStackEntry) -> Unit,
) {
    composable(
        route = SplashDestination.route
    ) {
            navBackStackEntry ->
        SplashRoute(
            navigateToHome = {
                navigateToHome(navBackStackEntry)
            },
            navigateToOnboarding = {
                navigateToOnboarding(navBackStackEntry)
            }
        )
    }
}