package com.github.af2905.movieland.profile.presentation.optionLiked.people

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import com.github.af2905.movieland.detail.persondetail.presentation.PersonDetailFragment
import com.github.af2905.movieland.profile.presentation.optionLiked.OptionLikedFragmentDirections
import javax.inject.Inject

class LikedPeopleNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardPersonDetail(personId: Int) {
        val action = OptionLikedFragmentDirections.openPersonDetail()
            .apply { arguments.putInt(PersonDetailFragment.PERSON_ID_ARG, personId) }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }
}