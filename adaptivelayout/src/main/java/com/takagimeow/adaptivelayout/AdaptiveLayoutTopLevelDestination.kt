package com.takagimeow.adaptivelayout

data class AdaptiveLayoutTopLevelDestination(
    override val route: String,
    override val destination: String,
    val selectedAdaptiveLayoutIcon: AdaptiveLayoutIcon,
    val unselectedAdaptiveLayoutIcon: AdaptiveLayoutIcon,
    val iconTextId: Int
) : AdaptiveLayoutNavigationDestination
