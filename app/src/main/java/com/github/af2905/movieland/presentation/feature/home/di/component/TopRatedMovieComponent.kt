package com.github.af2905.movieland.presentation.feature.home.di.component

import com.github.af2905.movieland.di.AppComponent
import com.github.af2905.movieland.di.scope.HomeScope
import com.github.af2905.movieland.presentation.feature.home.di.module.HomeFragmentModule
import com.github.af2905.movieland.presentation.feature.home.toprated.TopRatedMovieFragment
import dagger.Component

@HomeScope
@Component(modules = [HomeFragmentModule::class], dependencies = [AppComponent::class])
interface TopRatedMovieComponent {

    fun injectTopRatedMovieFragment(fragment: TopRatedMovieFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): TopRatedMovieComponent
    }
}