package com.takagimeow.adaptivelayout.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeRoute(
    isListAndDetail: Boolean,
) {
    HomeScreen(
        isListAndDetail = isListAndDetail,
    )
}

@Composable
fun HomeScreen(
    isListAndDetail: Boolean,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Home Screen")
    }
}