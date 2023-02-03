package com.takagimeow.adaptivelayout

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun AdaptiveLayoutNavigationRail(
    destinations: List<AdaptiveLayoutTopLevelDestination>,
    selectedDestination: String?,
    onDrawerClicked: () -> Unit = {},
    onNavigate: (AdaptiveLayoutNavigationDestination) -> Unit,
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight()
    ) {
        NavigationRailItem(
            selected = false,
            onClick = onDrawerClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = ""
                )
            }
        )
        destinations.forEachIndexed { _, destination ->
            val selected = selectedDestination == destination.destination
            NavigationRailItem(
                icon = {
                    val icon = if (selected) {
                        destination.selectedAdaptiveLayoutIcon
                    } else {
                        destination.unselectedAdaptiveLayoutIcon
                    }
                    when (icon) {
                        is AdaptiveLayoutIcon.ImageVectorAdaptiveLayoutIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = destination.route
                        )
                        is AdaptiveLayoutIcon.DrawableResourceAdaptiveLayoutIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = destination.route
                        )
                    }
                },
                selected = selected,
                onClick = {
                    onNavigate(destination)
                },
            )
        }
    }
}