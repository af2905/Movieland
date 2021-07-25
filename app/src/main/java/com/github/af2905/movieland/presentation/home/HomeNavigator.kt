package com.github.af2905.movieland.presentation.home

import androidx.navigation.NavController
import com.github.af2905.movieland.R
import com.github.af2905.movieland.di.qualifier.Global
import com.github.af2905.movieland.helper.Navigator
import javax.inject.Inject

class HomeNavigator @Inject constructor(
    @Global navController: NavController
) : Navigator(navController) {

    fun forwardMovieDetail() = navController.navigate(R.id.action_homeFragment_to_movieDetailFragment)

}