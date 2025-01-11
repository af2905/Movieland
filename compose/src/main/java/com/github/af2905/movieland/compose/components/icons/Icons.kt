package com.github.af2905.movieland.compose.components.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.github.af2905.movieland.compose.theme.AppTheme

@Composable
fun IconVectorFadeLargeView(
    painter: Painter,
    modifier: Modifier = Modifier,
    scaleIcon: Boolean = false,
    enabled: Boolean = true
) {
    VectorElement(
        painter = painter,
        scaleIcon = scaleIcon,
        color = if (enabled) AppTheme.colors.theme.tintFade else AppTheme.colors.type.disable,
        backgroundColor = if (enabled) AppTheme.colors.theme.tintGhost else AppTheme.colors.background.actionRipple,
        modifier = modifier
    )
}

@Composable
fun IconVectorPrimaryLargeView(
    painter: Painter,
    modifier: Modifier = Modifier,
    scaleIcon: Boolean = false,
    enabled: Boolean = true
) {
    VectorElement(
        painter = painter,
        scaleIcon = scaleIcon,
        color = if (enabled) AppTheme.colors.type.inverse else AppTheme.colors.type.disable,
        backgroundColor = if (enabled) AppTheme.colors.theme.tint else AppTheme.colors.background.actionRipple,
        modifier = modifier
    )
}

@Composable
fun IconVectorPrimaryInverseLargeView(
    painter: Painter,
    modifier: Modifier = Modifier,
    scaleIcon: Boolean = false,
    enabled: Boolean = true
) {
    VectorElement(
        painter = painter,
        scaleIcon = scaleIcon,
        color = if (enabled) AppTheme.colors.theme.tint else AppTheme.colors.background.actionRippleInverse,
        backgroundColor = if (enabled) AppTheme.colors.background.card else AppTheme.colors.background.actionRippleInverse,
        modifier = modifier
    )
}

@Composable
private fun VectorElement(
    painter: Painter,
    scaleIcon: Boolean,
    color: Color,
    backgroundColor: Color,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .size(AppTheme.dimens.iconViewLargeSize)
            .background(backgroundColor, CircleShape),
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .then(
                    if (scaleIcon) Modifier.size(AppTheme.dimens.iconViewLargeIconSize)
                    else Modifier
                ),
            tint = color
        )
    }
}

private const val disabledAlpha = 0.3f

@Composable
fun IconImageView(
    painter: Painter,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier.size(AppTheme.dimens.iconViewMediumSize),
        contentScale = ContentScale.Inside,
        alpha = if (enabled) DefaultAlpha else disabledAlpha
    )
}

@Composable
fun IconImageRoundView(
    painter: Painter,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .size(AppTheme.dimens.iconViewMediumSize)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        alpha = if (enabled) DefaultAlpha else disabledAlpha
    )
}

@Composable
fun IconVectorView(
    painter: Painter,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    VectorElement(
        painter = painter,
        color = if (enabled) AppTheme.colors.theme.tint else AppTheme.colors.type.disable,
        backgroundColor = Color.Transparent,
        modifier = modifier
    )
}

@Composable
fun IconVectorFadeView(
    painter: Painter,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    VectorElement(
        painter = painter,
        color = if (enabled) AppTheme.colors.theme.tintFade else AppTheme.colors.type.disable,
        backgroundColor = if (enabled) AppTheme.colors.theme.tintGhost else AppTheme.colors.background.actionRipple,
        modifier = modifier
    )
}

@Composable
fun IconVectorPrimaryView(
    painter: Painter,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    VectorElement(
        painter = painter,
        color = if (enabled) AppTheme.colors.type.inverse else AppTheme.colors.type.disable,
        backgroundColor = if (enabled) AppTheme.colors.theme.tint else AppTheme.colors.background.actionRipple,
        modifier = modifier
    )
}

@Composable
fun IconVectorAlertFadeView(
    painter: Painter,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    VectorElement(
        painter = painter,
        color = if (enabled) AppTheme.colors.background.alert else AppTheme.colors.type.disable,
        backgroundColor = if (enabled) AppTheme.colors.background.ghostAlert else AppTheme.colors.background.actionRipple,
        modifier = modifier
    )
}

@Composable
fun IconValueView(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    ValueElement(
        text = text,
        color = if (enabled) AppTheme.colors.theme.tint else AppTheme.colors.type.disable,
        backgroundColor = Color.Transparent,
        modifier = modifier
    )
}

@Composable
fun IconValueFadeView(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    ValueElement(
        text = text,
        color = if (enabled) AppTheme.colors.theme.tintFade else AppTheme.colors.type.disable,
        backgroundColor = if (enabled) AppTheme.colors.theme.tintGhost else AppTheme.colors.background.actionRipple,
        modifier = modifier
    )
}

@Composable
fun IconValuePrimaryView(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    ValueElement(
        text = text,
        color = if (enabled) AppTheme.colors.type.inverse else AppTheme.colors.type.disable,
        backgroundColor = if (enabled) AppTheme.colors.theme.tint else AppTheme.colors.background.actionRipple,
        modifier = modifier
    )
}

@Composable
fun IconVectorPrimaryInverseView(
    painter: Painter,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    VectorElement(
        painter = painter,
        color = if (enabled) AppTheme.colors.theme.tint else AppTheme.colors.background.actionRippleInverse,
        backgroundColor = if (enabled) AppTheme.colors.background.card else AppTheme.colors.background.actionRippleInverse,
        modifier = modifier
    )
}

@Composable
fun IconVectorFadeInverseView(
    painter: Painter,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    VectorElement(
        painter = painter,
        color = if (enabled) AppTheme.colors.type.inverse else AppTheme.colors.background.actionRippleInverse,
        backgroundColor = AppTheme.colors.background.actionRippleInverse,
        modifier = modifier
    )
}

@Composable
private fun VectorElement(
    painter: Painter,
    color: Color,
    backgroundColor: Color,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .size(AppTheme.dimens.iconViewMediumSize)
            .background(backgroundColor, CircleShape),
    ) {
        androidx.compose.material.Icon(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center),
            tint = color
        )
    }
}

@Composable
private fun ValueElement(
    text: String,
    color: Color,
    backgroundColor: Color,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .size(AppTheme.dimens.iconViewMediumSize)
            .background(backgroundColor, CircleShape),
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.Center),
            color = color,
            maxLines = 1,
            style = AppTheme.typography.caption1Medium
        )
    }
}

/*Previews*/

@Preview(showBackground = true)
@Composable
fun IconValueViewPreview() {
    IconValueView(
        text = "42",
        enabled = true
    )
}

@Preview(showBackground = true)
@Composable
fun IconValueFadeViewPreview() {
    IconValueFadeView(
        text = "42",
        enabled = true
    )
}

@Preview(showBackground = true)
@Composable
fun IconValuePrimaryViewPreview() {
    IconValuePrimaryView(
        text = "42",
        enabled = true
    )
}
