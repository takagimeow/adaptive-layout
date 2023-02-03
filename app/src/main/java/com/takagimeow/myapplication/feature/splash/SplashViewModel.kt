package com.takagimeow.myapplication.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SplashUiState(
    val showError: Boolean = false,
)

class SplashViewModel() : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            SplashUiState(
                showError = false
            )
        )
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    fun onAppStart(navigateToHome: () -> Unit, navigateToOnboarding: () -> Unit) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    showError = false,
                )
            }

            navigateToHome()
        }
    }
}