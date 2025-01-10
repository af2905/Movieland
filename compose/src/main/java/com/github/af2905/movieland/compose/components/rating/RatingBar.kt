package com.github.af2905.movieland.compose.components.rating

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
import com.github.af2905.movieland.compose.theme.AppTheme

@Composable
fun RatingBar(
    voteAverage: Double,
    rating: Double,
    modifier: Modifier = Modifier,
    numStars: Int = 5
) {
    val lowRange = 0.1..5.0
    val normalRange = 5.1..6.9
    val highRange = 7.0..10.0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.spaceXS),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = voteAverage.toString(),
            style = AppTheme.typography.bodyMedium,
            color = when (voteAverage) {
                in lowRange -> AppTheme.colors.background.alert
                in highRange -> AppTheme.colors.background.success
                in normalRange -> AppTheme.colors.background.divider
                else -> AppTheme.colors.background.divider
            },
            modifier = Modifier.padding(end = AppTheme.dimens.spaceXS)
        )
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..numStars) {
                val starColor =
                    if (i <= rating) AppTheme.colors.theme.rating else AppTheme.colors.theme.ratingGhost
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