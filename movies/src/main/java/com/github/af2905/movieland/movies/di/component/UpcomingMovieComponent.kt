package com.github.af2905.movieland.movies.di.component

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.movies.presentation.upcomingMovies.UpcomingMovieFragment
import dagger.Component
import javax.inject.Scope

@UpcomingMovieScope
@Component(dependencies = [CoreComponent::class, MoviesComponent::class])
interface UpcomingMovieComponent {

    fun injectUpcomingMovieFragment(fragment: UpcomingMovieFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            moviesComponent: MoviesComponent
        ): UpcomingMovieComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class UpcomingMovieScope