package com.github.af2905.movieland.presentation.feature.home.di.component

import com.github.af2905.movieland.di.AppComponent
import com.github.af2905.movieland.di.scope.HomeScope
import com.github.af2905.movieland.presentation.feature.home.di.module.HomeFragmentModule
import com.github.af2905.movieland.presentation.feature.home.upcoming.UpcomingMovieFragment
import dagger.Component

@HomeScope
@Component(modules = [HomeFragmentModule::class], dependencies = [AppComponent::class])
interface UpcomingMovieComponent {

    fun injectUpcomingMovieFragment(fragment: UpcomingMovieFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): UpcomingMovieComponent
    }
}