package com.github.af2905.movieland.detail.moviedetail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.github.af2905.movieland.compose.components.cards.ItemCard
import com.github.af2905.movieland.compose.components.chips.ChipView
import com.github.af2905.movieland.compose.components.divider.AppHorizontalDivider
import com.github.af2905.movieland.compose.components.headlines.HeadlinePrimaryActionView
import com.github.af2905.movieland.compose.components.rating.RatingBar
import com.github.af2905.movieland.compose.components.topbar.AppCenterAlignedTopAppBar
import com.github.af2905.movieland.compose.components.video_player.YouTubeThumbnail
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.core.data.MediaType
import com.github.af2905.movieland.core.data.database.entity.CreditsCast
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.ProductionCompany
import com.github.af2905.movieland.core.data.database.entity.Video
import com.github.af2905.movieland.util.extension.convertMinutesToHoursAndMinutes
import com.github.af2905.movieland.util.extension.getYearFromReleaseDate

@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    onAction: (MovieDetailsAction) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val showTitle by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex > 0 }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppCenterAlignedTopAppBar(
            title = if (showTitle) state.movie?.title.orEmpty() else "",
            onBackClick = { onAction(MovieDetailsAction.BackClick) },
            endButtons = {
                Row {
                    IconButton(onClick = { /* Handle action */ }) {
                        Icon(
                            imageVector = Icons.Outlined.BookmarkBorder,
                            contentDescription = ""
                        )
                    }
                    IconButton(onClick = { /* Handle action */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = ""
                        )
                    }
                }
            }
        )

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.theme.tint),
        ) {
            //Backdrop Image
            item { MovieBackdrop(state) }

            //Movie Information (This contains the title, so we track when it disappears)
            item { MovieInformation(state) }

            //Movie Details Section
            item { MovieDetails(state) }

            //YouTube Videos Section
            if (state.videos.isNotEmpty()) {
                item { MovieVideos(state.videos, onAction) }
            }

            //Movie casts Section
            if (state.casts.isNotEmpty()) {
                item { MovieCasts(state.casts, onAction) }
            }

            //Production companies
            if (!state.movie?.productionCompanies.isNullOrEmpty()) {
                item { ProductionCompanies(state.movie?.productionCompanies.orEmpty(), onAction) }
            }

            //Recommended Movies Section
            if (state.recommendedMovies.isNotEmpty()) {
                item { RecommendedMovies(state.recommendedMovies, onAction) }
            }

            //Similar Movies Section
            if (state.similarMovies.isNotEmpty()) {
                item { SimilarMovies(state.similarMovies, onAction) }
            }
        }
    }
}

@Composable
fun MovieBackdrop(state: MovieDetailsState) {
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

@Composable
fun MovieInformation(state: MovieDetailsState) {
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

@Composable
fun MovieDetails(state: MovieDetailsState) {
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

@Composable
fun MovieVideos(videos: List<Video>, onAction: (MovieDetailsAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeadlinePrimaryActionView(
            text = "Videos",
            action = "View All",
            onClick = { /* Handle View All Click */ }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(videos) { video ->
                YouTubeThumbnail(
                    videoId = video.key,
                    videoName = video.name,
                    onVideoClick = { videoId ->
                        onAction(MovieDetailsAction.OpenVideo(videoId))
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MovieCasts(casts: List<CreditsCast>, onAction: (MovieDetailsAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeadlinePrimaryActionView(
            text = "Casts",
            action = "View All",
            onClick = { /* Handle View All Click */ }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(casts) { cast ->
                ItemCard(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    title = cast.name,
                    subtitle = cast.character,
                    showSubtitle = true,
                    imageUrl = "https://image.tmdb.org/t/p/original/${cast.profilePath}",
                    mediaType = MediaType.PERSON,
                    onItemClick = {
                        onAction(MovieDetailsAction.OpenPersonDetail(cast.id))
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ProductionCompanies(
    companies: List<ProductionCompany>,
    onAction: (MovieDetailsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeadlinePrimaryActionView(
            text = "Production Companies",
            action = "View All",
            onClick = { /* Handle View All Click */ }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(companies) { item ->
                ChipView(
                    text = item.companyName,
                    isLarge = true,
                    onClick = {
                        //TODO action
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RecommendedMovies(recommendedMovies: List<Movie>, onAction: (MovieDetailsAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.background.default),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeadlinePrimaryActionView(
            text = "Recommended Movies",
            action = "View All",
            onClick = { /* Handle click */ }
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recommendedMovies) { movie ->
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
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SimilarMovies(similarMovies: List<Movie>, onAction: (MovieDetailsAction) -> Unit) {
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
            items(similarMovies) { movie ->
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


