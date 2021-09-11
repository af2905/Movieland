package com.github.af2905.movieland.presentation.feature.detail

import androidx.navigation.NavController
import com.github.af2905.movieland.helper.navigator.NavOptions
import com.github.af2905.movieland.helper.navigator.Navigator
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.MovieDetailsFragmentDirections
import javax.inject.Inject

class DetailNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardMovieDetail(movieId: Int) {
        navController.navigate(
            MovieDetailsFragmentDirections.openMovieDetails(movieId), NavOptions.optionsAnimSlide()
        )
    }
}