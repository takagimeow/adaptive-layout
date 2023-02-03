package com.takagimeow.myapplication.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.takagimeow.adaptivelayout.AdaptiveLayoutNavigationDestination
import com.takagimeow.myapplication.feature.settings.SettingsRoute

object SettingsDestination : AdaptiveLayoutNavigationDestination {
    override val route = "settings_route"
    override val destination = "settings_destination"
}

fun NavGraphBuilder.settingsGraph(
    isListAndDetail: Boolean,
) {
    navigation(
        route = SettingsDestination.route,
        startDestination = SettingsDestination.destination,
    ) {
        composable(
            route = SettingsDestination.destination,
        ) { navBackStackEntry ->
            SettingsRoute(
                isListAndDetail = isListAndDetail,
            )
        }
    }
}