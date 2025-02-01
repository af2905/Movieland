package com.github.af2905.movieland.detail.moviedetail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.github.af2905.movieland.compose.components.cards.ItemCard
import com.github.af2905.movieland.compose.components.divider.AppHorizontalDivider
import com.github.af2905.movieland.compose.components.headlines.HeadlinePrimaryActionView
import com.github.af2905.movieland.compose.components.rating.RatingBar
import com.github.af2905.movieland.compose.components.topbar.AppCenterAlignedTopAppBar
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.core.data.MediaType
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.util.extension.convertMinutesToHoursAndMinutes
import com.github.af2905.movieland.util.extension.getYearFromReleaseDate

@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    onAction: (MovieDetailsAction) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top App Bar
        AppCenterAlignedTopAppBar(
            title = "",
            onBackClick = { onAction(MovieDetailsAction.BackClick) },
            endButtons = { }
        )

        // Main Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.theme.tint),
            //contentPadding = PaddingValues(bottom = 16.dp),
        ) {
            // Backdrop Image
            item {
                var dataGroupSize by remember { mutableStateOf(Size.Zero) }

                Box(contentAlignment = Alignment.BottomCenter) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(with(LocalDensity.current) { (dataGroupSize.height / 2).toDp() })
                            .background(AppTheme.colors.background.default)
                    )

                    ElevatedCard(
                        enabled = false,
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                            .onGloballyPositioned { dataGroupSize = it.size.toSize() },
                        shape = RoundedCornerShape(AppTheme.dimens.radiusM),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = AppTheme.colors.theme.tintCard
                        ),
                        elevation = CardDefaults.cardElevation(AppTheme.dimens.elevationXS)
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/original/${state.movie?.backdropPath}",
                            contentDescription = null,
                            modifier = Modifier.height(200.dp),
                            error = rememberVectorPainter(image = Icons.Outlined.Image),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            // Movie Information
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppTheme.colors.background.default)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = state.movie?.title.orEmpty(),
                        style = AppTheme.typography.title2,
                        color = AppTheme.colors.type.secondary
                    )
                    if (state.movie?.voteAverage != null && state.movie.voteAverage != 0.0) {
                        RatingBar(rating = state.movie.voteAverage ?: 0.0)
                    }
                    Text(
                        text = "${state.movie?.releaseDate?.getYearFromReleaseDate().orEmpty()}, ${
                            state.movie?.genres?.joinToString { it.name }.orEmpty()
                        }",
                        color = AppTheme.colors.type.secondary
                    )
                    Text(
                        text = "${
                            state.movie?.productionCountries?.joinToString { it.countryName }
                                .orEmpty()
                        }, ${
                            state.movie?.runtime?.convertMinutesToHoursAndMinutes().orEmpty()
                        }",
                        color = AppTheme.colors.type.secondary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AppHorizontalDivider()
                }
            }

            // Movie Details
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppTheme.colors.background.default)
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    if (!state.movie?.tagline.isNullOrEmpty()) {
                        Text(
                            text = state.movie?.tagline.orEmpty(),
                            style = AppTheme.typography.title3,
                            color = AppTheme.colors.type.secondary
                        )
                    }
                    if (!state.movie?.overview.isNullOrEmpty()) {
                        Text(
                            text = state.movie?.overview.orEmpty(),
                            style = AppTheme.typography.body,
                            color = AppTheme.colors.type.secondary
                        )
                    }
                }
            }

            // Similar Movies Section
            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppTheme.colors.background.default),
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HeadlinePrimaryActionView(
                        text = "Similar Movies",
                        action = "View All",
                        onClick = { /* Handle click */ }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.similarMovies.orEmpty()) { movie ->
                            ItemCard(
                                modifier = Modifier.padding(horizontal = 6.dp),
                                title = movie.title,
                                imageUrl = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
                                rating = movie.voteAverage,
                                mediaType = MediaType.MOVIE,
                                onItemClick = {
                                    onAction(MovieDetailsAction.OpenMovieDetail(movie.id))
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 360)
@Composable
private fun MovieDetailsScreenPreview() {
    val sampleMovie = MovieDetail(
        id = 1,
        title = "The Shawshank Redemption",
        originalTitle = "The Shawshank Redemption",
        originalLanguage = "en",
        overview = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
        tagline = "Fear can hold you prisoner. Hope can set you free.",
        budget = 25000000,
        revenue = 28341469,
        runtime = 142,
        releaseDate = "1994-09-23",
        status = "Released",
        adult = false,
        popularity = 196.193,
        voteAverage = 8.7,
        voteCount = 27548,
        homepage = "https://www.warnerbros.com/movies/shawshank-redemption",
        backdropPath = "https://image.tmdb.org/t/p/original/oHPoF0Gzu8xwK4CtdXDaWdcuZxZ.jpg",
        posterPath = "https://image.tmdb.org/t/p/w500/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
        video = false,
        genres = emptyList(),
        productionCompanies = emptyList(),
        productionCountries = emptyList(),
    )

    val sampleState = MovieDetailsState(movie = sampleMovie)

    MovieDetailsScreen(
        state = sampleState,
        onAction = {}
    )
}


