package com.github.af2905.movieland.presentation.feature.detail.moviedetail.di

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.core.di.scope.FragmentScope
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.MovieDetailFragment
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.MovieDetailFragmentArgs
import dagger.BindsInstance
import dagger.Component

@FragmentScope
@Component(dependencies = [CoreComponent::class])
interface MovieDetailComponent {

    fun injectMovieDetailFragment(fragment: MovieDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            @BindsInstance args: MovieDetailFragmentArgs
        ): MovieDetailComponent
    }
}