package com.github.af2905.movieland.search

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import javax.inject.Inject

class SearchNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    /*fun forwardToMovieDetailScreen(id: Int) {
        val action = SearchFragmentDirections.openMovieDetail()
            .apply { arguments.putInt(MOVIE_ID_ARG, id) }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }

    fun forwardToPersonDetailScreen(id: Int) {
        val action = SearchFragmentDirections.openPersonDetail()
            .apply { arguments.putInt(PERSON_ID_ARG, id) }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }

    fun forwardToTvShowDetailScreen(id: Int) {
        val action = SearchFragmentDirections.openTvShowDetail().apply {
            arguments.putInt(TvShowDetailFragment.TV_SHOW_ID_ARG, id)
        }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }*/
}