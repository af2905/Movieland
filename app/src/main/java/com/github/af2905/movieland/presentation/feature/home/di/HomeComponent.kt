package com.github.af2905.movieland.presentation.feature.home.di

import com.github.af2905.movieland.di.AppComponent
import com.github.af2905.movieland.di.scope.HomeScope
import com.github.af2905.movieland.presentation.feature.home.HomeFragment
import dagger.Component

@HomeScope
@Component(modules = [HomeFragmentModule::class], dependencies = [AppComponent::class])
interface HomeComponent {

    fun injectHomeFragment(fragment: HomeFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): HomeComponent
    }
}