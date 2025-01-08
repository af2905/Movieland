package com.github.af2905.movieland.compose.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

object CircleBadgeShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = Outline.Generic(
        Path.combine(
            PathOperation.Difference,
            Path().apply {
                addOval(Rect(0f, 0f, size.width, size.height))
            },
            Path().apply {
                addOval(
                    Rect(
                        0.6f * size.width,
                        0.6f * size.height,
                        1.2f * size.width,
                        1.2f * size.height
                    )
                )
            }
        )
    )
}

@Preview
@Composable
private fun CircleBadgeShapeSample() {
    Box(modifier = Modifier.size(40.dp).background(Color.White, CircleBadgeShape))
}