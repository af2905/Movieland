package com.github.af2905.movieland.presentation.feature.home.di

import com.github.af2905.movieland.di.AppComponent
import com.github.af2905.movieland.di.scope.HomeScope
import com.github.af2905.movieland.presentation.feature.home.nowplaying.NowPlayingMovieFragment
import dagger.Component

@HomeScope
@Component(modules = [HomeFragmentModule::class], dependencies = [AppComponent::class])
interface NowPlayingMovieComponent {

    fun injectNowPlayingMovieFragment(fragment: NowPlayingMovieFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): NowPlayingMovieComponent
    }
}