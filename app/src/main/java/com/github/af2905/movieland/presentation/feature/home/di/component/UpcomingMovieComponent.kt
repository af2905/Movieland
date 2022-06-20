package com.github.af2905.movieland.presentation.feature.home.di.component

import com.github.af2905.movieland.di.CoreComponent
import com.github.af2905.movieland.presentation.feature.home.upcoming.UpcomingMovieFragment
import dagger.Component
import javax.inject.Scope

@UpcomingMovieScope
@Component(dependencies = [CoreComponent::class, HomeComponent::class])
interface UpcomingMovieComponent {

    fun injectUpcomingMovieFragment(fragment: UpcomingMovieFragment)

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent, homeComponent: HomeComponent): UpcomingMovieComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class UpcomingMovieScope