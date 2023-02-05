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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
fun AdaptiveLayoutNavigationRail(
    destinations: List<AdaptiveLayoutTopLevelDestination>,
    selectedDestination: String?,
    onDrawerClicked: () -> Unit = {},
    onNavigate: (AdaptiveLayoutNavigationDestination) -> Unit,
) {
    val navigationRailContentDescription = stringResource(id = R.string.navigation_rail)
    val navigationRailDrawerIconContentDescription = stringResource(id = R.string.navigation_rail_drawer_icon)
    NavigationRail(
        modifier = Modifier
            .fillMaxHeight()
            .semantics {
                contentDescription = navigationRailContentDescription
            },
    ) {
        NavigationRailItem(
            selected = false,
            onClick = onDrawerClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = navigationRailDrawerIconContentDescription
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
                    val contentDescription = if (selected) {
                        stringResource(id = R.string.navigation_rail_selected_icon, destination.route)
                    } else {
                        stringResource(id = R.string.navigation_rail_unselected_icon, destination.route)
                    }
                    when (icon) {
                        is AdaptiveLayoutIcon.ImageVectorAdaptiveLayoutIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = contentDescription
                        )
                        is AdaptiveLayoutIcon.DrawableResourceAdaptiveLayoutIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = contentDescription
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