package com.github.af2905.movieland.home.di.component

import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.home.presentation.HomeFragment
import dagger.Component
import javax.inject.Scope

@HomeScope
@Component(dependencies = [CoreComponent::class])
interface HomeComponent {

    fun injectHomeFragment(fragment: HomeFragment)

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): HomeComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class HomeScope