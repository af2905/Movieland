package com.github.af2905.movieland.detail.tvshowdetail.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.af2905.movieland.core.compose.AppNavRoutes
import com.github.af2905.movieland.detail.tvshowdetail.presentation.screen.TvShowDetailsScreen
import com.github.af2905.movieland.detail.tvshowdetail.presentation.state.TvShowDetailsEffect
import com.github.af2905.movieland.detail.tvshowdetail.presentation.viewmodel.TvShowDetailsViewModel

@Composable
fun TvShowDetailsNavWrapper(
    tvShowId: Int,
    navController: NavHostController
) {
    val viewModel = hiltViewModel<TvShowDetailsViewModel, TvShowDetailsViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(tvShowId)
        })

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TvShowDetailsEffect.NavigateBack -> {
                    navController.popBackStack()
                }

                is TvShowDetailsEffect.NavigateToPerson -> {
                    navController.navigate(
                        AppNavRoutes.PersonDetails.createRoute(effect.personId)
                    )
                }

                is TvShowDetailsEffect.NavigateToVideo -> {
                    navController.navigate(AppNavRoutes.YouTubePlayer.createRoute(effect.videoId))
                }

                is TvShowDetailsEffect.NavigateToTvShows -> {
                    navController.navigate(
                        AppNavRoutes.TvShows.createRoute(
                            tvShowId = effect.tvShowId,
                            tvShowType = effect.tvShowType
                        )
                    )
                }
            }
        }
    }

    TvShowDetailsScreen(state = viewModel.state, onAction = viewModel::onAction)
}
