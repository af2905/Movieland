package com.github.af2905.movieland.movies.di.module


import com.github.af2905.movieland.movies.di.component.MoviesScope
import com.github.af2905.movieland.movies.repository.MoviesForceUpdateRepository
import com.github.af2905.movieland.movies.repository.MoviesForceUpdateRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MoviesFragmentModule {

    @MoviesScope
    @Binds
    abstract fun bindForceUpdateRepository(impl: MoviesForceUpdateRepositoryImpl): MoviesForceUpdateRepository
}