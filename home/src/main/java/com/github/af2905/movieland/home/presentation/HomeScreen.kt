package com.github.af2905.movieland.home.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.af2905.movieland.compose.components.cards.ItemCard
import com.github.af2905.movieland.compose.components.cards.ItemCardLarge
import com.github.af2905.movieland.compose.components.chips.ChipIconView
import com.github.af2905.movieland.compose.components.chips.ChipIconViewStyle
import com.github.af2905.movieland.compose.components.chips.ChipView
import com.github.af2905.movieland.compose.components.chips.ChipViewStyle
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.compose.theme.Themes
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.res.stringResource
import com.github.af2905.movieland.compose.components.headlines.HeadlinePrimaryActionView
import com.github.af2905.movieland.compose.components.topbar.AppCenterAlignedTopAppBar
import com.github.af2905.movieland.home.R


@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = { state.trendingMovies.size }
    )
    Column {
        // Top App Bar

            AppCenterAlignedTopAppBar(
                title = "",
                onBackClick = { },
                endButtons = { }
            )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                HeadlinePrimaryActionView(
                    text = "Trending Movies",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

            // Top Rated Movies Horizontal Pager
            item {
                HorizontalPager(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
                    pageSpacing = AppTheme.dimens.space2XS,
                    state = pagerState
                ) { page ->
                    val movie = state.trendingMovies[page]

                    ItemCardLarge(
                        modifier = Modifier.padding(horizontal = AppTheme.dimens.space2XS),
                        title = movie.title,
                        imageUrl = "https://image.tmdb.org/t/p/original/${movie.backdropPath}",
                        rating = movie.voteAverage,
                        onItemClick = {}
                    )
                }
            }

            // Pager Indicators
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(state.trendingMovies.size) { iteration ->
                        val animatedColor by animateColorAsState(
                            if (pagerState.currentPage == iteration) AppTheme.colors.theme.tint else AppTheme.colors.background.border,
                            label = "",
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(4.dp)
                                .background(animatedColor, CircleShape)
                                .size(if (pagerState.currentPage == iteration) 10.dp else 6.dp)
                        )
                    }
                }
            }

            item {
                HeadlinePrimaryActionView(
                    text = "Trending TV Shows",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

            // Trending TV Shows LazyRow
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.trendingTvShows) { tvShow ->
                        ItemCard(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            title = tvShow.name,
                            imageUrl = "https://image.tmdb.org/t/p/original/${tvShow.posterPath}",
                            rating = tvShow.voteAverage,
                            onItemClick = {}
                        )
                    }
                }
            }

            item {
                HeadlinePrimaryActionView(
                    text = "Trending People",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

            // Trending TV Shows LazyRow
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.trendingPeople) { person ->
                        ItemCard(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            title = person.name,
                            imageUrl = "https://image.tmdb.org/t/p/original/${person.profilePath}",
                            rating = null,
                            onItemClick = {}
                        )
                    }
                }
            }

            item {
                HeadlinePrimaryActionView(text = "Movies genres")
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.moviesGenres) { genre ->
                        ChipView(
                            text = genre.name,
                            isLarge = true,
                            //style = ChipViewStyle.FadeTint,
                            onClick = { }
                        )
                    }
                }

                // Additional LazyRows for other categories

            }
        }
    }
}
