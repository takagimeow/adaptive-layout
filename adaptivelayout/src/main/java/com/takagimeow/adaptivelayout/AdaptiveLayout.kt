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
        },
        navController = navController,
        topLevelDestinations = topLevelDestinations,
    )

    val devicePostureFlow = viewModel.devicePostureFlow
    val devicePosture = devicePostureFlow.collectAsState().value

    val windowSize = calculateWindowSizeClass(context as ComponentActivity)

    AdaptiveLayoutApp(
        navController = viewModel.navController,
        topLevelDestinations = viewModel.topLevelDestinations,
        currentRoute = viewModel.currentDestination?.route,
        windowSize = windowSize.widthSizeClass,
        foldingDevicePosture = devicePosture,
        optionalNavigationDisplayConditions = optionalNavigationDisplayConditions,
        shouldShowBottomBar = viewModel.shouldShowBottomBar,
        onNavigateToDestination = viewModel::navigate,
        onNavigateAndPopUpToDestination = viewModel::navigateAndPopUp,
        background = background,
        content = content,
    )
}