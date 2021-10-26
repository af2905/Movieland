package com.github.af2905.movieland.presentation.feature.search

import androidx.navigation.NavController
import com.github.af2905.movieland.helper.navigator.NavOptions
import com.github.af2905.movieland.helper.navigator.Navigator
import com.github.af2905.movieland.presentation.feature.home.HomeFragmentDirections
import javax.inject.Inject

class SearchNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardMovieDetail(movieId: Int) {
        navController.navigate(
            HomeFragmentDirections.openMovieDetails(movieId), NavOptions.optionsAnimSlide()
        )
    }
}