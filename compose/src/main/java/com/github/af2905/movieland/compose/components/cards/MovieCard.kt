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

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    item: MovieItemModel,
    onItemClick: (MovieItemModel) -> Unit
) {
    ElevatedCard(
        onClick = {
            onItemClick(item)
        },
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
                model = item.posterFullPathToImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                error = rememberVectorPainter(image = Icons.Outlined.Image),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceXS))

            if (item.voteAverage != null && item.voteAverageStar != null) {
                RatingBar(
                    voteAverage = item.voteAverage,
                    rating = item.voteAverageStar ?: 0.0,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceXS))

            Text(
                text = item.title.orEmpty(),
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
                        if (item.title == null) Modifier.alpha(0.0f) else Modifier
                    )
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun PreviewMovieCardWithSampleData() {
    val sampleMovieItem = MovieItemModel(
        id = 1,
        adult = false,
        backdropPath = "/oHPoF0Gzu8xwK4CtdXDaWdcuZxZ.jpg",
        genreIds = listOf(35),
        originalLanguage = "en",
        originalTitle = "High Vote Movie",
        overview = "This is a movie with a high vote average.",
        popularity = 345.67,
        posterPath = "/aosm8NMQ3UyoBVpSxyimorCQykC.jpg",
        releaseDate = "2023-12-15",
        title = "Sample Movie Title",
        video = false,
        voteAverage = 7.8,
        voteCount = 1234
    )

    AppTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.background.default)
        ) {
            MovieCard(
                modifier = Modifier.padding(all = 16.dp),
                item = sampleMovieItem,
                onItemClick = { movieItem ->
                    println("Clicked on movie: ${movieItem.title}")
                }
            )
        }
    }
}

@Preview(showBackground = true, name = "MovieCard with High Vote Average")
@Composable
fun PreviewMovieCardHighVote() {
    val highVoteMovie = MovieItemModel(
        id = 2,
        adult = false,
        backdropPath = "/oHPoF0Gzu8xwK4CtdXDaWdcuZxZ.jpg",
        genreIds = listOf(35),
        originalLanguage = "en",
        originalTitle = "High Vote Movie",
        overview = "This is a movie with a high vote average.",
        popularity = 345.67,
        posterPath = "/aosm8NMQ3UyoBVpSxyimorCQykC.jpg",
        releaseDate = "2021-07-20",
        title = "High Vote Movie",
        video = false,
        voteAverage = 9.2,
        voteCount = 5678
    )

    MovieCard(
        item = highVoteMovie,
        onItemClick = { movieItem ->
            println("Clicked on movie: ${movieItem.title}")
        }
    )
}

@Preview(showBackground = true, name = "MovieCard with Low Vote Average")
@Composable
fun PreviewMovieCardLowVote() {
    val lowVoteMovie = MovieItemModel(
        id = 3,
        adult = false,
        backdropPath = null,
        genreIds = listOf(18),
        originalLanguage = "en",
        originalTitle = "Low Vote Movie",
        overview = "This is a movie with a low vote average.",
        popularity = 789.12,
        posterPath = null,
        releaseDate = "2019-04-10",
        title = "Low Vote Movie",
        video = false,
        voteAverage = 4.3,
        voteCount = 234
    )

    MovieCard(
        item = lowVoteMovie,
        onItemClick = { movieItem ->
            println("Clicked on movie: ${movieItem.title}")
        }
    )
}