package com.github.af2905.movieland.di.module

import com.github.af2905.movieland.data.repository.MoviesRepository
import com.github.af2905.movieland.data.repository.SearchRepository
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import com.github.af2905.movieland.domain.repository.ISearchRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindMoviesRepository(moviesRepository: MoviesRepository): IMoviesRepository

    @Binds
    fun bindSearchRepository(searchRepository: SearchRepository): ISearchRepository

}