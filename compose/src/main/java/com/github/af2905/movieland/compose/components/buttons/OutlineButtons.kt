package com.github.af2905.movieland.compose.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.theme.AppTheme

@Composable
fun OutlineButtonView(
    text: String,
    modifier: Modifier = Modifier,
    isProgress: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedProgressButton(
        text = text,
        modifier = modifier,
        isProgress = isProgress,
        enabled = enabled,
        color = AppTheme.colors.theme.tint,
        onClick = onClick
    )
}

@Composable
internal fun OutlinedProgressButton(
    text: String,
    modifier: Modifier,
    isProgress: Boolean,
    enabled: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .height(AppTheme.dimens.buttonHeight),
        onClick = { if (!isProgress) onClick() },
        border = BorderStroke(
            AppTheme.dimens.buttonOutlineStrokeWidth,
            if (enabled) color else AppTheme.colors.background.actionRipple
        ),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(AppTheme.dimens.radiusS),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = color,
            disabledContentColor = AppTheme.colors.type.disable
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        enabled = enabled
    ) {
        if (isProgress) {
            ButtonProgress(color = color)
        } else {
            ButtonText(text)
        }
    }
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

/*Previews*/

@Preview(showBackground = true)
@Composable
fun PreviewOutlineButtonView() {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlineButtonView(
            text = "Outline Button",
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlineButtonView(
            text = "Disabled",
            enabled = false,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlineButtonView(
            text = "Loading",
            isProgress = true,
            onClick = {}
        )
    }
}
