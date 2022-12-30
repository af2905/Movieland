package com.github.af2905.movieland.home.di.component

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.home.presentation.topRatedMovies.TopRatedMovieFragment
import dagger.Component
import javax.inject.Scope

@TopRatedMovieScope
@Component(dependencies = [CoreComponent::class, HomeComponent::class])
interface TopRatedMovieComponent {

    fun injectTopRatedMovieFragment(fragment: TopRatedMovieFragment)

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent, homeComponent: HomeComponent): TopRatedMovieComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class TopRatedMovieScope