package com.github.af2905.movieland.home.di.component.movie

import androidx.fragment.app.Fragment
import com.github.af2905.movieland.core.di.CoreComponent
import com.github.af2905.movieland.home.di.module.MoviesFragmentModule
import com.github.af2905.movieland.home.presentation.movies.MoviesFragment
import com.github.af2905.movieland.home.repository.ForceUpdateRepository
import dagger.Component
import javax.inject.Scope

@MoviesScope
@Component(modules = [MoviesFragmentModule::class], dependencies = [CoreComponent::class])
interface MoviesComponent {

    fun injectMoviesFragment(fragment: MoviesFragment)
    fun getForceUpdateRepository() : ForceUpdateRepository

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): MoviesComponent
    }
}

object MoviesComponentProvider {
    fun getMoviesComponent(fragment: Fragment?) = (fragment as? MoviesFragment)?.moviesComponent
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MoviesScope
