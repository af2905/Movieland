package com.github.af2905.movieland.home.di.component

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.home.presentation.nowplaying.NowPlayingMovieFragment
import dagger.Component
import javax.inject.Scope

@NowPlayingMovieScope
@Component(dependencies = [CoreComponent::class, HomeComponent::class])
interface NowPlayingMovieComponent {

    fun injectNowPlayingMovieFragment(fragment: NowPlayingMovieFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            homeComponent: HomeComponent
        ): NowPlayingMovieComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class NowPlayingMovieScope