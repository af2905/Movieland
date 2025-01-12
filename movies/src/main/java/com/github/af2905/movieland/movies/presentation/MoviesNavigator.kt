package com.github.af2905.movieland.movies.presentation

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import com.github.af2905.movieland.detail.moviedetail.presentation.MovieDetailFragment.Companion.MOVIE_ID_ARG
import javax.inject.Inject

class MoviesNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardToMovieDetailScreen(movieId: Int) {
  /*      val action = MoviesFragmentDirections.openMovieDetail()
            .apply { arguments.putInt(MOVIE_ID_ARG, movieId) }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )*/
    }
}