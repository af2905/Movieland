package com.github.af2905.movieland.compose.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.theme.AppTheme

@Composable
fun VerticalButtonView(
    text: String,
    painter: Painter,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    iconColor: Color = AppTheme.colors.theme.tint,
    onClick: () -> Unit
) {
    VerticalButton(
        text = text,
        painter = painter,
        modifier = modifier,
        enabled = enabled,
        textColor = AppTheme.colors.type.primary,
        iconColor = iconColor,
        iconSize = AppTheme.dimens.imageSizeS,
        onClick = onClick
    )
}

@Composable
fun VerticalButtonLargeView(
    text: String,
    painter: Painter,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = AppTheme.colors.theme.tintGhost,
    iconColor: Color = AppTheme.colors.theme.tintFade,
    textColor: Color = AppTheme.colors.type.primary,
    onClick: () -> Unit
) {
    VerticalButton(
        text = text,
        painter = painter,
        modifier = modifier,
        enabled = enabled,
        textColor = textColor,
        iconColor = iconColor,
        backgroundColor = backgroundColor,
        iconSize = AppTheme.dimens.imageSizeL,
        onClick = onClick
    )
}

@Composable
internal fun VerticalButton(
    text: String,
    painter: Painter,
    modifier: Modifier,
    enabled: Boolean,
    textColor: Color,
    iconColor: Color,
    iconSize: Dp,
    backgroundColor: Color? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(AppTheme.dimens.radiusS)),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(AppTheme.dimens.spaceXS)
        ) {
            Icon(
                modifier = Modifier
                    .then(
                        if (backgroundColor == null) Modifier
                        else Modifier
                            .background(
                                color = if (enabled) backgroundColor
                                else AppTheme.colors.background.actionRipple,
                                shape = CircleShape
                            )
                            .padding(AppTheme.dimens.spaceS)
                    )
                    .size(iconSize),
                painter = painter,
                tint = if (enabled) iconColor else AppTheme.colors.type.disable,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceXS))
            Text(
                text = text,
                color = if (enabled) textColor else AppTheme.colors.type.disable,
                style = AppTheme.typography.caption1
            )
        }
    }
}