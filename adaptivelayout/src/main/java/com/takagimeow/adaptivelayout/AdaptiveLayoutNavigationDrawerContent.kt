package com.takagimeow.adaptivelayout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveLayoutNavigationDrawerContent(
    destinations: List<AdaptiveLayoutTopLevelDestination>,
    selectedDestination: String?,
    modifier: Modifier = Modifier,
    onDrawerClicked: () -> Unit = {},
    onNavigate: (AdaptiveLayoutNavigationDestination) -> Unit,
) {
    val navigationDrawerContentDescription = stringResource(id = R.string.navigation_drawer)
    Column(
        modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .padding(24.dp)
            .semantics {
                contentDescription = navigationDrawerContentDescription
            }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

        }

        destinations.forEachIndexed { _, destination ->
            val selected = selectedDestination == destination.destination
            NavigationDrawerItem(
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                icon = {
                    val icon = if (selected) {
                        destination.selectedAdaptiveLayoutIcon
                    } else {
                        destination.unselectedAdaptiveLayoutIcon
                    }
                    val contentDescription = if (selected) {
                        stringResource(id = R.string.navigation_drawer_selected_icon, destination.route)
                    } else {
                        stringResource(id = R.string.navigation_drawer_unselected_icon, destination.route)
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
                label = { Text(stringResource(id = destination.iconTextId)) },
                selected = selected,
                onClick = {
                    onNavigate(destination)
                    onDrawerClicked()
                },
            )
        }
    }
}