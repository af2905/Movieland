package com.github.af2905.movieland.presentation.feature.search.di

import com.github.af2905.movieland.di.AppComponent
import com.github.af2905.movieland.di.scope.FragmentScope
import com.github.af2905.movieland.presentation.feature.search.SearchFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface SearchComponent {

    fun injectSearchFragment(fragment: SearchFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): SearchComponent
    }
}