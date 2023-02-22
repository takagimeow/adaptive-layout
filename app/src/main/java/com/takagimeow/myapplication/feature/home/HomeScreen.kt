package com.takagimeow.myapplication.feature.home

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.adaptive.FoldAwareConfiguration
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.takagimeow.adaptivelayout.AdaptiveLayoutContentType
import com.takagimeow.adaptivelayout.core.util.LocalContentType

@Composable
fun HomeRoute() {
    val isListAndDetail = (LocalContentType.current == AdaptiveLayoutContentType.LIST_AND_DETAIL)

    HomeScreen(
        isListAndDetail = isListAndDetail,
    )
}

@Composable
fun HomeScreen(
    isListAndDetail: Boolean,
) {
    val activity = LocalContext.current
    val displayFeatures = calculateDisplayFeatures(activity as Activity)

    if(isListAndDetail) {
        TwoPane(
            first = {
                Card(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Home Screen - First")
                    }
                }
            },
            second = {
                Card(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Home Screen - Second")
                    }
                }
            },
            strategy = HorizontalTwoPaneStrategy(
                splitFraction = 1f / 3f,
            ),
            displayFeatures = displayFeatures,
            foldAwareConfiguration = FoldAwareConfiguration.VerticalFoldsOnly,
            modifier = Modifier.padding(8.dp)
        )
    } else {
        Card(
            modifier = Modifier.padding(8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Home Screen - Single Pane")
            }
        }
    }
}