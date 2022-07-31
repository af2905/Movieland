package com.github.af2905.movieland.core.di.module

import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.core.repository.PeopleRepository
import com.github.af2905.movieland.core.repository.SearchRepository
import com.github.af2905.movieland.core.repository.impl.MoviesRepositoryImpl
import com.github.af2905.movieland.core.repository.impl.PeopleRepositoryImpl
import com.github.af2905.movieland.core.repository.impl.SearchRepositoryImpl

import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindMoviesRepository(moviesRepository: MoviesRepositoryImpl): MoviesRepository

    @Binds
    fun bindSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository

    @Binds
    fun bindPeopleRepository(peopleRepository: PeopleRepositoryImpl): PeopleRepository
}