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
import kotlinx.coroutines.launch

// private const val TAG = "AdaptiveLayoutApp"

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AdaptiveLayoutApp(
    appState: AdaptiveLayoutAppState,
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture, // State indicating whether folded or not.
    optionalNavigationDisplayConditions: Boolean,
    background: @Composable (route: String?, @Composable () -> Unit) -> Unit,
    content: @Composable (isListAndDetail: Boolean) -> Unit,
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
        appState,
        navigationType,
        contentType,
        optionalNavigationDisplayConditions = optionalNavigationDisplayConditions,
        background = background,
        content = content,
    )
}

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdaptiveLayoutNavigationWrapperUI(
    appState: AdaptiveLayoutAppState,
    navigationType: AdaptiveLayoutNavigationType,
    contentType: AdaptiveLayoutContentType,
    optionalNavigationDisplayConditions: Boolean,
    background: @Composable (route: String?, @Composable () -> Unit) -> Unit,
    content: @Composable (isListAndDetail: Boolean) -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (navigationType == AdaptiveLayoutNavigationType.PERMANENT_NAVIGATION_DRAWER && optionalNavigationDisplayConditions) {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet {
                    AdaptiveLayoutNavigationDrawerContent(
                        destinations = appState.topLevelDestinations,
                        selectedDestination = appState.currentDestination?.route,
                        onDrawerClicked = {
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        onNavigate = appState::navigateAndPopUp
                    )
                }
            }
        ) {
            AdaptiveLayoutAppContent(
                appState,
                navigationType,
                contentType,
                optionalNavigationDisplayConditions = true,
                background = background,
                content = content,
            )
        }
    } else if (optionalNavigationDisplayConditions) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    AdaptiveLayoutNavigationDrawerContent(
                        destinations = appState.topLevelDestinations,
                        selectedDestination = appState.currentDestination?.route,
                        onDrawerClicked = {
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        onNavigate = appState::navigateAndPopUp
                    )
                }
            },
            drawerState = drawerState
        ) {
            AdaptiveLayoutAppContent(
                appState,
                navigationType,
                contentType,
                optionalNavigationDisplayConditions = true,
                onDrawerClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                },
                background = background,
                content = content,
            )
        }
    } else {
        AdaptiveLayoutAppContent(
            appState,
            navigationType,
            contentType,
            optionalNavigationDisplayConditions = false,
            onDrawerClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            background = background,
            content = content,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AdaptiveLayoutAppContent(
    appState: AdaptiveLayoutAppState,
    navigationType: AdaptiveLayoutNavigationType,
    contentType: AdaptiveLayoutContentType,
    optionalNavigationDisplayConditions: Boolean,
    onDrawerClicked: () -> Unit = {},
    background: @Composable (route: String?, @Composable () -> Unit) -> Unit,
    content: @Composable (isListAndDetail: Boolean) -> Unit,
) {

    Row(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == AdaptiveLayoutNavigationType.NAVIGATION_RAIL && optionalNavigationDisplayConditions) {
            AdaptiveLayoutNavigationRail(
                destinations = appState.topLevelDestinations,
                selectedDestination = appState.currentDestination?.route,
                onDrawerClicked = onDrawerClicked,
                onNavigate = appState::navigateAndPopUp
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                background(appState.currentDestination?.route) {
                    content(
                        isListAndDetail = contentType == AdaptiveLayoutContentType.LIST_AND_DETAIL
                    )
                }
            }

            AnimatedVisibility(
                visible = navigationType == AdaptiveLayoutNavigationType.BOTTOM_NAVIGATION && appState.shouldShowBottomBar
            ) {
                AdaptiveLayoutBottomNavigationBar(
                    destinations = appState.topLevelDestinations,
                    currentRoute = appState.currentDestination?.route,
                    onNavigate = appState::navigateAndPopUp
                )
            }
        }
    }
}