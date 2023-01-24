package com.github.af2905.movieland.search

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import com.github.af2905.movieland.detail.moviedetail.presentation.MovieDetailFragment.Companion.MOVIE_ID_ARG
import com.github.af2905.movieland.detail.persondetail.presentation.PersonDetailFragment.Companion.PERSON_ID_ARG
import com.github.af2905.movieland.detail.tvshowdetail.presentation.TvShowDetailFragment
import com.github.af2905.movieland.search.presentation.SearchFragmentDirections
import javax.inject.Inject

class SearchNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardToMovieDetailScreen(id: Int) {
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
    }
}