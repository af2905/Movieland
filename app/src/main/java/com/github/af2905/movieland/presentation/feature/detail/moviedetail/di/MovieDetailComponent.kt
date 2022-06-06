package com.github.af2905.movieland.presentation.feature.detail.moviedetail.di

import com.github.af2905.movieland.di.AppComponent
import com.github.af2905.movieland.di.scope.FragmentScope
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.MovieDetailFragment
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.MovieDetailFragmentArgs
import dagger.BindsInstance
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface MovieDetailComponent {

    fun injectMovieDetailFragment(fragment: MovieDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(
            appComponent: AppComponent,
            @BindsInstance args: MovieDetailFragmentArgs
        ): MovieDetailComponent
    }
}