package com.github.af2905.movieland.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.af2905.movieland.compose.theme.Themes
import com.github.af2905.movieland.core.compose.AppNavRoutes

@Composable
fun HomeScreenNavWrapper(
    navController: NavHostController,
    isDarkTheme: Boolean,
    currentTheme: Themes,
    onDarkThemeClick: () -> Unit,
    onThemeClick: (Themes) -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()


    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToMovieDetail -> {
                    navController.navigate(
                        AppNavRoutes.MovieDetails.createRoute(effect.movieId)
                    )
                }

                is HomeEffect.NavigateToTvShowDetail -> {
                    navController.navigate(
                        AppNavRoutes.TVShowDetails.createRoute(effect.tvShowId)
                    )
                }

                is HomeEffect.NavigateToPersonDetail -> {
                    navController.navigate(
                        AppNavRoutes.PersonDetails.createRoute(effect.personId)
                    )
                }

                is HomeEffect.NavigateToGenre -> {

                }
            }
        }
    }

    HomeScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}