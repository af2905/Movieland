package com.github.af2905.movieland.presentation.feature.home

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import com.github.af2905.movieland.detail.moviedetail.MovieDetailFragment.Companion.MOVIE_ID_ARG
import javax.inject.Inject

class HomeNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardMovieDetail(movieId: Int) {
        val action = HomeFragmentDirections.openMovieDetail()
            .apply { arguments.putInt(MOVIE_ID_ARG, movieId) }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }
}