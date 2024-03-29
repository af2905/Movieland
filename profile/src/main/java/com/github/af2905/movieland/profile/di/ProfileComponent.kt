package com.github.af2905.movieland.profile.di

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.core.di.scope.FragmentScope
import com.github.af2905.movieland.profile.presentation.profile.ProfileFragment
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