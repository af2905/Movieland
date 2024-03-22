package com.github.af2905.movieland.compose

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val dimens: Dimens = Dimens()

@Immutable
data class Dimens(
    // Space
    val space3XS: Dp = 2.dp,
    val space2XS: Dp = 4.dp,
    val spaceXS: Dp = 8.dp,
    val spaceS: Dp = 12.dp,
    val spaceM: Dp = 16.dp,
    val spaceL: Dp = 20.dp,
    val spaceXL: Dp = 24.dp,
    val space2XL: Dp = 32.dp,
    val space3XL: Dp = 40.dp,
    val space4XL: Dp = 48.dp,
    val space5XL: Dp = 52.dp,
    val space6XL: Dp = 72.dp,

    // Elevation
    val elevationXS: Dp = 2.dp,
    val elevationS: Dp = 6.dp,
    val elevationDefault: Dp = 10.dp,
    val elevationL: Dp = 20.dp,

    // Radius
    val radiusXS: Dp = 4.dp,
    val radiusS: Dp = 8.dp,
    val radiusM: Dp = 12.dp,
    val radiusL: Dp = 16.dp,

    // Image
    val imageSizeXS: Dp = 24.dp,
    val imageSizeS: Dp = 50.dp,
    val imageSizeM: Dp = 90.dp,
    val imageSizeL: Dp = 130.dp,

    // Card
    val cardSizeS: Dp = 130.dp,
    val cardSizeM: Dp = 170.dp,
    val cardSizeL: Dp = 200.dp,

    // Stroke
    val stroke3XS: Dp = 0.1.dp,
    val strokeS: Dp = 1.dp,
    val strokeM: Dp = 2.dp,

    // Button
    val buttonHeight: Dp = 48.dp
)