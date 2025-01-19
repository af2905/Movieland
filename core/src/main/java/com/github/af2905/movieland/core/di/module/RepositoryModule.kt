package com.github.af2905.movieland.core.di.module

import com.github.af2905.movieland.core.repository.GenresRepository
import com.github.af2905.movieland.core.repository.MoviesRepository
import com.github.af2905.movieland.core.repository.PeopleRepository
import com.github.af2905.movieland.core.repository.SearchRepository
import com.github.af2905.movieland.core.repository.TrendingRepository
import com.github.af2905.movieland.core.repository.TvShowsRepository
import com.github.af2905.movieland.core.repository.impl.GenresRepositoryImpl
import com.github.af2905.movieland.core.repository.impl.MoviesRepositoryImpl
import com.github.af2905.movieland.core.repository.impl.PeopleRepositoryImpl
import com.github.af2905.movieland.core.repository.impl.SearchRepositoryImpl
import com.github.af2905.movieland.core.repository.impl.TrendingRepositoryImpl
import com.github.af2905.movieland.core.repository.impl.TvShowsRepositoryImpl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMoviesRepository(moviesRepository: MoviesRepositoryImpl): MoviesRepository

    @Binds
    fun bindSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository

    @Binds
    fun bindPeopleRepository(peopleRepository: PeopleRepositoryImpl): PeopleRepository

    @Binds
    fun provideTvShowsRepository(tvShowsRepository: TvShowsRepositoryImpl): TvShowsRepository

    @Binds
    fun bindTrendingRepository(trendingRepository: TrendingRepositoryImpl): TrendingRepository

    @Binds
    fun bindGenresRepository(genresRepository: GenresRepositoryImpl): GenresRepository
}