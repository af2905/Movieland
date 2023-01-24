package com.github.af2905.movieland.detail.tvshowdetail

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import com.github.af2905.movieland.detail.persondetail.presentation.PersonDetailFragment.Companion.PERSON_ID_ARG
import com.github.af2905.movieland.detail.tvshowdetail.presentation.TvShowDetailFragment.Companion.TV_SHOW_ID_ARG
import com.github.af2905.movieland.detail.tvshowdetail.presentation.TvShowDetailFragmentDirections
import javax.inject.Inject

class TvShowDetailNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

        fun forwardToTvShowDetail(id: Int) {
        val action = TvShowDetailFragmentDirections.openTvShowDetail().apply {
            arguments.putInt(TV_SHOW_ID_ARG, id)
        }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }

    fun forwardToPersonDetail(id: Int) {
        val action = TvShowDetailFragmentDirections.openPersonDetail().apply {
            arguments.putInt(PERSON_ID_ARG, id)
        }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }
}