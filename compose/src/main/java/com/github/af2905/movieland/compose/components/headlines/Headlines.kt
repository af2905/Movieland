package com.github.af2905.movieland.compose.components.headlines

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.github.af2905.movieland.compose.theme.AppTheme

private const val rightElementWidthPercent = 0.5f

@Composable
fun HeadlinePrimaryView(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE
) {
    Headline(
        text = text,
        modifier = modifier,
        maxLines = maxLines,
        textColor = AppTheme.colors.type.secondary,
        textStyle = AppTheme.typography.title3,
        minHeight = AppTheme.dimens.headlinePrimaryDefaultMinHeight,
        paddingTop = AppTheme.dimens.spaceM,
        paddingBottom = AppTheme.dimens.spaceXS
    )
}

@Composable
fun HeadlinePrimaryActionView(
    text: String,
    modifier: Modifier = Modifier,
    action: String? = null,
    maxLines: Int = Int.MAX_VALUE,
    onClick: () -> Unit = {},
) {
    var rightElementMaxWidth by remember { mutableFloatStateOf(Float.MAX_VALUE) }
    Headline(
        text = text,
        modifier = modifier,
        maxLines = maxLines,
        textColor = AppTheme.colors.type.secondary,
        textStyle = AppTheme.typography.title3,
        minHeight = AppTheme.dimens.headlinePrimaryDefaultMinHeight,
        paddingTop = AppTheme.dimens.spaceM,
        paddingBottom = AppTheme.dimens.spaceXS,
        onRightWidth = { width -> rightElementMaxWidth = width }
    ) {
        if (!action.isNullOrEmpty()) {
            HeadlineAction(
                action = action,
                color = AppTheme.colors.theme.tint,
                maxWidth = Dp(rightElementMaxWidth),
                onClick = onClick
            )
        }
    }
}

@Composable
internal fun Headline(
    text: String,
    modifier: Modifier,
    maxLines: Int,
    textColor: Color,
    textStyle: TextStyle,
    minHeight: Dp,
    paddingTop: Dp,
    paddingBottom: Dp,
    onClick: (() -> Unit)? = null,
    onRightWidth: (Float) -> Unit = {},
    rightElement: @Composable (RowScope.() -> Unit)? = null
) {
    val density = LocalDensity.current.density
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = minHeight)
            .clickable(
                onClick = { onClick?.invoke() },
                enabled = onClick != null
            )
            .padding(
                start = AppTheme.dimens.spaceM,
                end = AppTheme.dimens.spaceM
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .padding(top = paddingTop)
                .onGloballyPositioned {
                    onRightWidth(rightElementWidthPercent * it.size.width / density)
                }
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = paddingBottom)
                    .alignBy(FirstBaseline),
                color = textColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = maxLines,
                style = textStyle
            )
            rightElement?.let { it() }
        }
    }
}

@Composable
internal fun RowScope.HeadlineAction(
    action: String,
    color: Color,
    maxWidth: Dp,
    onClick: () -> Unit
) {
    androidx.compose.material.Text(
        text = action,
        modifier = Modifier
            .padding(start = AppTheme.dimens.spaceM)
            .widthIn(max = maxWidth)
            .alignBy(FirstBaseline)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = AppTheme.typography.bodyMedium
    )
}

/*Previews*/

@Preview(showBackground = true, name = "HeadlinePrimaryView Preview")
@Composable
fun PreviewHeadlinePrimaryView() {
    AppTheme {
        HeadlinePrimaryView(
            text = "This is a headline",
            maxLines = 2,
            modifier = Modifier.padding(AppTheme.dimens.spaceM)
        )
    }
}

@Preview(showBackground = true, name = "HeadlinePrimaryActionView with Action")
@Composable
fun PreviewHeadlinePrimaryActionViewWithAction() {
    AppTheme {
        HeadlinePrimaryActionView(
            text = "This is a headline with an action",
            action = "Click Me",
            maxLines = 2,
            onClick = { /* Handle Action */ },
            modifier = Modifier.padding(AppTheme.dimens.spaceM)
        )
    }
}

@Preview(showBackground = true, name = "HeadlinePrimaryActionView without Action")
@Composable
fun PreviewHeadlinePrimaryActionViewWithoutAction() {
    AppTheme {
        HeadlinePrimaryActionView(
            text = "This is a headline without an action",
            action = null,
            maxLines = 1,
            modifier = Modifier.padding(AppTheme.dimens.spaceM)
        )
    }
}
