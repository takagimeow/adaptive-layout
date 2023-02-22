package com.takagimeow.myapplication.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.takagimeow.adaptivelayout.AdaptiveLayoutNavigationDestination
import com.takagimeow.myapplication.feature.home.navigation.homeGraph
import com.takagimeow.myapplication.feature.settings.navigation.settingsGraph
import com.takagimeow.myapplication.feature.splash.navigation.splashGraph

private const val TAG = "MyApplicationNavGraph"
@Composable
fun MyApplicationNavGraph(
    navController: NavHostController,
    onNavigateToDestination: (AdaptiveLayoutNavigationDestination, String, from: NavBackStackEntry?) -> Unit,
    onNavigateAndPopUpToDestination: (AdaptiveLayoutNavigationDestination, String, from: NavBackStackEntry?) -> Unit,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        splashGraph(
            navigateToHome = { navBackStackEntry ->
                Log.d(TAG, "navigateToHomeが呼び出されました")
                onNavigateAndPopUpToDestination(
                    HomeTopLevelDestination,
                    HomeTopLevelDestination.destination,
                    navBackStackEntry
                )
            },
            navigateToOnboarding = {
                Log.d(TAG, "navigateToOnboardingが呼び出されました")
            }
        )
        homeGraph() {}
        settingsGraph()
    }
}