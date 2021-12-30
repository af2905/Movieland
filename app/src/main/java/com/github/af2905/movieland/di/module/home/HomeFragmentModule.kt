package com.github.af2905.movieland.di.module.home

import com.github.af2905.movieland.di.scope.FragmentScope
import com.github.af2905.movieland.di.scope.HomeScope
import com.github.af2905.movieland.presentation.feature.home.HomeRepository
import com.github.af2905.movieland.presentation.feature.home.HomeRepositoryImpl
import com.github.af2905.movieland.presentation.feature.home.popular.PopularMovieFragment
import com.github.af2905.movieland.presentation.feature.home.top.TopRatedMovieFragment
import com.github.af2905.movieland.presentation.feature.home.upcoming.UpcomingMovieFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun popularMovieFragment(): PopularMovieFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun upcomingMovieFragment(): UpcomingMovieFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun topMovieFragment(): TopRatedMovieFragment

    @HomeScope
    @Binds
    abstract fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository
}