package com.github.af2905.movieland.tvshows.di.component

import androidx.fragment.app.Fragment
import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.tvshows.di.module.TvShowsFragmentModule
import com.github.af2905.movieland.tvshows.presentation.TvShowsFragment
import com.github.af2905.movieland.tvshows.repository.TvShowsForceUpdateRepository
import dagger.Component
import javax.inject.Scope

@TvShowsScope
@Component(modules = [TvShowsFragmentModule::class], dependencies = [CoreComponent::class])
interface TvShowsComponent {

    fun injectTvShowsFragment(fragment: TvShowsFragment)
    fun getForceUpdateRepository(): TvShowsForceUpdateRepository

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): TvShowsComponent
    }
}

object TvShowsComponentProvider {
    fun getTvShowsComponent(fragment: Fragment?) = (fragment as? TvShowsFragment)?.tvShowsComponent
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class TvShowsScope