package com.github.af2905.movieland.presentation.feature.home.di.component

import androidx.fragment.app.Fragment
import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.presentation.feature.home.HomeFragment
import com.github.af2905.movieland.presentation.feature.home.HomeRepository
import com.github.af2905.movieland.presentation.feature.home.di.module.HomeFragmentModule
import dagger.Component
import javax.inject.Scope

@HomeScope
@Component(modules = [HomeFragmentModule::class], dependencies = [CoreComponent::class])
interface HomeComponent {

    fun injectHomeFragment(fragment: HomeFragment)
    fun getHomeRepository() : HomeRepository

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): HomeComponent
    }
}

object HomeComponentProvider {
    fun getHomeComponent(fragment: Fragment?) = (fragment as? HomeFragment)?.homeComponent
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class HomeScope