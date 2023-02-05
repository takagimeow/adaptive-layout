package com.takagimeow.adaptivelayout.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import com.takagimeow.adaptivelayout.AdaptiveLayoutTopLevelDestination
import com.takagimeow.adaptivelayout.AdaptiveLayoutIcon
import com.takagimeow.adaptivelayout.R
import com.takagimeow.adaptivelayout.feature.home.navigation.HomeDestination
import com.takagimeow.adaptivelayout.feature.settings.navigation.SettingsDestination

object AdaptiveLayoutIcons {
    val Home = Icons.Outlined.Home
    val HomeFilled = Icons.Filled.Home
    val Settings = Icons.Outlined.Settings
    val SettingsFilled = Icons.Filled.Settings
}
val HomeTopLevelDestination: AdaptiveLayoutTopLevelDestination = AdaptiveLayoutTopLevelDestination(
    route = HomeDestination.route,
    destination = HomeDestination.destination,
    selectedAdaptiveLayoutIcon = AdaptiveLayoutIcon.ImageVectorAdaptiveLayoutIcon(AdaptiveLayoutIcons.HomeFilled),
    unselectedAdaptiveLayoutIcon = AdaptiveLayoutIcon.ImageVectorAdaptiveLayoutIcon(AdaptiveLayoutIcons.Home),
    iconTextId = R.string.home
)

val SettingsTopLevelDestination: AdaptiveLayoutTopLevelDestination = AdaptiveLayoutTopLevelDestination(
    route = SettingsDestination.route,
    destination = SettingsDestination.destination,
    selectedAdaptiveLayoutIcon = AdaptiveLayoutIcon.ImageVectorAdaptiveLayoutIcon(AdaptiveLayoutIcons.SettingsFilled),
    unselectedAdaptiveLayoutIcon = AdaptiveLayoutIcon.ImageVectorAdaptiveLayoutIcon(AdaptiveLayoutIcons.Settings),
    iconTextId = R.string.settings
)