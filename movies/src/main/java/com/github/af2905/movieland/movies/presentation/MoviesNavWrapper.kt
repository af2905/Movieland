package com.github.af2905.movieland.movies.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.af2905.movieland.core.compose.AppNavRoutes
import com.github.af2905.movieland.core.data.database.entity.MovieType

@Composable
fun MoviesNavWrapper(
    movieType: MovieType,
    movieId: Int?,
    navController: NavHostController
) {
    val viewModel = hiltViewModel<MoviesViewModel, MoviesViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(
                movieType = movieType,
                movieId = movieId
            )
        }
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MoviesEffect.NavigateBack -> {
                    navController.popBackStack()
                }

                is MoviesEffect.NavigateToMovieDetail -> {
                    navController.navigate(AppNavRoutes.MovieDetails.createRoute(effect.movieId))
                }
            }
        }
    }

    val movies = viewModel.moviesPager.collectAsLazyPagingItems()
    MoviesScreen(
        movieType = viewModel.state.movieType,
        movies = movies,
        onAction = viewModel::onAction
    )
}