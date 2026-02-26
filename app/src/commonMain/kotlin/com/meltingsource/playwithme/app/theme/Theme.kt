package com.meltingsource.playwithme.app.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Theme {
    object Spacing {
        val small: Dp = 4.dp
        val medium: Dp = 8.dp
        val large: Dp = 16.dp
    }

    object Card {
        val width: Dp = 40.dp
        val height: Dp = 50.dp
        val elevation: Dp = 1.dp
        val shape: Shape = RoundedCornerShape(8.0.dp)
        val color: Color = Color.White
    }

    object Table {
        val tonalElevation: Dp = 2.dp
        val shape: Shape = RoundedCornerShape(16.0.dp)
    }
}