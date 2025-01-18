package com.github.af2905.movieland.home.presentation.compose

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.ModeNight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.af2905.movieland.compose.components.cards.ItemCard
import com.github.af2905.movieland.compose.components.cards.ItemCardHorizontal
import com.github.af2905.movieland.compose.components.cards.ItemCardLarge
import com.github.af2905.movieland.compose.components.chips.ChipIconView
import com.github.af2905.movieland.compose.components.chips.ChipIconViewStyle
import com.github.af2905.movieland.compose.components.chips.ChipView
import com.github.af2905.movieland.compose.components.chips.ChipViewStyle
import com.github.af2905.movieland.compose.theme.AppTheme
import com.github.af2905.movieland.compose.theme.Themes
import com.github.af2905.movieland.core.compose.AppNavRoutes
import com.github.af2905.movieland.home.presentation.HomeViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color


@Composable
fun HomeScreen(
    navController: NavHostController,
    isDarkTheme: Boolean,
    currentTheme: Themes,
    onDarkThemeClick: () -> Unit,
    onThemeClick: (Themes) -> Unit
) {

    val viewModel: HomeViewModel = hiltViewModel()

    val movies by viewModel.getTrendingMovies().collectAsState(initial = emptyList())

    val pagerState = rememberPagerState(
        pageCount = { movies.size }
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = 16.dp,
            state = pagerState
        ) { page ->
            val movie = movies[page]

            ItemCardLarge(
                modifier = Modifier.padding(horizontal = 6.dp),
                title = movie.title,
                imageUrl = "https://image.tmdb.org/t/p/original/${movie.backdropPath}",
                rating = movie.voteAverage,
                onItemClick = {}
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(movies.size) { iteration ->
                val animatedColor by animateColorAsState(
                    if (pagerState.currentPage == iteration) AppTheme.colors.theme.tint else AppTheme.colors.background.border,
                    label = "",
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(4.dp)
                        .background(animatedColor, CircleShape)
                        .size(if(pagerState.currentPage == iteration) 10.dp else 6.dp)
                )
            }
        }


        ChipIconView(
            image = if (isDarkTheme) Icons.Outlined.DarkMode else Icons.Outlined.LightMode,
            style = ChipIconViewStyle.FadeTint,
            onClick = {
                onDarkThemeClick()
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.spaceM)
                .horizontalScroll((rememberScrollState()))
                .padding(horizontal = AppTheme.dimens.spaceM),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.spaceXS)
        ) {
            for (theme in Themes.entries) {
                ChipView(
                    text = theme.name,
                    isLarge = true,
                    style = ChipViewStyle.FadeTint,
                    onClick = { onThemeClick(theme) }
                )
            }
        }

        LazyRow {
            items(movies) { movie ->

                println("MOVIES_TAG: movie -> $movie")

                ItemCardLarge(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    title = movie.title,
                    imageUrl = "https://image.tmdb.org/t/p/original/${movie.backdropPath}",
                    rating = movie.voteAverage,
                    onItemClick = {}
                )
            }
        }

    }


    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ChipIconView(
            image = if (isDarkTheme) Icons.Outlined.DarkMode else Icons.Outlined.LightMode,
            style = ChipIconViewStyle.FadeTint,
            onClick = {
                onDarkThemeClick()
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.spaceM)
                .horizontalScroll((rememberScrollState()))
                .padding(horizontal = AppTheme.dimens.spaceM),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.spaceXS)
        ) {
            for (theme in Themes.entries) {
                ChipView(
                    text = theme.name,
                    isLarge = true,
                    style = ChipViewStyle.FadeTint,
                    onClick = { onThemeClick(theme) }
                )
            }
        }
*//*
        ItemCardLarge(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = "Lion King",
            imageUrl = "https://image.tmdb.org/t/p/original/oHPoF0Gzu8xwK4CtdXDaWdcuZxZ.jpg",
            rating = 6.7,
            onItemClick = {}
        )*//*

        ItemCardHorizontal(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = "Lion King Lion King Lion King Lion King",
            imageUrl = "https://image.tmdb.org/t/p/original/aosm8NMQ3UyoBVpSxyimorCQykC.jpg",
            rating = 9.7,
            itemTypeName = "Movie",
            onItemClick = {}
        )

        ItemCard(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = "Lion King",
            imageUrl = "https://image.tmdb.org/t/p/original/aosm8NMQ3UyoBVpSxyimorCQykC.jpg",
            rating = 6.7,
            onItemClick = {}
        )

        Button(onClick = {
            navController.navigate(AppNavRoutes.MovieDetails.createRoute(123)) // Example ID
        }) {
            Text(text = "Go to Movie Details")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(AppNavRoutes.PersonDetails.createRoute(123)) // Example ID
        }) {
            Text(text = "Go to Person Details")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(AppNavRoutes.TVShowDetails.createRoute(123)) // Example ID
        }) {
            Text(text = "Go to TV Show Details")
        }
    }*/
}