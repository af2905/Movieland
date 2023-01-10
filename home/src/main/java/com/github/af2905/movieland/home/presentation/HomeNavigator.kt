package com.github.af2905.movieland.home.presentation

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import com.github.af2905.movieland.detail.moviedetail.presentation.MovieDetailFragment.Companion.MOVIE_ID_ARG
import com.github.af2905.movieland.detail.persondetail.presentation.PersonDetailFragment.Companion.PERSON_ID_ARG
import javax.inject.Inject

class HomeNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardToMovieDetailScreen(movieId: Int) {
        val action = HomeFragmentDirections.openMovieDetail()
            .apply { arguments.putInt(MOVIE_ID_ARG, movieId) }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }

    fun forwardToPersonDetailScreen(id: Int) {
        val action = HomeFragmentDirections.openPersonDetail().apply {
            arguments.putInt(PERSON_ID_ARG, id)
        }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }

    fun forwardToMoviesScreen() {
        val action = HomeFragmentDirections.openMovies()

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }
}