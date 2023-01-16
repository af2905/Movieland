package com.github.af2905.movieland.movies.di.component

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.movies.presentation.topRatedMovies.TopRatedMovieFragment
import dagger.Component
import javax.inject.Scope

@TopRatedMovieScope
@Component(dependencies = [CoreComponent::class, MoviesComponent::class])
interface TopRatedMovieComponent {

    fun injectTopRatedMovieFragment(fragment: TopRatedMovieFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            moviesComponent: MoviesComponent
        ): TopRatedMovieComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class TopRatedMovieScope