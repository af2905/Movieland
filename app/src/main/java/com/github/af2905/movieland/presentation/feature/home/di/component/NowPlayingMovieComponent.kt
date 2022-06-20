package com.github.af2905.movieland.presentation.feature.home.di.component

import com.github.af2905.movieland.di.CoreComponent
import com.github.af2905.movieland.presentation.feature.home.nowplaying.NowPlayingMovieFragment
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