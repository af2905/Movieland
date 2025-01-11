package com.github.af2905.movieland.compose.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.theme.AppTheme

@Composable
internal fun ProgressButton(
    text: String,
    modifier: Modifier,
    buttonsColors: ButtonColors,
    isProgress: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = { if (!isProgress) onClick() },
        modifier = modifier
            .height(AppTheme.dimens.buttonHeight)
            .clip(RoundedCornerShape(AppTheme.dimens.radiusS)),
        elevation = null,
        contentPadding = PaddingValues(0.dp),
        enabled = enabled,
        colors = buttonsColors
    ) {
        if (isProgress) {
            ButtonProgress(color = buttonsColors.contentColor)
        } else {
            ButtonText(text)
        }
    }
}

@Composable
internal fun ButtonProgress(color: Color) {
    CircularProgressIndicator(
        modifier = Modifier
            .padding(horizontal = AppTheme.dimens.spaceS)
            .size(AppTheme.dimens.buttonProgressSize),
        strokeWidth = AppTheme.dimens.buttonProgressStrokeWidth,
        color = color
    )
}

@Composable
private fun ButtonText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .padding(horizontal = AppTheme.dimens.spaceM),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = AppTheme.typography.bodyMedium.copy(color = Color.Unspecified)
    )
}