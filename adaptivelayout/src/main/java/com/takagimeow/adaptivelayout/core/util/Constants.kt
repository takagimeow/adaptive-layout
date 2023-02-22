package com.takagimeow.adaptivelayout.core.util

import androidx.compose.runtime.staticCompositionLocalOf
import com.takagimeow.adaptivelayout.AdaptiveLayoutContentType

val LocalContentType = staticCompositionLocalOf<AdaptiveLayoutContentType> {
    error("CompositionLocal ContentType not provided")
}