package com.takagimeow.adaptivelayout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.takagimeow.adaptivelayout.core.testing.RobotTestRule
import javax.inject.Inject

object MyApplicationIcons {
    val Home = Icons.Outlined.Home
    val HomeFilled = Icons.Filled.Home
    val Settings = Icons.Outlined.Settings
    val SettingsFilled = Icons.Filled.Settings
}

object HomeDestination : AdaptiveLayoutNavigationDestination {
    override val route = "home_route"
    override val destination = "home_destination"
}

object SettingsDestination : AdaptiveLayoutNavigationDestination {
    override val route = "settings_route"
    override val destination = "settings_destination"
}

val HomeTopLevelDestination: AdaptiveLayoutTopLevelDestination = AdaptiveLayoutTopLevelDestination(
    route = HomeDestination.route,
    destination = HomeDestination.destination,
    selectedAdaptiveLayoutIcon = AdaptiveLayoutIcon.ImageVectorAdaptiveLayoutIcon(MyApplicationIcons.HomeFilled),
    unselectedAdaptiveLayoutIcon = AdaptiveLayoutIcon.ImageVectorAdaptiveLayoutIcon(MyApplicationIcons.Home),
    iconTextId = R.string.home
)

val SettingsTopLevelDestination: AdaptiveLayoutTopLevelDestination = AdaptiveLayoutTopLevelDestination(
    route = SettingsDestination.route,
    destination = SettingsDestination.destination,
    selectedAdaptiveLayoutIcon = AdaptiveLayoutIcon.ImageVectorAdaptiveLayoutIcon(MyApplicationIcons.SettingsFilled),
    unselectedAdaptiveLayoutIcon = AdaptiveLayoutIcon.ImageVectorAdaptiveLayoutIcon(MyApplicationIcons.Settings),
    iconTextId = R.string.settings
)

class AdaptiveLayoutBottomNavigationBarRobot @Inject constructor() {

    context(RobotTestRule)
    fun checkIconsDisplayed() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.home)).assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.settings)).assertExists()

        composeTestRule.onNode(
            hasContentDescription("home_route"),
            useUnmergedTree = true,
        ).assertExists()
        composeTestRule.onNode(
            hasContentDescription("settings_route"),
            useUnmergedTree = true
        ).assertExists()
    }

    operator fun invoke(
        robotTestRule: RobotTestRule,
        function: context(RobotTestRule) AdaptiveLayoutBottomNavigationBarRobot.() -> Unit
    ) {
        robotTestRule.composeTestRule.setContent {

            val topLevelDestinations: List<AdaptiveLayoutTopLevelDestination> = listOf(
                HomeTopLevelDestination,
                SettingsTopLevelDestination
            )

            AdaptiveLayoutBottomNavigationBar(
                destinations = topLevelDestinations,
                currentRoute = topLevelDestinations[0].destination,
                onNavigate = {}
            )
        }
        function(robotTestRule, this@AdaptiveLayoutBottomNavigationBarRobot)
    }
}