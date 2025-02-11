package com.github.af2905.movieland.detail.moviedetail.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.af2905.movieland.core.compose.AppNavRoutes
import com.github.af2905.movieland.detail.moviedetail.presentation.screen.MovieDetailsScreen
import com.github.af2905.movieland.detail.moviedetail.presentation.state.MovieDetailsEffect
import com.github.af2905.movieland.detail.moviedetail.presentation.viewmodel.MovieDetailsViewModel

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

                is MovieDetailsEffect.NavigateToPerson -> {
                    navController.navigate(
                        AppNavRoutes.PersonDetails.createRoute(effect.personId)
                    )
                }

                is MovieDetailsEffect.NavigateToVideo -> {
                    navController.navigate(AppNavRoutes.YouTubePlayer.createRoute(effect.videoId))
                }

                is MovieDetailsEffect.NavigateToMovies -> {
                    navController.navigate(
                        AppNavRoutes.Movies.createRoute(
                            movieId = effect.movieId,
                            movieType = effect.movieType
                        )
                    )
                }
            }
        }
    }

    MovieDetailsScreen(state = viewModel.state, onAction = viewModel::onAction)
}