package com.github.af2905.movieland.home.presentation

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.compose.components.cards.ItemCard
import com.github.af2905.movieland.compose.components.cards.ItemCardLarge
import com.github.af2905.movieland.compose.components.chips.ChipView
import com.github.af2905.movieland.compose.theme.AppTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import com.github.af2905.movieland.compose.components.headlines.HeadlinePrimaryActionView
import com.github.af2905.movieland.compose.components.topbar.AppCenterAlignedTopAppBar
import com.github.af2905.movieland.core.data.MediaType
import com.github.af2905.movieland.home.presentation.models.getMovieGenreItems
import com.github.af2905.movieland.home.presentation.models.getTvShowGenreItems

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = { state.trendingMovies.size }
    )
    Column {
        AppCenterAlignedTopAppBar(
            title = "Movieland",
            onBackClick = { },
            endButtons = {

            }
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
                        onItemClick = {
                            onAction(HomeAction.OpenMovieDetail(movie.id))
                        }
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
                            mediaType = MediaType.TV,
                            onItemClick = {
                                onAction(HomeAction.OpenTvShowDetail(tvShow.id))
                            }
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
                            mediaType = MediaType.PERSON,
                            onItemClick = {
                                onAction(HomeAction.OpenPersonDetail(person.id))
                            }
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
                    items(state.moviesGenres.getMovieGenreItems()) { item ->
                        ChipView(
                            text = "${item.icon}  ${item.title}",
                            isLarge = true,
                            onClick = {
                                onAction(HomeAction.OpenGenre(item.id))
                            }
                        )
                    }
                }
            }

            item {
                HeadlinePrimaryActionView(
                    text = "Now Playing Movies",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

            // Now Playing Movies LazyRow
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.nowPlayingMovies) { movie ->
                        ItemCard(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            title = movie.title,
                            imageUrl = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
                            rating = movie.voteAverage,
                            mediaType = MediaType.MOVIE,
                            onItemClick = {
                                onAction(HomeAction.OpenMovieDetail(movie.id))
                            }
                        )
                    }
                }
            }

            item {
                HeadlinePrimaryActionView(
                    text = "Popular Movies",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

            // Popular Movies LazyRow
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.popularMovies) { movie ->
                        ItemCard(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            title = movie.title,
                            imageUrl = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
                            rating = movie.voteAverage,
                            mediaType = MediaType.MOVIE,
                            onItemClick = {
                                onAction(HomeAction.OpenMovieDetail(movie.id))
                            }
                        )
                    }
                }
            }
            item {
                HeadlinePrimaryActionView(
                    text = "Upcoming Movies",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

            // Upcoming Movies LazyRow
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.upcomingMovies) { movie ->
                        ItemCard(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            title = movie.title,
                            imageUrl = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
                            rating = movie.voteAverage,
                            mediaType = MediaType.MOVIE,
                            onItemClick = {
                                onAction(HomeAction.OpenMovieDetail(movie.id))
                            }
                        )
                    }
                }
            }

            item {
                HeadlinePrimaryActionView(
                    text = "Top Rated Movies",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

            // Upcoming Movies LazyRow
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.topRatedMovies) { movie ->
                        ItemCard(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            title = movie.title,
                            imageUrl = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
                            rating = movie.voteAverage,
                            mediaType = MediaType.MOVIE,
                            onItemClick = {
                                onAction(HomeAction.OpenMovieDetail(movie.id))
                            }
                        )
                    }
                }
            }

            item {
                HeadlinePrimaryActionView(text = "TV Shows genres")
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.tvShowsGenres.getTvShowGenreItems()) { item ->
                        ChipView(
                            text = "${item.icon}  ${item.title}",
                            isLarge = true,
                            onClick = {
                                onAction(HomeAction.OpenGenre(item.id))
                            }
                        )
                    }
                }
            }

            item {
                HeadlinePrimaryActionView(
                    text = "Popular TV Shows",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

            // Popular TV Shows LazyRow
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.popularTvShows) { tvShow ->
                        ItemCard(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            title = tvShow.name,
                            imageUrl = "https://image.tmdb.org/t/p/original/${tvShow.posterPath}",
                            rating = tvShow.voteAverage,
                            mediaType = MediaType.TV,
                            onItemClick = {
                                onAction(HomeAction.OpenTvShowDetail(tvShow.id))
                            }
                        )
                    }
                }
            }

            item {
                HeadlinePrimaryActionView(
                    text = "Top rated TV Shows",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

            // Popular Movies LazyRow
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.topRatedTvShows) { tvShow ->
                        ItemCard(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            title = tvShow.name,
                            imageUrl = "https://image.tmdb.org/t/p/original/${tvShow.posterPath}",
                            rating = tvShow.voteAverage,
                            mediaType = MediaType.TV,
                            onItemClick = {
                                onAction(HomeAction.OpenTvShowDetail(tvShow.id))
                            }
                        )
                    }
                }
            }

            item {
                HeadlinePrimaryActionView(
                    text = "Popular People",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

            // Popular people Shows LazyRow
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppTheme.dimens.spaceM),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.popularPeople) { person ->
                        ItemCard(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            title = person.name,
                            imageUrl = "https://image.tmdb.org/t/p/original/${person.profilePath}",
                            mediaType = MediaType.PERSON,
                            onItemClick = {
                                onAction(HomeAction.OpenPersonDetail(person.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

