package com.github.af2905.movieland.liked.presentation.movies

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import com.github.af2905.movieland.detail.moviedetail.presentation.MovieDetailFragment
import com.github.af2905.movieland.liked.presentation.LikedFragmentDirections
import javax.inject.Inject

class LikedMoviesNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardMovieDetail(movieId: Int) {
        val action = LikedFragmentDirections.openMovieDetail().apply {
            arguments.putInt(MovieDetailFragment.MOVIE_ID_ARG, movieId)
        }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }
}