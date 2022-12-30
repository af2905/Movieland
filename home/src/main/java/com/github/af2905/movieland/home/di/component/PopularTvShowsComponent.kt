package com.github.af2905.movieland.home.di.component

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.home.presentation.tvShows.popularTvShows.PopularTvShowsFragment
import dagger.Component
import javax.inject.Scope

@PopularTvShowsScope
@Component(dependencies = [CoreComponent::class, HomeComponent::class])
interface PopularTvShowsComponent {

    fun injectPopularTvShowsFragment(fragment: PopularTvShowsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            homeComponent: HomeComponent
        ): PopularTvShowsComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PopularTvShowsScope