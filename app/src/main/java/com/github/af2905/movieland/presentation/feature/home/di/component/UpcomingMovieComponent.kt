package com.github.af2905.movieland.presentation.feature.home.di.component

import com.github.af2905.movieland.di.AppComponent
import com.github.af2905.movieland.presentation.feature.home.upcoming.UpcomingMovieFragment
import dagger.Component
import javax.inject.Scope

@UpcomingMovieScope
@Component(dependencies = [AppComponent::class, HomeComponent::class])
interface UpcomingMovieComponent {

    fun injectUpcomingMovieFragment(fragment: UpcomingMovieFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent, homeComponent: HomeComponent): UpcomingMovieComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class UpcomingMovieScope