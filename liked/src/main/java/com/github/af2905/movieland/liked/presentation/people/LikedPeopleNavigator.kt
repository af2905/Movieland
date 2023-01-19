package com.github.af2905.movieland.liked.presentation.people

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import com.github.af2905.movieland.detail.persondetail.presentation.PersonDetailFragment
import com.github.af2905.movieland.liked.presentation.LikedFragmentDirections
import javax.inject.Inject

class LikedPeopleNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardPersonDetail(personId: Int) {
        val action = LikedFragmentDirections.openPersonDetail()
            .apply { arguments.putInt(PersonDetailFragment.PERSON_ID_ARG, personId) }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }
}