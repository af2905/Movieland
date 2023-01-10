package com.github.af2905.movieland.home.di.module

import com.github.af2905.movieland.home.di.component.movie.MoviesScope
import com.github.af2905.movieland.home.repository.ForceUpdateRepository
import com.github.af2905.movieland.home.repository.ForceUpdateRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MoviesFragmentModule {

    @MoviesScope
    @Binds
    abstract fun bindForceUpdateRepository(impl: ForceUpdateRepositoryImpl): ForceUpdateRepository
}