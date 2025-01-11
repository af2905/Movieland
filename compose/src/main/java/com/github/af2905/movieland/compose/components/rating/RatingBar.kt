package com.github.af2905.movieland.compose.components.rating

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.theme.AppTheme

private fun Double.fiveStarRating(): Double = this / 2

@Composable
fun RatingBar(
    rating: Double,
    modifier: Modifier = Modifier,
    numStars: Int = 5
) {
    val lowRange = 0.1..5.0
    val normalRange = 5.1..6.9
    val highRange = 7.0..10.0

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = rating.toString(),
            style = AppTheme.typography.bodyMedium,
            color = when (rating) {
                in lowRange -> AppTheme.colors.background.alert
                in highRange -> AppTheme.colors.background.success
                in normalRange -> AppTheme.colors.background.actionRippleInverse
                else -> AppTheme.colors.background.actionRippleInverse
            },
            modifier = Modifier.padding(end = AppTheme.dimens.spaceXS)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val fiveStarRating = rating.fiveStarRating()
            for (i in 1..numStars) {
                val starColor =
                    if (i <= fiveStarRating) AppTheme.colors.theme.rating else AppTheme.colors.theme.ratingGhost
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    modifier = Modifier
                        .size(AppTheme.dimens.starSize),
                    tint = starColor
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode RatingBar Preview"
)
@Composable
fun PreviewRatingBar() {
    AppTheme(darkTheme = true) { // Replace with your custom theme if needed
        Box(modifier = Modifier.padding(16.dp)) {
            RatingBar(
                rating = 7.2, // Sample rating value
                modifier = Modifier.padding(8.dp),
                numStars = 5
            )
        }
    }
}