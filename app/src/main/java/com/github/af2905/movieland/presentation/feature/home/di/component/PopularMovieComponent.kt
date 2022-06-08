package com.github.af2905.movieland.presentation.feature.home.di.component

import com.github.af2905.movieland.di.AppComponent
import com.github.af2905.movieland.presentation.feature.home.popular.PopularMovieFragment
import dagger.Component
import javax.inject.Scope

@PopularMovieScope
@Component(dependencies = [AppComponent::class, HomeComponent::class])
interface PopularMovieComponent {

    fun injectPopularMovieFragment(fragment: PopularMovieFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent, homeComponent: HomeComponent): PopularMovieComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PopularMovieScope