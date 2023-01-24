package com.github.af2905.movieland.search.di

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.core.di.scope.FragmentScope
import com.github.af2905.movieland.search.presentation.SearchFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [CoreComponent::class])
interface SearchComponent {

    fun injectSearchFragment(fragment: SearchFragment)

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): SearchComponent
    }
}