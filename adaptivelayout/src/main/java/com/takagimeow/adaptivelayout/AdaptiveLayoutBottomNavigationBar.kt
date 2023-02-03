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

@Composable
fun AdaptiveLayoutBottomNavigationBar(
    destinations: List<AdaptiveLayoutTopLevelDestination>,
    currentRoute: String?,
    onNavigate: (AdaptiveLayoutNavigationDestination) -> Unit,
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
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