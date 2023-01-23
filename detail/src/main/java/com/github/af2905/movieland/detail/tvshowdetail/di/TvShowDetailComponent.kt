package com.github.af2905.movieland.detail.tvshowdetail.di

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.core.di.scope.FragmentScope
import com.github.af2905.movieland.detail.tvshowdetail.presentation.TvShowDetailFragment
import dagger.BindsInstance
import dagger.Component

@FragmentScope
@Component(dependencies = [CoreComponent::class])
interface TvShowDetailComponent {

    fun injectTvShowDetailFragment(fragment: TvShowDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent,
            @BindsInstance tvShowId: Int
        ): TvShowDetailComponent
    }
}