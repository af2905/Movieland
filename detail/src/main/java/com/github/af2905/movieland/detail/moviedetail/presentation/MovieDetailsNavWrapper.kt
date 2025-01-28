package com.github.af2905.movieland.detail.moviedetail.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.af2905.movieland.core.compose.AppNavRoutes

@Composable
fun MovieDetailsNavWrapper(
    itemId: Int,
    navController: NavHostController
) {

    val viewModel = hiltViewModel<MovieDetailsViewModel, MovieDetailsViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(itemId)
        })

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MovieDetailsEffect.NavigateBack -> {
                    navController.popBackStack()
                }

                is MovieDetailsEffect.NavigateToMovieDetail -> {
                    navController.navigate(
                        AppNavRoutes.MovieDetails.createRoute(effect.movieId)
                    )
                }

                is MovieDetailsEffect.NavigateToGenre -> {
                    /*navController.navigate(
                        AppNavRoutes.Genre.createRoute(effect.genreId)
                    )*/

                }
            }
        }
    }

    MovieDetailsScreen(state = viewModel.state, onAction = viewModel::onAction)
}