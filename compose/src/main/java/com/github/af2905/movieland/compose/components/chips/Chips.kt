package com.github.af2905.movieland.compose.components.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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

enum class ChipViewStyle {
    Tint,
    FadeTint,
    Alert,
    Success,
    Inverse;

    @Composable
    internal fun getTextColor(): Color = when (this) {
        Tint -> AppTheme.colors.type.inverse
        FadeTint -> AppTheme.colors.theme.tint
        Alert -> AppTheme.colors.type.inverse
        Success -> AppTheme.colors.type.inverse
        Inverse -> AppTheme.colors.theme.tint
    }

    @Composable
    internal fun getDisabledTextColor(): Color = when (this) {
        Tint,
        FadeTint,
        Alert,
        Success -> AppTheme.colors.type.disable

        Inverse -> AppTheme.colors.type.ghostInverse
    }

    @Composable
    internal fun getBackgroundColor(): Color = when (this) {
        Tint -> AppTheme.colors.theme.tint
        FadeTint -> AppTheme.colors.theme.tintGhost
        Alert -> AppTheme.colors.background.alert
        Success -> AppTheme.colors.background.success
        Inverse -> AppTheme.colors.background.card
    }

    @Composable
    internal fun getDisabledBackgroundColor(): Color = when (this) {
        Tint,
        FadeTint,
        Alert,
        Success -> AppTheme.colors.background.actionRipple

        Inverse -> AppTheme.colors.background.actionRippleInverse
    }
}

enum class ChipIconViewStyle {
    InverseTint,
    InverseDark;

    @Composable
    internal fun getTextColor(): Color = when (this) {
        InverseTint -> AppTheme.colors.theme.tint
        InverseDark -> AppTheme.colors.background.black
    }

    @Composable
    internal fun getDisabledTextColor(): Color = when (this) {
        InverseTint -> AppTheme.colors.type.disable
        InverseDark -> AppTheme.colors.type.ghostInverse
    }

    @Composable
    internal fun getBackgroundColor(): Color = when (this) {
        InverseTint -> AppTheme.colors.theme.tintCard
        InverseDark -> AppTheme.colors.background.white
    }

    @Composable
    internal fun getDisabledBackgroundColor(): Color = when (this) {
        InverseTint -> AppTheme.colors.background.actionRipple
        InverseDark -> AppTheme.colors.background.actionRippleInverse
    }
}

@Composable
fun ChipView(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLarge: Boolean = false,
    style: ChipViewStyle = ChipViewStyle.Tint,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .height(
                if (isLarge) AppTheme.dimens.chipLargeHeight else AppTheme.dimens.chipHeight
            )
            .clip(
                RoundedCornerShape(
                    if (isLarge) AppTheme.dimens.radiusL else AppTheme.dimens.radiusM
                )
            )
            .background(
                if (enabled) style.getBackgroundColor() else style.getDisabledBackgroundColor()
            )
            .then(
                if (onClick == null) Modifier
                else Modifier.clickable(
                    onClick = onClick,
                    enabled = enabled
                )
            )
            .padding(
                horizontal = if (isLarge) AppTheme.dimens.spaceS else AppTheme.dimens.spaceXS
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (enabled) style.getTextColor() else style.getDisabledTextColor(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = AppTheme.typography.caption1Medium
        )
    }
}

@Composable
fun ChipActionView(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onCancelClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .height(AppTheme.dimens.chipLargeHeight)
            .clip(RoundedCornerShape(AppTheme.dimens.radiusL))
            .background(
                if (enabled) AppTheme.colors.theme.tint
                else AppTheme.colors.background.actionRipple
            )
            .clickable(
                onClick = onClick,
                enabled = enabled
            )
            .padding(
                start = AppTheme.dimens.spaceS,
                end = if (enabled) AppTheme.dimens.spaceXS else AppTheme.dimens.spaceS
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = if (enabled) AppTheme.colors.type.inverse else AppTheme.colors.type.disable,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = AppTheme.typography.caption1Medium
        )
        if (enabled) {
            Box(
                modifier = Modifier
                    .padding(start = AppTheme.dimens.spaceXS)
                    .size(AppTheme.dimens.spaceM)
                    .clip(CircleShape)
                    .background(AppTheme.colors.background.border)
                    .clickable(
                        onClick = onCancelClick,
                        enabled = enabled
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = AppTheme.colors.type.inverse
                )
            }
        }
    }
}

@Composable
fun ChipStrokeView(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(AppTheme.dimens.chipLargeHeight)
            .clip(RoundedCornerShape(AppTheme.dimens.radiusL))
            .border(
                width = AppTheme.dimens.chipStrokeWidth,
                color = if (enabled) AppTheme.colors.theme.tint
                else AppTheme.colors.type.disable,
                shape = RoundedCornerShape(AppTheme.dimens.radiusL)
            )
            .clickable(
                onClick = onClick,
                enabled = enabled
            )
            .padding(horizontal = AppTheme.dimens.spaceS),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (enabled) AppTheme.colors.theme.tint else AppTheme.colors.type.disable,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = AppTheme.typography.caption1Medium
        )
    }
}

@Composable
fun ChipChoiceView(
    text: String,
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(AppTheme.dimens.chipLargeHeight)
            .clip(RoundedCornerShape(AppTheme.dimens.radiusL))
            .background(
                if (!enabled) AppTheme.colors.background.actionRipple
                else if (checked) AppTheme.colors.theme.tint else AppTheme.colors.theme.tintGhost
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
                enabled = enabled
            )
            .padding(horizontal = AppTheme.dimens.spaceS),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (!enabled) AppTheme.colors.type.disable
            else if (checked) AppTheme.colors.type.inverse else AppTheme.colors.theme.tint,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = AppTheme.typography.caption1Medium
        )
    }
}

@Composable
fun ChipIconView(
    painter: Painter,
    modifier: Modifier = Modifier,
    text: String? = null,
    enabled: Boolean = true,
    style: ChipIconViewStyle = ChipIconViewStyle.InverseTint,
    onClick: () -> Unit = {}
) {
    ChipIcon(
        painter = painter,
        modifier = modifier,
        text = text,
        enabled = enabled,
        textColor = style.getTextColor(),
        disabledTextColor = style.getDisabledTextColor(),
        backgroundColor = style.getBackgroundColor(),
        disabledBackgroundColor = style.getDisabledBackgroundColor(),
        onClick = onClick
    )
}

@Composable
fun ChipIconView(
    painter: Painter,
    modifier: Modifier = Modifier,
    text: String? = null,
    enabled: Boolean = true,
    textColor: Color = Color.Unspecified,
    disabledTextColor: Color = Color.Unspecified,
    backgroundColor: Color = Color.Unspecified,
    disabledBackgroundColor: Color = Color.Unspecified,
    onClick: () -> Unit = {}
) {
    ChipIcon(
        painter = painter,
        modifier = modifier,
        text = text,
        enabled = enabled,
        textColor = textColor,
        disabledTextColor = disabledTextColor,
        backgroundColor = backgroundColor,
        disabledBackgroundColor = disabledBackgroundColor,
        onClick = onClick
    )
}

@Composable
internal fun ChipIcon(
    painter: Painter,
    modifier: Modifier,
    text: String?,
    enabled: Boolean,
    textColor: Color,
    disabledTextColor: Color,
    backgroundColor: Color,
    disabledBackgroundColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .widthIn(max = AppTheme.dimens.chipIconMaxWidth)
            .height(AppTheme.dimens.chipLargeHeight)
            .clip(RoundedCornerShape(AppTheme.dimens.radiusL))
            .background(if (enabled) backgroundColor else disabledBackgroundColor)
            .clickable(
                onClick = onClick,
                enabled = enabled
            )
            .padding(horizontal = AppTheme.dimens.spaceS),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            modifier = Modifier.size(AppTheme.dimens.spaceM),
            contentDescription = null,
            tint = if (enabled) textColor else disabledTextColor
        )
        if (!text.isNullOrEmpty()) {
            Text(
                text = text,
                modifier = Modifier
                    .padding(start = AppTheme.dimens.spaceXS),
                color = if (enabled) textColor else disabledTextColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = AppTheme.typography.caption1Medium
            )
        }
    }
}

/*Previews*/

@Preview(showBackground = true)
@Composable
fun PreviewChipView() {
    Column(modifier = Modifier.padding(16.dp)) {
        ChipView(
            text = "Tint",
            style = ChipViewStyle.Tint,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        ChipView(
            text = "Alert",
            style = ChipViewStyle.Alert,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        ChipView(
            text = "Disabled",
            enabled = false,
            style = ChipViewStyle.Success,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChipActionView() {
    ChipActionView(
        text = "Action",
        onCancelClick = {},
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewChipStrokeView() {
    Column(modifier = Modifier.padding(16.dp)) {
        ChipStrokeView(
            text = "Outlined",
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        ChipStrokeView(
            text = "Disabled",
            enabled = false,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChipChoiceView() {
    Column(modifier = Modifier.padding(16.dp)) {
        ChipChoiceView(
            text = "Selected",
            checked = true,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        ChipChoiceView(
            text = "Not Selected",
            checked = false,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        ChipChoiceView(
            text = "Disabled",
            enabled = false,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChipIconView() {
    Column(modifier = Modifier.padding(16.dp)) {
        ChipIconView(
            painter = painterResource(id = android.R.drawable.ic_menu_camera),
            text = "With Icon",
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        ChipIconView(
            painter = painterResource(id = android.R.drawable.ic_menu_camera),
            text = "Disabled",
            enabled = false,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        ChipIconView(
            painter = painterResource(id = android.R.drawable.ic_menu_camera),
            text = null,
            onClick = {}
        )
    }
}

