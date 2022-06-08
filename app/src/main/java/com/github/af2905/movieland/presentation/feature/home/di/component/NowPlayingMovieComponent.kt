package com.github.af2905.movieland.presentation.feature.home.di.component

import com.github.af2905.movieland.di.AppComponent
import com.github.af2905.movieland.presentation.feature.home.nowplaying.NowPlayingMovieFragment
import dagger.Component
import javax.inject.Scope

@NowPlayingMovieScope
@Component(dependencies = [AppComponent::class, HomeComponent::class])
interface NowPlayingMovieComponent {

    fun injectNowPlayingMovieFragment(fragment: NowPlayingMovieFragment)

    @Component.Factory
    interface Factory {
        fun create(
            appComponent: AppComponent,
            homeComponent: HomeComponent
        ): NowPlayingMovieComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class NowPlayingMovieScope