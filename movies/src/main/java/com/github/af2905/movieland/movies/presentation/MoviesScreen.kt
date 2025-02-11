package com.github.af2905.movieland.movies.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.af2905.movieland.compose.components.empty_state.EmptyStateView
import com.github.af2905.movieland.compose.components.topbar.AppCenterAlignedTopAppBar
import com.github.af2905.movieland.movies.R
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.components.shimmer.shimmerBackground
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.core.data.MediaType
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import com.github.af2905.movieland.compose.components.cards.ItemCard
import com.github.af2905.movieland.core.common.helper.ImageProvider

@Composable
fun MoviesScreen(
    state: MoviesState,
    onAction: (MoviesAction) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppCenterAlignedTopAppBar(
            title = stringResource(R.string.popular_movies),
            onBackClick = { onAction(MoviesAction.BackClick) }
        )

        when {
            state.isLoading -> {
                ShimmerMoviesScreen()
            }

            state.isError -> {
                EmptyStateView(
                    modifier = Modifier.fillMaxSize(),
                    icon = Icons.Outlined.ErrorOutline,
                    title = stringResource(R.string.oops_something_went_wrong),
                    action = stringResource(R.string.retry),
                    onClick = { onAction(MoviesAction.RetryFetch) }
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.popularMovies) { movie ->
                        ItemCard(
                            title = movie.title,
                            imageUrl = ImageProvider.getImageUrl(movie.posterPath),
                            rating = movie.voteAverage,
                            mediaType = MediaType.MOVIE,
                            onItemClick = {
                                onAction(MoviesAction.OpenMovieDetail(movie.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShimmerMoviesScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(AppTheme.dimens.spaceM)
    ) {
        items(5) {
            Spacer(
                modifier = Modifier
                    .size(150.dp, 200.dp)
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusM))
            )
        }
    }
}