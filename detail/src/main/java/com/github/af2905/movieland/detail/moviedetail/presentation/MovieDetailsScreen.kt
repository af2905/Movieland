package com.github.af2905.movieland.detail.moviedetail.presentation

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.github.af2905.movieland.compose.components.divider.AppHorizontalDivider
import com.github.af2905.movieland.compose.components.rating.RatingBar
import com.github.af2905.movieland.compose.components.topbar.AppCenterAlignedTopAppBar
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.util.extension.convertMinutesToHoursAndMinutes
import com.github.af2905.movieland.util.extension.getYearFromReleaseDate

@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    onAction: (MovieDetailsAction) -> Unit,
) {
    Column {
        AppCenterAlignedTopAppBar(
            title = "Movie Details",
            onBackClick = { onAction(MovieDetailsAction.BackClick) },
            endButtons = {

            }
        )
        Column(
            modifier = Modifier
                .background(AppTheme.colors.theme.tint)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(Modifier.height(12.dp))
            Column(
                verticalArrangement = Arrangement.Bottom
            ) {

                var patientDataGroupSize by remember { mutableStateOf(Size.Zero) }

                Box(contentAlignment = Alignment.BottomCenter) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                with(LocalDensity.current) {
                                    Modifier.height((patientDataGroupSize.height / 2).toDp())
                                }
                            )
                            .background(AppTheme.colors.background.default)
                    )

                    ElevatedCard(
                        enabled = false,
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
                            .onGloballyPositioned {
                                patientDataGroupSize = it.size.toSize()
                            },
                        shape = RoundedCornerShape(AppTheme.dimens.radiusM),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = AppTheme.colors.theme.tintCard
                        ),
                        elevation = CardDefaults.cardElevation(AppTheme.dimens.elevationXS)
                    ) {

                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/original/${state.movie?.backdropPath}",
                            contentDescription = null,
                            modifier = Modifier
                                .height(200.dp),
                            error = rememberVectorPainter(image = Icons.Outlined.Image),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxSize()
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
                        text = "${
                            state.movie?.releaseDate?.getYearFromReleaseDate().orEmpty()
                        }, ${state.movie?.genres?.joinToString { it.name }.orEmpty()}",
                        color = AppTheme.colors.type.secondary
                    )
                    Text(
                        text = "${
                            state.movie?.productionCountries?.joinToString { it.countryName }
                                .orEmpty()
                        }, ${state.movie?.runtime?.convertMinutesToHoursAndMinutes().orEmpty()}",
                        color = AppTheme.colors.type.secondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    AppHorizontalDivider()
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppTheme.colors.background.default)
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

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


