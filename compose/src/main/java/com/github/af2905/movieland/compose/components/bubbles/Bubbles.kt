package com.github.af2905.movieland.compose.components.bubbles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.theme.AppTheme
import kotlin.math.ceil

@Composable
fun BubbleDefaultView(
    text: String,
    modifier: Modifier = Modifier,
    isSelectable: Boolean = true,
    onClick: (Int) -> Unit = {}
) {
    BubbleDefaultView(
        text = AnnotatedString(text),
        modifier = modifier,
        isSelectable = isSelectable,
        onClick = onClick
    )
}

@Composable
fun BubbleDefaultView(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    isSelectable: Boolean = true,
    onClick: (Int) -> Unit = {}
) {
    Bubble(
        text = text,
        modifier = modifier,
        isSelectable = isSelectable,
        isIncome = true,
        backgroundColor = AppTheme.colors.background.ghost,
        textColor = AppTheme.colors.type.primary,
        onClick = onClick
    )
}

@Composable
fun BubbleIncomeView(
    text: String,
    modifier: Modifier = Modifier,
    isSelectable: Boolean = true,
    onClick: (Int) -> Unit = {}
) {
    BubbleIncomeView(
        text = AnnotatedString(text),
        modifier = modifier,
        isSelectable = isSelectable,
        onClick = onClick
    )
}

@Composable
fun BubbleIncomeView(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    isSelectable: Boolean = true,
    onClick: (Int) -> Unit = {}
) {
    Bubble(
        text = text,
        modifier = modifier,
        isSelectable = isSelectable,
        isIncome = true,
        backgroundColor = AppTheme.colors.background.card,
        textColor = AppTheme.colors.type.primary,
        onClick = onClick
    )
}

@Composable
fun BubbleOutcomeView(
    text: String,
    modifier: Modifier = Modifier,
    isSelectable: Boolean = true,
    onClick: (Int) -> Unit = {}
) {
    BubbleOutcomeView(
        text = AnnotatedString(text),
        modifier = modifier,
        isSelectable = isSelectable,
        onClick = onClick
    )
}

@Composable
fun BubbleOutcomeView(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    isSelectable: Boolean = true,
    onClick: (Int) -> Unit = {}
) {
    Bubble(
        text = text,
        modifier = modifier,
        isSelectable = isSelectable,
        isIncome = false,
        backgroundColor = AppTheme.colors.theme.tint,
        textColor = AppTheme.colors.type.inverse,
        onClick = onClick
    )
}

@Composable
private fun Bubble(
    text: AnnotatedString,
    modifier: Modifier,
    isSelectable: Boolean,
    isIncome: Boolean,
    backgroundColor: Color,
    textColor: Color,
    onClick: (Int) -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        val bubble: @Composable () -> Unit = {
            var widthCalculated by remember { mutableStateOf(false) }
            var maxWidth by remember { mutableFloatStateOf(Float.MAX_VALUE) }
            val density = LocalDensity.current.density
            ClickableText(
                text = text,
                modifier = Modifier
                    .background(
                        backgroundColor,
                        RoundedCornerShape(
                            topStart = AppTheme.dimens.radiusBubble,
                            topEnd = AppTheme.dimens.radiusBubble,
                            bottomEnd = if (isIncome) AppTheme.dimens.radiusBubble else 0.dp,
                            bottomStart = if (isIncome) 0.dp else AppTheme.dimens.radiusBubble
                        )
                    )
                    .padding(
                        start = if (isIncome) AppTheme.dimens.spaceM else AppTheme.dimens.spaceXL,
                        end = if (isIncome) AppTheme.dimens.spaceXL else AppTheme.dimens.spaceM,
                        top = AppTheme.dimens.spaceS,
                        bottom = AppTheme.dimens.spaceS
                    )
                    .widthIn(max = maxWidth.dp),
                onTextLayout = { textLayoutResult ->
                    if (!widthCalculated) {
                        maxWidth = (0 until textLayoutResult.lineCount).maxOf { n ->
                            ceil(textLayoutResult.getLineRight(n) - textLayoutResult.getLineLeft(n))
                        } / density
                        widthCalculated = true
                    }
                },
                style = AppTheme.typography.body.copy(color = textColor),
                onClick = onClick
            )
        }
        Box(
            modifier = Modifier.align(if (isIncome) Alignment.CenterStart else Alignment.CenterEnd)
        ) {
            if (isSelectable) {
                val selectionColors = TextSelectionColors(
                    handleColor = AppTheme.colors.theme.tintSelection,
                    backgroundColor = AppTheme.colors.theme.tintSelection
                )
                CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {
                    SelectionContainer(content = bubble)
                }
            } else {
                bubble.invoke()
            }
        }
    }
}