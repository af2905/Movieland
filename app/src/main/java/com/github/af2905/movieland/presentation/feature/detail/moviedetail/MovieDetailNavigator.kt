package com.github.af2905.movieland.presentation.feature.detail.moviedetail

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import javax.inject.Inject

class MovieDetailNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardMovieDetail(movieId: Int) {
        navController.navigate(
            MovieDetailFragmentDirections.openMovieDetail(movieId), NavOptions.optionsAnimSlide()
        )
    }

    fun forwardPersonDetail(personId: Int) {
        navController.navigate(
            MovieDetailFragmentDirections.openPersonDetail(personId), NavOptions.optionsAnimSlide()
        )
    }
}