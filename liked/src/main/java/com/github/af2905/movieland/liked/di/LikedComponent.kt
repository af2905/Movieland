package com.github.af2905.movieland.liked.di

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.core.di.scope.FragmentScope
import com.github.af2905.movieland.liked.presentation.LikedFragment
import com.github.af2905.movieland.liked.presentation.movies.LikedMoviesFragment
import com.github.af2905.movieland.liked.presentation.people.LikedPeopleFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [CoreComponent::class])
interface LikedComponent {

    fun injectLikedFragment(fragment: LikedFragment)
    fun injectLikedMoviesFragment(fragment: LikedMoviesFragment)
    fun injectLikedPeopleFragment(fragment: LikedPeopleFragment)

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): LikedComponent
    }
}