package com.github.af2905.movieland.presentation.feature.home.di.component

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.presentation.feature.home.popular.PopularMovieFragment
import dagger.Component
import javax.inject.Scope

@PopularMovieScope
@Component(dependencies = [CoreComponent::class, HomeComponent::class])
interface PopularMovieComponent {

    fun injectPopularMovieFragment(fragment: PopularMovieFragment)

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent, homeComponent: HomeComponent): PopularMovieComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PopularMovieScope