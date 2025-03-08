package com.github.af2905.movieland.tvshows.presentation

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.github.af2905.movieland.compose.components.cards.ItemCardHorizontal
import com.github.af2905.movieland.compose.components.shimmer.shimmerBackground
import com.github.af2905.movieland.compose.components.topbar.AppCenterAlignedTopAppBar
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.core.common.helper.ImageProvider
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.tvshows.R

@Composable
fun TvShowsScreen(
    tvShowType: TvShowType,
    tvShows: LazyPagingItems<TvShow>,
    onAction: (TvShowsAction) -> Unit
) {
    Scaffold(
        topBar = {
            AppCenterAlignedTopAppBar(
                title = stringResource(
                    when (tvShowType) {
                        TvShowType.POPULAR -> R.string.popular_tv_shows
                        TvShowType.TOP_RATED -> R.string.top_rated_tv_shows
                        //TvShowType.ON_THE_AIR -> R.string.on_the_air_tv_shows
                        //TvShowType.AIRING_TODAY -> R.string.airing_today_tv_shows
                        TvShowType.TRENDING_DAY, TvShowType.TRENDING_WEEK -> R.string.trending_tv_shows
                        TvShowType.SIMILAR -> R.string.similar_tv_shows
                        TvShowType.RECOMMENDED -> R.string.recommended_tv_shows
                        else -> R.string.popular_tv_shows //TODO temp
                    }
                ),
                onBackClick = { onAction(TvShowsAction.BackClick) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AppTheme.colors.background.default),
            contentPadding = PaddingValues(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(tvShows.itemCount) { index ->
                val tvShow = tvShows[index]
                tvShow?.let {
                    ItemCardHorizontal(
                        title = it.name,
                        description = it.overview,
                        imageUrl = ImageProvider.getImageUrl(it.posterPath),
                        rating = it.voteAverage,
                        onItemClick = { onAction(TvShowsAction.OpenTvShowDetail(it.id)) }
                    )
                }
            }

            tvShows.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { ShimmerTvShowItem() }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingIndicator() }
                    }

                    loadState.refresh is LoadState.Error -> {
                        item { ErrorRetryButton { retry() } }
                    }
                }
            }
        }
    }
}

@Composable
private fun ShimmerTvShowItem() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        repeat(5) {
            Spacer(
                modifier = Modifier
                    .height(136.dp)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusM))
            )
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorRetryButton(onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onRetry) {
            Text(text = stringResource(R.string.retry))
        }
    }
}