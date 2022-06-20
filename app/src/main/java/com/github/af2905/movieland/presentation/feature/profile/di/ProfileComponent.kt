package com.github.af2905.movieland.presentation.feature.profile.di

import com.github.af2905.movieland.di.CoreComponent
import com.github.af2905.movieland.di.scope.FragmentScope
import com.github.af2905.movieland.presentation.feature.profile.ProfileFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [CoreComponent::class])
interface ProfileComponent {

    fun injectProfileFragment(fragment: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): ProfileComponent
    }
}