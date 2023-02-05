package com.takagimeow.adaptivelayout.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsRoute(
    isListAndDetail: Boolean,
) {
    SettingsScreen(
        isListAndDetail = isListAndDetail,
    )
}

@Composable
fun SettingsScreen(
    isListAndDetail: Boolean,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Settings Screen")
    }
}