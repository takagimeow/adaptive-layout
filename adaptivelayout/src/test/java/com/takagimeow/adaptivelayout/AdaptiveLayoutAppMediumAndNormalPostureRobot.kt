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

class AdaptiveLayoutAppMediumAndNormalPostureRobot @Inject constructor() {

    context(RobotTestRule)
    fun checkNavigationRailDisplayed() {
        composeTestRule.onNodeWithContentDescription("Navigation Rail").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Drawer Icon on Navigation Rail").assertIsDisplayed()
    }

    context(RobotTestRule)
    fun ensureIconBecomesSelectedWhenClicked() {
        val semanticsTree = composeTestRule.onRoot().printToString()
        println(semanticsTree)

        composeTestRule.onNodeWithContentDescription("Selected home_route Icon on Navigation Rail").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Unselected settings_route Icon on Navigation Rail").assertIsDisplayed().performClick()

        composeTestRule.onNodeWithContentDescription("Unselected home_route Icon on Navigation Rail").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Selected settings_route Icon on Navigation Rail").assertIsDisplayed()
    }

    context(RobotTestRule)
    fun ensureDrawerDisplayed() {
        composeTestRule.onNodeWithContentDescription("Drawer Icon on Navigation Rail").performClick()

        composeTestRule.onNodeWithContentDescription("Navigation Drawer").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Unselected settings_route Icon on Navigation Drawer").performClick()

        composeTestRule.onNodeWithContentDescription("Navigation Drawer").assertIsNotDisplayed()
    }

    operator fun invoke(
        robotTestRule: RobotTestRule,
        function: context(RobotTestRule) AdaptiveLayoutAppMediumAndNormalPostureRobot.() -> Unit
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
                windowSize = WindowWidthSizeClass.Medium,
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
        function(robotTestRule, this@AdaptiveLayoutAppMediumAndNormalPostureRobot)
    }
}