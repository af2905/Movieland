package com.github.af2905.movieland.compose.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Dimens(
    // Spaces
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

    // Elevations
    val elevationXS: Dp = 2.dp,
    val elevationS: Dp = 6.dp,
    val elevationDefault: Dp = 10.dp,
    val elevationL: Dp = 20.dp,

    // Radius
    val radiusXS: Dp = 4.dp,
    val radiusS: Dp = 8.dp,
    val radiusM: Dp = 12.dp,
    val radiusL: Dp = 16.dp,
    val radiusDialog: Dp = 14.dp,
    val radiusNotice: Dp = 20.dp,
    val radiusInformer: Dp = 20.dp,
    val radiusTip: Dp = 20.dp,
    val radiusBubble: Dp = 24.dp,

    // Buttons
    val buttonHeight: Dp = 48.dp,
    val buttonFlatHeight: Dp = 40.dp,
    val buttonProgressSize: Dp = 20.dp,
    val buttonProgressStrokeWidth: Dp = 2.dp,
    val buttonOutlineStrokeWidth: Dp = 2.dp,
    val buttonIconSize: Dp = 20.dp,

    // Images
    val imageSizeS: Dp = 24.dp,
    val imageSizeM: Dp = 40.dp,
    val imageSizeL: Dp = 64.dp,
    val imageBadgeSizeS: Dp = 12.dp,
    val imageBadgeSizeM: Dp = 20.dp,

    // Avatars
    val avatarDefaultSize: Dp = 40.dp,
    val avatarLargeSize: Dp = 64.dp,
    val avatarBadgeSize: Dp = 16.dp,
    val avatarBadgeOffset: Dp = 4.dp,

    // BottomBar
    val itemWidth: Dp = 58.dp,
    val iconBottomBarSize: Dp = 20.dp,

    // IconViews
    val iconViewMediumSize: Dp = 40.dp,
    val iconViewLargeSize: Dp = 64.dp,
    val iconViewLargeIconSize: Dp = 40.dp,

    // ListItems
    val listItemDefaultSize: Dp = 56.dp,
    val listItemLargeSize: Dp = 72.dp,
    val dividerHeight: Dp = 1.dp,
    val progressHeight: Dp = 2.dp,
    val itemListTextView: Dp = 40.dp,

    // Headlines
    val headlinePrimaryLargeMinHeight: Dp = 64.dp,
    val headlineSecondaryLargeMinHeight: Dp = 56.dp,
    val headlinePrimaryDefaultMinHeight: Dp = 48.dp,
    val headlineSecondaryDefaultMinHeight: Dp = 34.dp,
    val headlinePrimarySmallMinHeight: Dp = 40.dp,
    val headlineSecondaryLargePaddingTop: Dp = 30.dp,

    // Tips
    val tipHeight: Dp = 64.dp,

    // Chips
    val chipHeight: Dp = 24.dp,
    val chipLargeHeight: Dp = 32.dp,
    val chipStrokeWidth: Dp = 1.dp,
    val chipIconMaxWidth: Dp = 180.dp,

    // Dialogs
    val alertDialogWidth: Dp = 328.dp,
    val alertDialogHeight: Dp = 512.dp,
    val alertDialogWidthProportion: Float = 0.912f,
    val alertDialogHeightProportion: Float = 0.8f,

    // Tooltips
    val tooltipMinWidth: Dp = 80.dp,
    val tooltipMaxWidth: Dp = 264.dp,
    val tooltipArrowWidth: Dp = 11.dp,
    val tooltipArrowHeight: Dp = 6.dp,
    val tooltipMinHorizontalPadding: Dp = 8.dp,

    // Star size
    val starSize: Dp = 16.dp
)

internal val dimens: Dimens = Dimens()

internal val dimens600: Dimens = Dimens(
    alertDialogWidthProportion = 0.72f
)