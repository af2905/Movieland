package com.github.af2905.movieland.search.presentation.result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.af2905.movieland.core.compose.AppNavRoutes

@Composable
fun SearchNavWrapper(
    navController: NavHostController
) {
    val viewModel: SearchViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchEffect.NavigateToMovieDetail -> {
                    navController.navigate(AppNavRoutes.MovieDetails.createRoute(effect.movieId))
                }
                is SearchEffect.NavigateToTvShowDetail -> {
                    navController.navigate(AppNavRoutes.TVShowDetails.createRoute(effect.tvShowId))
                }
                is SearchEffect.NavigateToPersonDetail -> {
                    navController.navigate(AppNavRoutes.PersonDetails.createRoute(effect.personId))
                }
            }
        }
    }

    SearchScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

