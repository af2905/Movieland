package com.github.af2905.movieland.detail.persondetail

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import com.github.af2905.movieland.detail.moviedetail.presentation.MovieDetailFragment.Companion.MOVIE_ID_ARG
import com.github.af2905.movieland.detail.persondetail.presentation.PersonDetailFragmentDirections
import javax.inject.Inject

class PersonDetailNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardMovieDetail(id: Int) {
        val action = PersonDetailFragmentDirections.openMovieDetail().apply {
            arguments.putInt(MOVIE_ID_ARG, id)
        }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }
}