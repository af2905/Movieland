package com.github.af2905.movieland.home.di.component.movie

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.home.presentation.movies.nowPlayingMovies.NowPlayingMovieFragment
import dagger.Component
import javax.inject.Scope

@NowPlayingMovieScope
@Component(dependencies = [CoreComponent::class, MoviesComponent::class])
interface NowPlayingMovieComponent {

    fun injectNowPlayingMovieFragment(fragment: NowPlayingMovieFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            moviesComponent: MoviesComponent
        ): NowPlayingMovieComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class NowPlayingMovieScope