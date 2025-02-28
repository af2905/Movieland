package com.github.af2905.movieland.compose.components.buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.theme.AppTheme

@Composable
fun PrimaryButtonView(
    text: String,
    modifier: Modifier = Modifier,
    isProgress: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    ProgressButton(
        text = text,
        modifier = modifier,
        buttonsColors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.theme.tint,
            contentColor = AppTheme.colors.type.inverse,
            disabledContainerColor = AppTheme.colors.background.actionRipple,
            disabledContentColor = AppTheme.colors.type.disable
        ),
        isProgress = isProgress,
        enabled = enabled,
        onClick = onClick
    )
}

@Composable
fun PrimaryButtonInverseView(
    text: String,
    modifier: Modifier = Modifier,
    isProgress: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    ProgressButton(
        text = text,
        modifier = modifier,
        buttonsColors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.background.card,
            contentColor = AppTheme.colors.theme.tint,
            disabledContainerColor = AppTheme.colors.background.actionRippleInverse,
            disabledContentColor = AppTheme.colors.type.ghostInverse
        ),
        isProgress = isProgress,
        enabled = enabled,
        onClick = onClick
    )
}

@Composable
fun SecondaryButtonView(
    text: String,
    modifier: Modifier = Modifier,
    isProgress: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    ProgressButton(
        text = text,
        modifier = modifier,
        buttonsColors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.theme.tintGhost,
            contentColor = AppTheme.colors.theme.tint,
            disabledContainerColor = AppTheme.colors.background.actionRipple,
            disabledContentColor = AppTheme.colors.type.disable
        ),
        isProgress = isProgress,
        enabled = enabled,
        onClick = onClick
    )
}

@Composable
fun PrimaryIconColoredButton(
    text: String,
    painter: Painter,
    modifier: Modifier = Modifier,
    isProgress: Boolean = false,
    enabled: Boolean = true,
    backgroundColor: Color,
    contentColor: Color = AppTheme.colors.type.inverse,
    onClick: () -> Unit
) {
    ButtonWithIcon(
        text = text,
        painter = painter,
        modifier = modifier,
        buttonsColors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = AppTheme.colors.background.actionRipple,
            disabledContentColor = AppTheme.colors.type.disable
        ),
        isProgress = isProgress,
        enabled = enabled,
        onClick = onClick
    )
}

@Composable
internal fun ButtonWithIcon(
    text: String,
    painter: Painter,
    modifier: Modifier,
    buttonsColors: ButtonColors,
    isProgress: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = { if (!isProgress) onClick() },
        modifier = modifier
            .clip(RoundedCornerShape(AppTheme.dimens.radiusS))
            .width(IntrinsicSize.Max)
            .height(AppTheme.dimens.buttonHeight),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        contentPadding = PaddingValues(0.dp),
        enabled = enabled,
        colors = buttonsColors
    ) {
        if (isProgress) {
            ButtonProgress(color = buttonsColors.contentColor)
        } else {
            ButtonContent(text, painter)
        }
    }
}

@Composable
private fun ButtonContent(text: String, painter: Painter) {
    Box(
        modifier = Modifier
            .padding(AppTheme.dimens.spaceS)
            .fillMaxWidth()
    ) {
        androidx.compose.material.Icon(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(AppTheme.dimens.buttonIconSize)
        )
        androidx.compose.material.Text(
            text = text,
            modifier = Modifier
                .padding(horizontal = AppTheme.dimens.space3XL)
                .align(Alignment.Center),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = AppTheme.typography.bodyMedium.copy(color = Color.Unspecified)
        )
    }
}

/*Previews*/

@Preview(showBackground = true)
@Composable
fun PreviewPrimaryButtonView() {
    Column(modifier = Modifier.padding(16.dp)) {
        PrimaryButtonView(
            text = "Primary Button",
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        PrimaryButtonView(
            text = "Loading...",
            isProgress = true,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        PrimaryButtonView(
            text = "Disabled",
            enabled = false,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPrimaryButtonInverseView() {
    Column(modifier = Modifier.padding(16.dp)) {
        PrimaryButtonInverseView(
            text = "Inverse Button",
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        PrimaryButtonInverseView(
            text = "Loading...",
            isProgress = true,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        PrimaryButtonInverseView(
            text = "Disabled",
            enabled = false,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSecondaryButtonView() {
    Column(modifier = Modifier.padding(16.dp)) {
        SecondaryButtonView(
            text = "Secondary Button",
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        SecondaryButtonView(
            text = "Loading...",
            isProgress = true,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        SecondaryButtonView(
            text = "Disabled",
            enabled = false,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPrimaryIconColoredButton() {
    val painter = painterResource(id = android.R.drawable.ic_menu_camera)
    Column(modifier = Modifier.padding(16.dp)) {
        PrimaryIconColoredButton(
            text = "Button with Icon",
            painter = painter,
            backgroundColor = AppTheme.colors.theme.tint,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        PrimaryIconColoredButton(
            text = "Loading...",
            painter = painter,
            isProgress = true,
            backgroundColor = AppTheme.colors.theme.tint,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        PrimaryIconColoredButton(
            text = "Disabled",
            painter = painter,
            enabled = false,
            backgroundColor = AppTheme.colors.theme.tint,
            onClick = {}
        )
    }
}
