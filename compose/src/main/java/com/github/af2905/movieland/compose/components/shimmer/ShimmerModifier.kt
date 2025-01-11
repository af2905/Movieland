package com.github.af2905.movieland.compose.components.shimmer

import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize

/**
 * Draws shape with [shimmerBrush]
 * @param shape - desired shape of the background
 * */
fun Modifier.shimmerBackground(
    shape: Shape = RectangleShape
): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val shimmerBrush = shimmerBrush(size)
    this
        .onSizeChanged { size = it }
        .background(
            brush = shimmerBrush,
            shape = shape
        )
}