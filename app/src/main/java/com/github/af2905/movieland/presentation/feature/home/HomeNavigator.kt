package com.github.af2905.movieland.presentation.feature.home

import androidx.navigation.NavController
import com.github.af2905.movieland.R
import com.github.af2905.movieland.helper.navigator.Navigator
import javax.inject.Inject

class HomeNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardMovieDetail() =
        navController.navigate(R.id.action_homeFragment_to_movieDetailFragment)

}