package com.github.af2905.movieland.home.di.component

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.home.presentation.people.PopularPeopleFragment
import dagger.Component
import javax.inject.Scope

@PopularPeopleScope
@Component(dependencies = [CoreComponent::class, HomeComponent::class])
interface PopularPeopleComponent {

    fun injectPopularPeopleFragment(fragment: PopularPeopleFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            homeComponent: HomeComponent
        ): PopularPeopleComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PopularPeopleScope