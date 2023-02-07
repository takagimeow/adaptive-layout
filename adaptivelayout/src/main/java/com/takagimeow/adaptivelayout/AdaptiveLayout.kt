package com.takagimeow.adaptivelayout

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AdaptiveLayout(
    navController: NavHostController = rememberNavController(),
    topLevelDestinations: List<AdaptiveLayoutTopLevelDestination>,
    appState: AdaptiveLayoutAppState = rememberAdaptiveLayoutAppState(
        navController = navController,
        topLevelDestinations = topLevelDestinations
    ),
    optionalNavigationDisplayConditions: Boolean = true,
    background: @Composable (route: String?, content: @Composable () -> Unit) -> Unit = { _, content -> content() },
    content: @Composable (
        isListAndDetail: Boolean,
        navController: NavHostController,
        onNavigateToDestination: (navigationDestination: AdaptiveLayoutNavigationDestination, destination: String, from: NavBackStackEntry?) -> Unit,
        onNavigateAndPopUpToDestination: (navigationDestination: AdaptiveLayoutNavigationDestination, destination: String, from: NavBackStackEntry?) -> Unit,
    ) -> Unit,
) {
    val context = LocalContext.current
    val viewModel = AdaptiveLayoutViewModel(
        contextProvider = object : ContextProvider {
            override val activity: ComponentActivity
                get() = context as ComponentActivity
        }
    )

    val devicePostureFlow = viewModel.devicePostureFlow
    val devicePosture = devicePostureFlow.collectAsState().value

    val windowSize = calculateWindowSizeClass(context as ComponentActivity)

    AdaptiveLayoutApp(
        navController = appState.navController,
        topLevelDestinations = appState.topLevelDestinations,
        currentRoute = appState.currentDestination?.route,
        windowSize = windowSize.widthSizeClass,
        foldingDevicePosture = devicePosture,
        optionalNavigationDisplayConditions = optionalNavigationDisplayConditions,
        shouldShowBottomBar = appState.shouldShowBottomBar,
        onNavigateToDestination = appState::navigate,
        onNavigateAndPopUpToDestination = appState::navigateAndPopUp,
        background = background,
        content = content,
    )
}