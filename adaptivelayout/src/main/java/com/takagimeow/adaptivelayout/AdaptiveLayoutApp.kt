package com.takagimeow.adaptivelayout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

// private const val TAG = "AdaptiveLayoutApp"

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AdaptiveLayoutApp(
    // Property
    navController: NavHostController,
    topLevelDestinations: List<AdaptiveLayoutTopLevelDestination>,
    currentRoute: String?,
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture, // State indicating whether folded or not.
    optionalNavigationDisplayConditions: Boolean,
    shouldShowBottomBar: Boolean,
    // Handler
    onNavigateToDestination: (destination: AdaptiveLayoutNavigationDestination, route: String?, from: NavBackStackEntry?) -> Unit,
    onNavigateAndPopUpToDestination: (destination: AdaptiveLayoutNavigationDestination, route: String?, from: NavBackStackEntry?) -> Unit,
    // Composable
    background: @Composable (route: String?, @Composable () -> Unit) -> Unit,
    content: @Composable (
        isListAndDetail: Boolean,
        navController: NavHostController,
        onNavigateToDestination: (navigationDestination: AdaptiveLayoutNavigationDestination, destination: String, from: NavBackStackEntry?) -> Unit,
        onNavigateAndPopUpToDestination: (navigationDestination: AdaptiveLayoutNavigationDestination, destination: String, from: NavBackStackEntry?) -> Unit,
    ) -> Unit,
) {
    val navigationType: AdaptiveLayoutNavigationType
    val contentType: AdaptiveLayoutContentType

    when (windowSize) {
        //　When the screen size is the same as that of a smartphone
        WindowWidthSizeClass.Compact -> {
            navigationType = AdaptiveLayoutNavigationType.BOTTOM_NAVIGATION
            contentType = AdaptiveLayoutContentType.LIST_ONLY
        }
        // When the screen size is between a tablet and a smartphone
        WindowWidthSizeClass.Medium -> {
            navigationType = AdaptiveLayoutNavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                // If the device is Galaxy Z Fold 4 and the screen is fully open, display the list and detail windows on one screen.
                // If the device is a Surface DUO and the screen is not fully open, display the list and detail windows on one screen.
                AdaptiveLayoutContentType.LIST_AND_DETAIL
            } else {
                // If in collapsed state, only the list is displayed.
                AdaptiveLayoutContentType.LIST_ONLY
            }
        }
        // When the screen size is the same as that of a tablet
        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                AdaptiveLayoutNavigationType.NAVIGATION_RAIL
            } else {
                AdaptiveLayoutNavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = AdaptiveLayoutContentType.LIST_AND_DETAIL
        }

        else -> {
            navigationType = AdaptiveLayoutNavigationType.BOTTOM_NAVIGATION
            contentType = AdaptiveLayoutContentType.LIST_ONLY
        }
    }

    AdaptiveLayoutNavigationWrapperUI(
        navController,
        topLevelDestinations,
        currentRoute,
        navigationType,
        contentType,
        optionalNavigationDisplayConditions = optionalNavigationDisplayConditions,
        shouldShowBottomBar = shouldShowBottomBar,
        onNavigateToDestination = onNavigateToDestination,
        onNavigateAndPopUpToDestination = onNavigateAndPopUpToDestination,
        background = background,
        content = content,
    )
}

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdaptiveLayoutNavigationWrapperUI(
    // Property
    navController: NavHostController,
    topLevelDestinations: List<AdaptiveLayoutTopLevelDestination>,
    currentRoute: String?,
    navigationType: AdaptiveLayoutNavigationType,
    contentType: AdaptiveLayoutContentType,
    optionalNavigationDisplayConditions: Boolean,
    shouldShowBottomBar: Boolean,
    // Handler
    onNavigateToDestination: (destination: AdaptiveLayoutNavigationDestination, route: String?, from: NavBackStackEntry?) -> Unit,
    onNavigateAndPopUpToDestination: (destination: AdaptiveLayoutNavigationDestination, route: String?, from: NavBackStackEntry?) -> Unit,
    // Composable
    background: @Composable (route: String?, @Composable () -> Unit) -> Unit,
    content: @Composable (
        isListAndDetail: Boolean,
        navController: NavHostController,
        onNavigateToDestination: (navigationDestination: AdaptiveLayoutNavigationDestination, destination: String, from: NavBackStackEntry?) -> Unit,
        onNavigateAndPopUpToDestination: (navigationDestination: AdaptiveLayoutNavigationDestination, destination: String, from: NavBackStackEntry?) -> Unit,
    ) -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (navigationType == AdaptiveLayoutNavigationType.PERMANENT_NAVIGATION_DRAWER && optionalNavigationDisplayConditions) {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet {
                    AdaptiveLayoutNavigationDrawerContent(
                        destinations = topLevelDestinations,
                        selectedDestination = currentRoute,
                        onDrawerClicked = {
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        onNavigate = {
                            onNavigateAndPopUpToDestination(it, null, null)
                        }
                    )
                }
            }
        ) {
            AdaptiveLayoutAppContent(
                navController,
                topLevelDestinations,
                currentRoute,
                navigationType,
                contentType,
                optionalNavigationDisplayConditions = true,
                shouldShowBottomBar = shouldShowBottomBar,
                onNavigateToDestination = onNavigateToDestination,
                onNavigateAndPopUpToDestination = onNavigateAndPopUpToDestination,
                background = background,
                content = content,
            )
        }
    } else if (optionalNavigationDisplayConditions) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    AdaptiveLayoutNavigationDrawerContent(
                        destinations = topLevelDestinations,
                        selectedDestination = currentRoute,
                        onDrawerClicked = {
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        onNavigate = {
                            onNavigateAndPopUpToDestination(it, null, null)
                        }
                    )
                }
            },
            drawerState = drawerState
        ) {
            AdaptiveLayoutAppContent(
                navController,
                topLevelDestinations,
                currentRoute,
                navigationType,
                contentType,
                optionalNavigationDisplayConditions = true,
                shouldShowBottomBar = shouldShowBottomBar,
                onDrawerClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                },
                onNavigateToDestination = onNavigateToDestination,
                onNavigateAndPopUpToDestination = onNavigateAndPopUpToDestination,
                background = background,
                content = content,
            )
        }
    } else {
        AdaptiveLayoutAppContent(
            navController,
            topLevelDestinations,
            currentRoute,
            navigationType,
            contentType,
            optionalNavigationDisplayConditions = false,
            shouldShowBottomBar = shouldShowBottomBar,
            onDrawerClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            onNavigateToDestination = onNavigateToDestination,
            onNavigateAndPopUpToDestination = onNavigateAndPopUpToDestination,
            background = background,
            content = content,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AdaptiveLayoutAppContent(
    // Property
    navController: NavHostController,
    topLevelDestinations: List<AdaptiveLayoutTopLevelDestination>,
    currentRoute: String?,
    navigationType: AdaptiveLayoutNavigationType,
    contentType: AdaptiveLayoutContentType,
    optionalNavigationDisplayConditions: Boolean,
    shouldShowBottomBar: Boolean,
    // Handler
    onDrawerClicked: () -> Unit = {},
    onNavigateToDestination: (destination: AdaptiveLayoutNavigationDestination, route: String?, from: NavBackStackEntry?) -> Unit,
    onNavigateAndPopUpToDestination: (destination: AdaptiveLayoutNavigationDestination, route: String?, from: NavBackStackEntry?) -> Unit,
    // Composable
    background: @Composable (route: String?, @Composable () -> Unit) -> Unit,
    content: @Composable (
        isListAndDetail: Boolean,
        navController: NavHostController,
        onNavigateToDestination: (destination: AdaptiveLayoutNavigationDestination, route: String?, from: NavBackStackEntry?) -> Unit,
        onNavigateAndPopUpToDestination: (destination: AdaptiveLayoutNavigationDestination, route: String?, from: NavBackStackEntry?) -> Unit,
    ) -> Unit,
) {

    Row(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == AdaptiveLayoutNavigationType.NAVIGATION_RAIL && optionalNavigationDisplayConditions) {
            AdaptiveLayoutNavigationRail(
                destinations = topLevelDestinations,
                selectedDestination = currentRoute,
                onDrawerClicked = onDrawerClicked,
                onNavigate = {
                    onNavigateAndPopUpToDestination(it, null, null)
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                background(currentRoute) {
                    content(
                        isListAndDetail = contentType == AdaptiveLayoutContentType.LIST_AND_DETAIL,
                        navController = navController,
                        onNavigateToDestination = onNavigateToDestination,
                        onNavigateAndPopUpToDestination = onNavigateAndPopUpToDestination,
                    )
                }
            }

            AnimatedVisibility(
                visible = navigationType == AdaptiveLayoutNavigationType.BOTTOM_NAVIGATION && shouldShowBottomBar
            ) {
                AdaptiveLayoutBottomNavigationBar(
                    destinations = topLevelDestinations,
                    currentRoute = currentRoute,
                    onNavigate = {
                        onNavigateAndPopUpToDestination(it, null, null)
                    }
                )
            }
        }
    }
}