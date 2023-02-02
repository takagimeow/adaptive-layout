package com.takagimeow.adaptivelayout

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AdaptiveLayoutIcon {
    data class ImageVectorAdaptiveLayoutIcon(val imageVector: ImageVector) : AdaptiveLayoutIcon()
    data class DrawableResourceAdaptiveLayoutIcon(@DrawableRes val id: Int) : AdaptiveLayoutIcon()
}