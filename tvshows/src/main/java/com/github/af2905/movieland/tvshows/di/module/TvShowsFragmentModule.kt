package com.github.af2905.movieland.tvshows.di.module

import com.github.af2905.movieland.tvshows.di.component.TvShowsScope
import com.github.af2905.movieland.tvshows.repository.TvShowsForceUpdateRepository
import com.github.af2905.movieland.tvshows.repository.TvShowsForceUpdateRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class TvShowsFragmentModule {

    @TvShowsScope
    @Binds
    abstract fun bindForceUpdateRepository(impl: TvShowsForceUpdateRepositoryImpl): TvShowsForceUpdateRepository
}