package com.takagimeow.adaptivelayout

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AdaptiveLayout(
    appState: AdaptiveLayoutAppState,
    optionalNavigationDisplayConditions: Boolean = true,
    viewModel: AdaptiveLayoutViewModel,
    activity: ComponentActivity,
    background: @Composable (route: String?, content: @Composable () -> Unit) -> Unit = { _, content -> content() },
    content: @Composable (isListAndDetail: Boolean) -> Unit,
) {

    val devicePostureFlow = viewModel.devicePostureFlow
    val devicePosture = devicePostureFlow.collectAsState().value

    val windowSize = calculateWindowSizeClass(activity)

    AdaptiveLayoutApp(
        appState = appState,
        windowSize = windowSize.widthSizeClass,
        foldingDevicePosture = devicePosture,
        optionalNavigationDisplayConditions = optionalNavigationDisplayConditions,
        background = background,
        content = content,
    )
}