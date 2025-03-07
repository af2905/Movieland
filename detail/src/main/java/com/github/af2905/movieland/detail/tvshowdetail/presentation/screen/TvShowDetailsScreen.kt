package com.github.af2905.movieland.detail.tvshowdetail.presentation.screen

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.af2905.movieland.compose.components.empty_state.EmptyStateView
import com.github.af2905.movieland.compose.components.rating.RatingBar
import com.github.af2905.movieland.compose.components.topbar.AppCenterAlignedTopAppBar
import com.github.af2905.movieland.compose.components.video_player.YouTubeThumbnail
import com.github.af2905.movieland.core.common.helper.ImageProvider
import com.github.af2905.movieland.core.data.database.entity.Video
import com.github.af2905.movieland.detail.tvshowdetail.presentation.state.TvShowDetailsAction
import com.github.af2905.movieland.detail.tvshowdetail.presentation.state.TvShowDetailsState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import com.github.af2905.movieland.compose.components.cards.ItemCard
import com.github.af2905.movieland.compose.components.shimmer.shimmerBackground
import com.github.af2905.movieland.core.data.database.entity.CreditsCast
import com.github.af2905.movieland.core.data.database.entity.MediaType
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowType

@Composable
fun TvShowDetailsScreen(
    state: TvShowDetailsState,
    onAction: (TvShowDetailsAction) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val showTitle by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex > 0 }
    }

    Scaffold(
        topBar = {
            AppCenterAlignedTopAppBar(
                title = if (showTitle) state.tvShow?.name.orEmpty() else "",
                onBackClick = { onAction(TvShowDetailsAction.BackClick) },
                elevation = 0.dp
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when {
                    state.isLoading -> {
                        ShimmerTvShowDetailsScreen()
                    }

                    state.isError -> {
                        EmptyStateView(
                            modifier = Modifier.fillMaxSize(),
                            icon = Icons.Outlined.ErrorOutline,
                            title = "Oops! Something went wrong",
                            action = "Retry",
                            onClick = { /* Retry Action */ }
                        )
                    }

                    else -> {
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            item { TvShowBackdrop(state) }
                            item { TvShowInformation(state) }
                            item { TvShowDetails(state) }

                            if (state.videos.isNotEmpty()) {
                                item { TvShowVideos(state.videos, onAction) }
                            }

                            if (state.casts.isNotEmpty()) {
                                item { TvShowCasts(state.casts, onAction) }
                            }

                            if (state.recommendedTvShows.isNotEmpty()) {
                                item { RecommendedTvShows(state.recommendedTvShows, onAction) }
                            }

                            if (state.similarTvShows.isNotEmpty()) {
                                item { SimilarTvShows(state.similarTvShows, onAction) }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun TvShowBackdrop(state: TvShowDetailsState) {
    Box(contentAlignment = Alignment.BottomCenter) {
        AsyncImage(
            model = ImageProvider.getImageUrl(state.tvShow?.backdropPath),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun TvShowInformation(state: TvShowDetailsState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = state.tvShow?.name.orEmpty(),
            //style = MaterialTheme.typography.h5,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        state.tvShow?.voteAverage?.let { rating ->
            if (rating > 0) {
                RatingBar(rating = rating)
            }
        }

        val releaseYear = state.tvShow?.firstAirDate?.take(4) // Extracts year
        val genres = state.tvShow?.genre?.joinToString { it.name }

        Text(
            text = listOfNotNull(releaseYear, genres).joinToString(" • "),
            //style = MaterialTheme.typography.body2,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun TvShowDetails(state: TvShowDetailsState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        state.tvShow?.tagline?.let {
            Text(
                text = "\"$it\"",
                //style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        state.tvShow?.overview?.let {
            Text(
                text = it,
                //style = MaterialTheme.typography.body1,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun TvShowVideos(videos: List<Video>, onAction: (TvShowDetailsAction) -> Unit) {
    if (videos.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Videos",
                //style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(videos) { video ->
                    YouTubeThumbnail(
                        videoId = video.key,
                        videoName = video.name,
                        onVideoClick = { videoId ->
                            onAction(TvShowDetailsAction.OpenVideo(videoId))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TvShowCasts(casts: List<CreditsCast>, onAction: (TvShowDetailsAction) -> Unit) {
    if (casts.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Cast",
                //style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(casts) { cast ->
                    ItemCard(
                        title = cast.name,
                        subtitle = cast.character,
                        showSubtitle = true,
                        imageUrl = ImageProvider.getImageUrl(cast.profilePath),
                        mediaType = MediaType.PERSON,
                        onItemClick = {
                            onAction(TvShowDetailsAction.OpenPersonDetail(cast.id))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun RecommendedTvShows(recommendedTvShows: List<TvShow>, onAction: (TvShowDetailsAction) -> Unit) {
    if (recommendedTvShows.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Recommended TV Shows",
                //style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recommendedTvShows) { tvShow ->
                    ItemCard(
                        title = tvShow.name,
                        imageUrl = ImageProvider.getImageUrl(tvShow.posterPath),
                        rating = tvShow.voteAverage,
                        mediaType = MediaType.TV,
                        onItemClick = {
                            onAction(TvShowDetailsAction.OpenTvShowsByType(TvShowType.RECOMMENDED))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SimilarTvShows(similarTvShows: List<TvShow>, onAction: (TvShowDetailsAction) -> Unit) {
    if (similarTvShows.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Similar TV Shows",
                //style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(similarTvShows) { tvShow ->
                    ItemCard(
                        title = tvShow.name,
                        imageUrl = ImageProvider.getImageUrl(tvShow.posterPath),
                        rating = tvShow.voteAverage,
                        mediaType = MediaType.TV,
                        onItemClick = {
                            onAction(TvShowDetailsAction.OpenTvShowsByType(TvShowType.SIMILAR))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ShimmerTvShowDetailsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // **Backdrop Shimmer**
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shimmerBackground(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // **Title & Info Shimmer**
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier
                    .height(24.dp)
                    .width(200.dp)
                    .shimmerBackground(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth(0.7f)
                    .shimmerBackground(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth(0.7f)
                    .shimmerBackground(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(
                modifier = Modifier
                    .height(0.5.dp)
                    .fillMaxWidth()
                    .shimmerBackground(RoundedCornerShape(4.dp))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // **TV Show Details Shimmer**
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth(0.7f)
                .shimmerBackground(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(16.dp))

        // **Shimmer for TV Show Casts**
        ShimmerHorizontalList()

        Spacer(modifier = Modifier.height(16.dp))

        // **Shimmer for Recommended TV Shows**
        ShimmerHorizontalList()
    }
}

@Composable
private fun ShimmerHorizontalList() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(5) {
            Spacer(
                modifier = Modifier
                    .size(120.dp, 180.dp)
                    .shimmerBackground(RoundedCornerShape(12.dp))
            )
        }
    }
}