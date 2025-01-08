package com.github.af2905.movieland.compose.components.avatars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.compose.theme.CircleBadgeShape


@Composable
fun AvatarDefaultView(
    painter: Painter,
    modifier: Modifier = Modifier,
    badgePainter: Painter? = null
) {
    AvatarDefaultView(
        painter = painter,
        text = null,
        modifier = modifier,
        badgePainter = badgePainter
    )
}

@Composable
fun AvatarDefaultView(
    text: String,
    modifier: Modifier = Modifier,
    badgePainter: Painter? = null
) {
    AvatarDefaultView(
        painter = null,
        text = text,
        modifier = modifier,
        badgePainter = badgePainter
    )
}

@Composable
fun AvatarDefaultView(
    painter: Painter?,
    text: String?,
    modifier: Modifier = Modifier,
    badgePainter: Painter? = null
) {
    Avatar(
        painter = painter,
        text = text,
        modifier = modifier,
        size = AppTheme.dimens.avatarDefaultSize,
        textColor = AppTheme.colors.theme.tintFade,
        textStyle = AppTheme.typography.title3,
        background = AppTheme.colors.theme.tintGhost,
        badgePainter = badgePainter
    )
}

@Composable
fun AvatarLargeView(
    painter: Painter,
    modifier: Modifier = Modifier
) {
    AvatarLargeView(
        painter = painter,
        text = null,
        modifier = modifier
    )
}

@Composable
fun AvatarLargeView(
    text: String,
    modifier: Modifier = Modifier
) {
    AvatarLargeView(
        painter = null,
        text = text,
        modifier = modifier
    )
}

@Composable
fun AvatarLargeView(
    painter: Painter?,
    text: String?,
    modifier: Modifier = Modifier
) {
    Avatar(
        painter = painter,
        text = text,
        modifier = modifier,
        size = AppTheme.dimens.avatarLargeSize,
        textColor = AppTheme.colors.theme.tintFade,
        textStyle = AppTheme.typography.title1,
        background = AppTheme.colors.theme.tintGhost
    )
}

@Composable
fun AvatarActiveDefaultView(
    painter: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AvatarActiveDefaultView(
        painter = painter,
        text = null,
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun AvatarActiveDefaultView(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AvatarActiveDefaultView(
        painter = null,
        text = text,
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun AvatarActiveDefaultView(
    painter: Painter?,
    text: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Avatar(
        painter = painter,
        text = text,
        modifier = modifier,
        size = AppTheme.dimens.avatarDefaultSize,
        textColor = AppTheme.colors.theme.tint,
        textStyle = AppTheme.typography.title3,
        background = AppTheme.colors.background.card,
        elevation = AppTheme.dimens.elevationXS,
        onClick = onClick
    )
}

@Composable
fun AvatarActiveLargeView(
    painter: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AvatarActiveLargeView(
        painter = painter,
        text = null,
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun AvatarActiveLargeView(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AvatarActiveLargeView(
        painter = null,
        text = text,
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun AvatarActiveLargeView(
    painter: Painter?,
    text: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Avatar(
        painter = painter,
        text = text,
        modifier = modifier,
        size = AppTheme.dimens.avatarLargeSize,
        textColor = AppTheme.colors.theme.tint,
        textStyle = AppTheme.typography.title1,
        background = AppTheme.colors.background.card,
        elevation = AppTheme.dimens.elevationXS,
        onClick = onClick
    )
}

@Composable
private fun Avatar(
    painter: Painter?,
    text: String?,
    modifier: Modifier,
    size: Dp,
    textColor: Color,
    textStyle: TextStyle,
    background: Color,
    badgePainter: Painter? = null,
    elevation: Dp = 0.dp,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        Box(
            modifier = Modifier
                .clip(if (badgePainter != null) CircleBadgeShape else CircleShape)
                .shadow(elevation)
                .background(background)
                .size(size)
                .clickable(
                    enabled = onClick != null,
                    onClick = { onClick?.invoke() }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (painter != null) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    text = text?.firstOrNull()?.toString() ?: "",
                    color = textColor,
                    style = textStyle
                )
            }
        }
        if (badgePainter != null) {
            Image(
                painter = badgePainter,
                contentDescription = null,
                modifier = Modifier
                    .size(AppTheme.dimens.avatarBadgeSize)
                    .offset(
                        x = AppTheme.dimens.avatarBadgeOffset,
                        y = AppTheme.dimens.avatarBadgeOffset
                    ),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(AppTheme.colors.theme.tint, BlendMode.SrcIn),
            )
        }
    }
}