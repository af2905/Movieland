package com.github.af2905.movieland.home.di.component.movie

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.home.presentation.movies.popularMovies.PopularMovieFragment
import dagger.Component
import javax.inject.Scope

@PopularMovieScope
@Component(dependencies = [CoreComponent::class, MoviesComponent::class])
interface PopularMovieComponent {

    fun injectPopularMovieFragment(fragment: PopularMovieFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            moviesComponent: MoviesComponent
        ): PopularMovieComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PopularMovieScope