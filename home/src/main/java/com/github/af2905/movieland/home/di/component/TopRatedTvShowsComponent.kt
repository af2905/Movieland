package com.github.af2905.movieland.home.di.component

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.home.presentation.tvShows.topRatedTvShows.TopRatedTvShowsFragment
import dagger.Component
import javax.inject.Scope

@TopRatedTvShowsScope
@Component(dependencies = [CoreComponent::class, HomeComponent::class])
interface TopRatedTvShowsComponent {

    fun injectTopRatedTvShowsFragment(fragment: TopRatedTvShowsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            homeComponent: HomeComponent
        ): TopRatedTvShowsComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class TopRatedTvShowsScope