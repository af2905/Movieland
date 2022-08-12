package com.github.af2905.movieland.search

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import com.github.af2905.movieland.detail.moviedetail.MovieDetailFragment.Companion.MOVIE_ID_ARG
import com.github.af2905.movieland.search.presentation.SearchFragmentDirections
import javax.inject.Inject

class SearchNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardMovieDetail(movieId: Int) {
        val action = SearchFragmentDirections.openMovieDetail()
            .apply { arguments.putInt(MOVIE_ID_ARG, movieId) }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }
}