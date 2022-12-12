package com.github.af2905.movieland.profile.presentation.profile

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.Navigator
import javax.inject.Inject

class ProfileNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardLikedMovies() {
        navController.navigate(ProfileFragmentDirections.openLikedMovies())
    }
}