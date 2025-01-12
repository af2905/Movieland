package com.github.af2905.movieland.compose.components.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.theme.AppTheme
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

/** Animation duration in seconds */
private const val ANIMATION_DURATION = 1250

/** The angle between the abscissa and the perpendicular drawn to the shimmer line */
private const val ANGLE = 20.0

/** Width of shimmer line in pixels */
private const val SHIMMER_LINE_WIDTH = 400

/** Animation speed multiplier */
private const val SPEED_MULTIPLIER = 4

/** The abscissa of the perpendicular drawn to the shimmer line */
private val dx: Float = cos(Math.toRadians(ANGLE)).toFloat() * SHIMMER_LINE_WIDTH

/** The ordinate of the perpendicular drawn to the shimmer line */
private val dy: Float = sin(Math.toRadians(ANGLE)).toFloat() * SHIMMER_LINE_WIDTH

/**
 * @return the brush for the shimmer animation
 * @param size - size of the element on which the shimmer animation is applied
 * */
@Composable
fun shimmerBrush(size: IntSize): Brush {
    val colors = listOf(
        AppTheme.colors.background.actionRipple,
        Color.White.copy(0.2f),
        AppTheme.colors.background.actionRipple
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = ANIMATION_DURATION,
                easing = LinearEasing
            )
        ),
        label = ""
    )

    val x0 = (SPEED_MULTIPLIER * size.width + tan(Math.toRadians(ANGLE)).toFloat() * size.height) *
            translateAnim.value + SHIMMER_LINE_WIDTH * (translateAnim.value - 1)
    val y0 = 0f
    val x1 = x0 + dx
    val y1 = y0 + dy

    return Brush.linearGradient(
        colors = colors,
        start = Offset(x0, y0),
        end = Offset(x1, y1),
        tileMode = TileMode.Clamp
    )
}

/*Previews*/
private const val W_H_PROPORTION: Double = 2.toDouble() / 3

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewShimmers() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Custom shimmer",
            modifier = Modifier.padding(AppTheme.dimens.spaceM),
            style = AppTheme.typography.body
        )
        CustomShimmer()
        Text(
            text = "Small shimmers",
            modifier = Modifier.padding(AppTheme.dimens.spaceM),
            style = AppTheme.typography.body
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .size(40.dp)
                    .shimmerBackground(RectangleShape)
            )

            Spacer(
                modifier = Modifier
                    .size(40.dp)
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusS))
            )

            Spacer(
                modifier = Modifier
                    .size(40.dp)
                    .shimmerBackground(CircleShape)
            )

            Spacer(
                modifier = Modifier
                    .size(60.dp, 25.dp)
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusS))
            )
        }
    }
}

@Composable
private fun CustomShimmer() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.spaceM),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spaceM)
    ) {
        repeat(2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.spaceM)
            ) {
                ShimmerCard()
                ShimmerCard()
            }
        }
    }
}

@Composable
private fun RowScope.ShimmerCard() {
    Spacer(
        modifier = Modifier
            .modifyCardHeight()
            .weight(1f)
            .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusS))
    )
}

private fun Modifier.modifyCardHeight() = layout { measurable, constraints ->
    val newHeight = constraints.maxWidth.times(W_H_PROPORTION).toInt()
    val placeable = measurable.measure(
        constraints.copy(
            minWidth = constraints.minWidth,
            maxWidth = constraints.maxWidth,
            minHeight = newHeight,
            maxHeight = newHeight,
        )
    )

    layout(placeable.width, placeable.height) {
        placeable.placeRelative(0, 0)
    }
}