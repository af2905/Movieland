package com.github.af2905.movieland.people.di

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.core.di.scope.FragmentScope
import com.github.af2905.movieland.people.presentation.PeopleFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [CoreComponent::class])
interface PeopleComponent {

    fun injectPeopleFragment(fragment: PeopleFragment)

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): PeopleComponent
    }
}
