package com.github.af2905.movieland.liked.presentation.tvshows

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import com.github.af2905.movieland.detail.tvshowdetail.presentation.TvShowDetailFragment.Companion.TV_SHOW_ID_ARG
import com.github.af2905.movieland.liked.presentation.LikedFragmentDirections
import javax.inject.Inject

class LikedTvShowsNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardToTvShowDetail(tvShowId: Int) {
        val action = LikedFragmentDirections.openTvShowDetail().apply {
            arguments.putInt(TV_SHOW_ID_ARG, tvShowId)
        }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }
}