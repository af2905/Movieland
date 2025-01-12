package com.github.af2905.movieland.tvshows.presentation

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import com.github.af2905.movieland.detail.tvshowdetail.presentation.TvShowDetailFragment.Companion.TV_SHOW_ID_ARG
import javax.inject.Inject

class TvShowsNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardToTvShowDetailScreen(id: Int) {
        /*val action = TvShowsFragmentDirections.openTvShowDetail().apply {
            arguments.putInt(TV_SHOW_ID_ARG, id)
        }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )*/
    }
}