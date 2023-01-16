package com.github.af2905.movieland.people.presentation

import androidx.navigation.NavController
import com.github.af2905.movieland.core.base.navigator.NavOptions
import com.github.af2905.movieland.core.base.navigator.Navigator
import com.github.af2905.movieland.detail.persondetail.presentation.PersonDetailFragment
import javax.inject.Inject

class PeopleNavigator @Inject constructor(
    navController: NavController
) : Navigator(navController) {

    fun forwardToPersonDetailScreen(id: Int) {
        val action = PeopleFragmentDirections.openPersonDetail().apply {
            arguments.putInt(PersonDetailFragment.PERSON_ID_ARG, id)
        }

        navController.navigate(
            directions = action,
            navOptions = NavOptions.optionsAnimSlide()
        )
    }
}