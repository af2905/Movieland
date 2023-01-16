package com.github.af2905.movieland.tvshows.di.component

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.tvshows.presentation.topRatedTvShows.TopRatedTvShowsFragment
import dagger.Component
import javax.inject.Scope

@TopRatedTvShowsScope
@Component(dependencies = [CoreComponent::class, TvShowsComponent::class])
interface TopRatedTvShowsComponent {

    fun injectTopRatedTvShowsFragment(fragment: TopRatedTvShowsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            tvShowsComponent: TvShowsComponent
        ): TopRatedTvShowsComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class TopRatedTvShowsScope
