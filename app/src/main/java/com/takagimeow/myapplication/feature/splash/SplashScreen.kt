package com.takagimeow.myapplication.feature.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

private const val SPLASH_TIMEOUT = 1000L

@Composable
fun SplashRoute(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = SplashViewModel(),
    navigateToHome: () -> Unit,
    navigateToOnboarding: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(true) {
        delay(SPLASH_TIMEOUT)
        viewModel.onAppStart(
            navigateToHome,
            navigateToOnboarding,
        )
    }

    SplashScreen(
        modifier = modifier,
        showError = uiState.showError
    )
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    showError: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showError) {

        } else {
            CircularProgressIndicator()
        }
    }
}