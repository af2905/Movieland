package com.github.af2905.movieland.tvshows.di.component

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.tvshows.presentation.popularTvShows.PopularTvShowsFragment
import dagger.Component
import javax.inject.Scope

@PopularTvShowsScope
@Component(dependencies = [CoreComponent::class, TvShowsComponent::class])
interface PopularTvShowsComponent {

    fun injectPopularTvShowsFragment(fragment: PopularTvShowsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            tvShowsComponent: TvShowsComponent
        ): PopularTvShowsComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PopularTvShowsScope
