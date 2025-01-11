package com.github.af2905.movieland.compose.components.cards

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.af2905.movieland.compose.components.rating.RatingBar
import com.github.af2905.movieland.compose.theme.AppTheme

private fun Double?.orDefault(): Double = this ?: 0.0

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    title: String?,
    imageUrl: String?,
    rating: Double?,
    onItemClick: () -> Unit
) {
    ElevatedCard(
        onClick = { onItemClick() },
        modifier = modifier
            .width(150.dp),
        shape = RoundedCornerShape(AppTheme.dimens.radiusM),
        colors = CardDefaults.elevatedCardColors(
            containerColor = AppTheme.colors.background.card
        ),
        elevation = CardDefaults.cardElevation(AppTheme.dimens.elevationS)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                error = rememberVectorPainter(image = Icons.Outlined.Image),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceXS))

            RatingBar(
                rating = rating.orDefault(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.spaceXS)
                        then (
                        if (rating == null) Modifier.alpha(0.0f) else Modifier)
            )

            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceXS))

            Text(
                text = title.orEmpty(),
                style = AppTheme.typography.caption1,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = AppTheme.dimens.spaceXS,
                        end = AppTheme.dimens.spaceXS,
                        bottom = AppTheme.dimens.space2XS
                    )
                    .then(
                        if (title == null) Modifier.alpha(0.0f) else Modifier
                    )
            )
        }
    }
}

@Composable
fun MovieCardLarge(
    modifier: Modifier = Modifier,
    title: String?,
    imageUrl: String?,
    rating: Double?,
    onItemClick: () -> Unit
) {
    ElevatedCard(
        onClick = { onItemClick() },
        modifier = modifier.height(250.dp),
        shape = RoundedCornerShape(AppTheme.dimens.radiusM),
        colors = CardDefaults.elevatedCardColors(
            containerColor = AppTheme.colors.background.card
        ),
        elevation = CardDefaults.cardElevation(AppTheme.dimens.elevationS)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                error = rememberVectorPainter(image = Icons.Outlined.Image),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceXS))

            Row {
                Text(
                    text = title.orEmpty(),
                    style = AppTheme.typography.bodyMedium,
                    minLines = 2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            start = AppTheme.dimens.spaceS,
                            end = AppTheme.dimens.spaceXS,
                            bottom = AppTheme.dimens.space2XS
                        )
                        .then(
                            if (title == null) Modifier.alpha(0.0f) else Modifier
                        )
                )

                RatingBar(
                    rating = rating.orDefault(),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = AppTheme.dimens.spaceS)
                            then (
                            if (rating == null) Modifier.alpha(0.0f) else Modifier)
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun PreviewMovieCardLargeWithSampleData() {
    AppTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.background.default)
        ) {
            MovieCardLarge(
                modifier = Modifier.padding(all = 16.dp),
                title = "Lion King",
                imageUrl = "https://image.tmdb.org/t/p/original/oHPoF0Gzu8xwK4CtdXDaWdcuZxZ.jpg",
                rating = 6.7,
                onItemClick = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun PreviewMovieCardWithSampleData() {
    AppTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.background.default)
        ) {
            MovieCard(
                modifier = Modifier.padding(all = 16.dp),
                title = "Lion King",
                imageUrl = "https://image.tmdb.org/t/p/original/aosm8NMQ3UyoBVpSxyimorCQykC.jpg",
                rating = 6.7,
                onItemClick = {}
            )
        }
    }
}