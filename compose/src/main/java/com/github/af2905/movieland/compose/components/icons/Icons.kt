package com.github.af2905.movieland.compose.components.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.github.af2905.movieland.compose.theme.AppTheme

@Composable
fun IconVectorFadeLargeView(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    scaleIcon: Boolean = false,
    enabled: Boolean = true
) {
    VectorElement(
        icon = icon,
        scaleIcon = scaleIcon,
        color = if (enabled) AppTheme.colors.theme.tintFade else AppTheme.colors.type.disable,
        backgroundColor = if (enabled) AppTheme.colors.theme.tintGhost else AppTheme.colors.background.actionRipple,
        modifier = modifier
    )
}

@Composable
fun IconVectorPrimaryLargeView(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    scaleIcon: Boolean = false,
    enabled: Boolean = true
) {
    VectorElement(
        icon = icon,
        scaleIcon = scaleIcon,
        color = if (enabled) AppTheme.colors.type.inverse else AppTheme.colors.type.disable,
        backgroundColor = if (enabled) AppTheme.colors.theme.tint else AppTheme.colors.background.actionRipple,
        modifier = modifier
    )
}

@Composable
fun IconVectorPrimaryInverseLargeView(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    scaleIcon: Boolean = false,
    enabled: Boolean = true
) {
    VectorElement(
        icon = icon,
        scaleIcon = scaleIcon,
        color = if (enabled) AppTheme.colors.theme.tint else AppTheme.colors.background.actionRippleInverse,
        backgroundColor = if (enabled) AppTheme.colors.background.card else AppTheme.colors.background.actionRippleInverse,
        modifier = modifier
    )
}

@Composable
private fun VectorElement(
    icon: ImageVector,
    scaleIcon: Boolean = true,
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
            imageVector = icon,
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
fun IconVectorView(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    VectorElement(
        icon = icon,
        color = if (enabled) AppTheme.colors.theme.tint else AppTheme.colors.type.disable,
        backgroundColor = Color.Transparent,
        modifier = modifier
    )
}

@Composable
fun IconVectorFadeView(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    VectorElement(
        icon = icon,
        color = if (enabled) AppTheme.colors.theme.tintFade else AppTheme.colors.type.disable,
        backgroundColor = if (enabled) AppTheme.colors.theme.tintGhost else AppTheme.colors.background.actionRipple,
        modifier = modifier
    )
}

@Composable
fun IconVectorPrimaryView(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    VectorElement(
        icon = icon,
        color = if (enabled) AppTheme.colors.type.inverse else AppTheme.colors.type.disable,
        backgroundColor = if (enabled) AppTheme.colors.theme.tint else AppTheme.colors.background.actionRipple,
        modifier = modifier
    )
}

@Composable
fun IconVectorAlertFadeView(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    VectorElement(
        icon = icon,
        color = if (enabled) AppTheme.colors.background.alert else AppTheme.colors.type.disable,
        backgroundColor = if (enabled) AppTheme.colors.background.ghostAlert else AppTheme.colors.background.actionRipple,
        modifier = modifier
    )
}

/*Previews*/

@Preview(showBackground = true)
@Composable
fun IconVectorViewPreview() {
    IconVectorView(
        icon = Icons.Outlined.ErrorOutline,
        enabled = true
    )
}

@Preview(showBackground = true)
@Composable
fun IconVectorFadeViewPreview() {
    IconVectorFadeView(
        icon = Icons.Outlined.Info,
        enabled = true
    )
}

@Preview(showBackground = true)
@Composable
fun IconVectorPrimaryViewPreview() {
    IconVectorPrimaryView(
        icon = Icons.Outlined.CheckCircle,
        enabled = true
    )
}
