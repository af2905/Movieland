package com.github.af2905.movieland.di.module

import com.github.af2905.movieland.data.repository.MoviesRepositoryImpl
import com.github.af2905.movieland.data.repository.PeopleRepositoryImpl
import com.github.af2905.movieland.data.repository.SearchRepositoryImpl
import com.github.af2905.movieland.domain.repository.MoviesRepository
import com.github.af2905.movieland.domain.repository.PeopleRepository
import com.github.af2905.movieland.domain.repository.SearchRepository
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