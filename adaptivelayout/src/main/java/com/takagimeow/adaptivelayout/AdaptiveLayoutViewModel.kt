package com.takagimeow.adaptivelayout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AdaptiveLayoutViewModel(
    contextProvider: ContextProvider,
) : ViewModel() {
    // Check and monitor folding state.
    val devicePostureFlow = WindowInfoTracker.getOrCreate(contextProvider.activity)
        .windowLayoutInfo(contextProvider.activity)
        .flowWithLifecycle(contextProvider.activity.lifecycle) // Supported since lifecycle-viewmodel-compose:2.5.1
        .map { layoutInfo ->
            val foldingFeature =
                layoutInfo.displayFeatures
                    .filterIsInstance<FoldingFeature>()
                    .firstOrNull()
            when {
                isBookPosture(foldingFeature) ->
                    DevicePosture.BookPosture(foldingFeature.bounds)

                isSeparating(foldingFeature) ->
                    DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

                else -> DevicePosture.NormalPosture
            }
        }
        .stateIn(
            scope = contextProvider.activity.lifecycleScope,
            started = SharingStarted.Eagerly,
            initialValue = DevicePosture.NormalPosture
        )
}
