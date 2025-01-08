package com.github.af2905.movieland.compose.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.theme.AppTheme


@Composable
fun FlatButtonView(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    FlatButton(
        text = text,
        modifier = modifier,
        buttonsColors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = AppTheme.colors.theme.tint,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = AppTheme.colors.type.disable
        ),
        enabled = enabled,
        onClick = onClick
    )
}

@Composable
fun FlatAlertButtonView(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    FlatButton(
        text = text,
        modifier = modifier,
        buttonsColors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = AppTheme.colors.type.alert,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = AppTheme.colors.type.disable
        ),
        enabled = enabled,
        onClick = onClick
    )
}

@Composable
fun FlatButtonInverseView(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    FlatButton(
        text = text,
        modifier = modifier,
        buttonsColors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = AppTheme.colors.type.secondaryInverse,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = AppTheme.colors.type.ghostInverse
        ),
        enabled = enabled,
        onClick = onClick
    )
}

@Composable
fun FlatSecondaryButtonView(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    FlatButton(
        text = text,
        modifier = modifier,
        buttonsColors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = AppTheme.colors.type.ghost,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = AppTheme.colors.type.disable
        ),
        enabled = enabled,
        onClick = onClick
    )
}

@Composable
internal fun FlatButton(
    text: String,
    modifier: Modifier,
    buttonsColors: ButtonColors,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .height(AppTheme.dimens.buttonFlatHeight)
            .clip(RoundedCornerShape(AppTheme.dimens.radiusS)),
        contentPadding = PaddingValues(0.dp),
        elevation = null,
        enabled = enabled,
        colors = buttonsColors
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(horizontal = AppTheme.dimens.spaceXS),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = AppTheme.typography.caption1Medium.copy(color = Color.Unspecified)
        )
    }
}