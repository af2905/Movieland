package com.github.af2905.movieland.presentation.feature.search

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import com.github.af2905.movieland.presentation.feature.home.HomeFragmentDirections
import javax.inject.Inject

class SearchNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardMovieDetail(movieId: Int) {
        navController.navigate(
            HomeFragmentDirections.openMovieDetail(movieId), NavOptions.optionsAnimSlide()
        )
    }
}