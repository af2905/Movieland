package com.github.af2905.movieland.presentation.feature.profile.di

import com.github.af2905.movieland.di.AppComponent
import com.github.af2905.movieland.di.scope.FragmentScope
import com.github.af2905.movieland.presentation.feature.profile.ProfileFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface ProfileComponent {

    fun injectProfileFragment(fragment: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ProfileComponent
    }
}