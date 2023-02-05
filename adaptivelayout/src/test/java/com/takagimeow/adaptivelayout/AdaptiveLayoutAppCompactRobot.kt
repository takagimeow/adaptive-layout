package com.takagimeow.adaptivelayout

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToString
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

        composeTestRule.onNodeWithContentDescription("Selected home_route Icon on Bottom Navigation Bar", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Unselected settings_route Icon on Bottom Navigation Bar", useUnmergedTree = true).assertIsDisplayed().performClick()

        composeTestRule.onNodeWithContentDescription("Unselected home_route Icon on Bottom Navigation Bar", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Selected settings_route Icon on Bottom Navigation Bar", useUnmergedTree = true).assertIsDisplayed()
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
            val appState = rememberAdaptiveLayoutAppState(
                topLevelDestinations = topLevelDestinations,
            )

            AdaptiveLayoutApp(
                appState = appState,
                windowSize = WindowWidthSizeClass.Compact,
                foldingDevicePosture = DevicePosture.NormalPosture,
                optionalNavigationDisplayConditions = true,
                background = { _, content -> content() }
            ) {isListAndDetail ->
                AdaptiveLayoutNavGraph(
                    navController = appState.navController,
                    isListAndDetail = isListAndDetail,
                    startDestination = HomeDestination.route,
                    onNavigateAndPopUpToDestination = appState::navigateAndPopUp,
                    onNavigateToDestination = appState::navigate,
                )
            }
        }
        function(robotTestRule, this@AdaptiveLayoutAppCompactRobot)
    }
}