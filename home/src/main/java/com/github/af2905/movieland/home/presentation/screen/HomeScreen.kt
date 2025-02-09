package com.github.af2905.movieland.home.presentation.screen

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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.rememberCoroutineScope
import com.github.af2905.movieland.compose.components.bottomsheet.AppModalBottomSheet
import com.github.af2905.movieland.compose.components.chips.ChipViewStyle
import com.github.af2905.movieland.compose.components.empty_state.EmptyStateView
import com.github.af2905.movieland.compose.components.headlines.HeadlinePrimaryActionView
import com.github.af2905.movieland.compose.components.shimmer.shimmerBackground
import com.github.af2905.movieland.compose.components.topbar.AppCenterAlignedTopAppBar
import com.github.af2905.movieland.compose.theme.Themes
import com.github.af2905.movieland.core.data.MediaType
import com.github.af2905.movieland.home.domain.models.getMovieGenreItems
import com.github.af2905.movieland.home.domain.models.getTvShowGenreItems
import com.github.af2905.movieland.home.presentation.HomeAction
import com.github.af2905.movieland.home.presentation.HomeState
import com.github.af2905.movieland.home.presentation.screen.bottomsheet.SelectAppColorBottomSheetComponent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    currentTheme: Themes,
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        pageCount = { state.trendingMovies.size }
    )

    if (sheetState.isVisible) {
        AppModalBottomSheet(
            sheetState = sheetState,
            content = {
                SelectAppColorBottomSheetComponent(
                    list = Themes.entries,
                    currentTheme = currentTheme,
                    onItemClick = { theme ->
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                        onAction(HomeAction.ChangeAppColor(selectedTheme = theme))
                    }
                )
            }
        )
    }

    Column {
        AppCenterAlignedTopAppBar(
            title = "Movieland",
            onBackClick = { },
            hasNavigationBack = false,
            endButtons = {
                ChipView(
                    text = "Change color",
                    onClick = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    },
                    style = ChipViewStyle.FadeTint
                )
            }
        )

        when {
            state.isLoading -> {
                // **Shimmer Loading Screen**
                ShimmerHomeScreen()
            }
            state.isError -> {
                // **Error Screen**
                EmptyStateView(
                    modifier = Modifier.fillMaxSize(),
                    icon = Icons.Outlined.ErrorOutline,
                    title = "Oops! Something went wrong.",
                    action = "Retry",
                    onClick = { /* Retry Action */ }
                )
            }
            else -> {
                // **Content Screen**
                HomeContent(state, onAction, pagerState)
            }
        }
    }
}

@Composable
private fun HomeContent(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    pagerState: PagerState
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Trending movies
        if (state.trendingMovies.isNotEmpty()) {
            item {
                HeadlinePrimaryActionView(
                    text = "Trending Movies",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

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
        }

        //Trending TV Shows
        if (state.trendingTvShows.isNotEmpty()) {
            item {
                HeadlinePrimaryActionView(
                    text = "Trending TV Shows",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

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
        }

        // Trending people
        if (state.trendingPeople.isNotEmpty()) {
            item {
                HeadlinePrimaryActionView(
                    text = "Trending People",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

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
        }

        // Movies genres
        if (state.moviesGenres.isNotEmpty()) {
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
                            style = ChipViewStyle.FadeTint,
                            text = "${item.icon}  ${item.title}",
                            isLarge = true,
                            onClick = {
                                onAction(HomeAction.OpenGenre(item.id))
                            }
                        )
                    }
                }
            }
        }

        // Now Playing Movies
        if (state.nowPlayingMovies.isNotEmpty()) {
            item {
                HeadlinePrimaryActionView(
                    text = "Now Playing Movies",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

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
        }

        // Popular Movies
        if (state.popularMovies.isNotEmpty()) {
            item {
                HeadlinePrimaryActionView(
                    text = "Popular Movies",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

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
        }

        // Upcoming Movies
        if (state.upcomingMovies.isNotEmpty()) {
            item {
                HeadlinePrimaryActionView(
                    text = "Upcoming Movies",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

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
        }

        // Top Rated Movies
        if (state.topRatedMovies.isNotEmpty()) {
            item {
                HeadlinePrimaryActionView(
                    text = "Top Rated Movies",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

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
        }

        // TV Shows genres
        if (state.tvShowsGenres.isNotEmpty()) {
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
                            style = ChipViewStyle.FadeTint,
                            text = "${item.icon}  ${item.title}",
                            isLarge = true,
                            onClick = {
                                onAction(HomeAction.OpenGenre(item.id))
                            }
                        )
                    }
                }
            }
        }

        // Popular TV Shows
        if (state.popularTvShows.isNotEmpty()) {
            item {
                HeadlinePrimaryActionView(
                    text = "Popular TV Shows",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

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
        }

        // Top rated TV Shows
        if (state.topRatedTvShows.isNotEmpty()) {
            item {
                HeadlinePrimaryActionView(
                    text = "Top rated TV Shows",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

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
        }

        // Popular People
        if (state.popularPeople.isNotEmpty()) {
            item {
                HeadlinePrimaryActionView(
                    text = "Popular People",
                    action = "View All",
                    onClick = { /* Handle click */ })
            }

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

@Composable
fun ShimmerHomeScreen() {
    LazyColumn(
        modifier = Modifier
        .fillMaxSize()
        .padding(AppTheme.dimens.spaceM)) {
        item {
            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))
            Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth(0.8f)
                .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusS))
        )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))
            Spacer(
                modifier = Modifier
                    .height(230.dp)
                    .fillMaxWidth()
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusM))
            )

            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM)) }
        item {
            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))
            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.8f)
                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.radiusS))
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))
            ShimmerHorizontalList()
            Spacer(modifier = Modifier.height(AppTheme.dimens.spaceM))
            ShimmerHorizontalList()
        }
    }
}

@Composable
fun ShimmerHorizontalList() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
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



