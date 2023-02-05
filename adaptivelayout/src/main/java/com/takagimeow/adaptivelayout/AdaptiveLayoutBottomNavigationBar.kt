package com.takagimeow.adaptivelayout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
fun AdaptiveLayoutBottomNavigationBar(
    destinations: List<AdaptiveLayoutTopLevelDestination>,
    currentRoute: String?,
    onNavigate: (AdaptiveLayoutNavigationDestination) -> Unit,
) {
    val bottomNavigationBarContentDescription = stringResource(id = R.string.bottom_navigation_bar)
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = bottomNavigationBarContentDescription
            }
    ) {
        destinations.forEachIndexed { _, destination ->
            val selected = currentRoute == destination.destination
            NavigationBarItem(
                modifier = Modifier.testTag(
                    destination.destination
                ),
                icon = {
                    val icon = if (selected) {
                        destination.selectedAdaptiveLayoutIcon
                    } else {
                        destination.unselectedAdaptiveLayoutIcon
                    }
                    val contentDescription = if (selected) {
                        stringResource(id = R.string.bottom_navigation_bar_selected_icon, destination.route)
                    } else {
                        stringResource(id = R.string.bottom_navigation_bar_unselected_icon, destination.route)
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
                label = {
                    Text(stringResource(id = destination.iconTextId))
                },
                selected = selected,
                onClick = {
                    onNavigate(destination)
                },
            )
        }
    }
}