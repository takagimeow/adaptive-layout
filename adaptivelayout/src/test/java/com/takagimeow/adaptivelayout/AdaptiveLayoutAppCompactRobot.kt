package com.takagimeow.adaptivelayout

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToString
import androidx.navigation.compose.rememberNavController
import com.takagimeow.adaptivelayout.core.testing.RobotTestRule
import com.takagimeow.adaptivelayout.ui.AdaptiveLayoutNavGraph
import javax.inject.Inject

class AdaptiveLayoutAppCompactRobot @Inject constructor() {
    context(RobotTestRule)
    fun checkBottomNavigationBarDisplayed() {
        val semanticsTree = composeTestRule.onRoot().printToString()
        println(semanticsTree)
        composeTestRule.onNodeWithContentDescription("Navigation Drawer").assertIsNotDisplayed()
        composeTestRule.onNodeWithContentDescription("Bottom Navigation Bar").assertIsDisplayed()
    }

    context(RobotTestRule)
    fun ensureIconBecomesSelectedWhenClicked() {
        val semanticsTree = composeTestRule.onRoot().printToString()
        println(semanticsTree)

        composeTestRule.onNodeWithContentDescription(
            "Selected home_route Icon on Bottom Navigation Bar",
            useUnmergedTree = true
        ).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            "Unselected settings_route Icon on Bottom Navigation Bar",
            useUnmergedTree = true
        ).assertIsDisplayed().performClick()

        composeTestRule.onNodeWithContentDescription(
            "Unselected home_route Icon on Bottom Navigation Bar",
            useUnmergedTree = true
        ).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            "Selected settings_route Icon on Bottom Navigation Bar",
            useUnmergedTree = true
        ).assertIsDisplayed()
    }

    operator fun invoke(
        robotTestRule: RobotTestRule,
        function: context(RobotTestRule) AdaptiveLayoutAppCompactRobot.() -> Unit
    ) {
        robotTestRule.composeTestRule.setContent {
            val topLevelDestinations: List<AdaptiveLayoutTopLevelDestination> = listOf(
                HomeTopLevelDestination,
                SettingsTopLevelDestination
            )
            val navController = rememberNavController()
            val appState: AdaptiveLayoutAppState = rememberAdaptiveLayoutAppState(
                navController = navController,
                topLevelDestinations = topLevelDestinations
            )
            AdaptiveLayoutApp(
                navController = navController,
                topLevelDestinations = topLevelDestinations,
                currentRoute = appState.currentDestination?.route,
                windowSize = WindowWidthSizeClass.Compact,
                foldingDevicePosture = DevicePosture.NormalPosture,
                optionalNavigationDisplayConditions = true,
                shouldShowBottomBar = appState.shouldShowBottomBar,
                onNavigateToDestination = appState::navigate,
                onNavigateAndPopUpToDestination = appState::navigateAndPopUp,
                background = { _, content -> content() }
            ) { isListAndDetail, navController, navigate, navigateAndPopUp ->
                AdaptiveLayoutNavGraph(
                    navController = navController,
                    isListAndDetail = isListAndDetail,
                    startDestination = HomeDestination.route,
                    onNavigateAndPopUpToDestination = navigateAndPopUp,
                    onNavigateToDestination = navigate,
                )
            }
        }
        function(robotTestRule, this@AdaptiveLayoutAppCompactRobot)
    }
}