package com.github.af2905.movieland.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.af2905.movieland.compose.theme.Themes
import com.github.af2905.movieland.core.compose.AppNavRoutes
import com.github.af2905.movieland.home.presentation.screen.HomeScreen
import com.github.af2905.movieland.home.presentation.state.HomeAction
import com.github.af2905.movieland.home.presentation.state.HomeEffect
import com.github.af2905.movieland.home.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreenNavWrapper(
    navController: NavHostController,
    isDarkTheme: Boolean,
    currentTheme: Themes,
    onDarkThemeClick: () -> Unit,
    onThemeClick: (theme: Themes) -> Unit
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
                    //TODO
                }

                is HomeEffect.ChangeAppColor -> {
                    onThemeClick(effect.selectedTheme)
                }

                is HomeEffect.NavigateToMovies -> {
                    navController.navigate(
                        AppNavRoutes.Movies.createRoute(
                            movieId = null,
                            movieType = effect.movieType
                        )
                    )
                }
            }
        }
    }

    HomeScreen(
        currentTheme = currentTheme,
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}