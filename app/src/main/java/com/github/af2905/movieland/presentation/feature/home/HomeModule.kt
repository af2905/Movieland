package com.github.af2905.movieland.presentation.feature.home

import com.github.af2905.movieland.di.scope.FragmentScope
import com.github.af2905.movieland.presentation.feature.home.popular.PopularMovieFragment
import com.github.af2905.movieland.presentation.feature.home.top.TopRatedMovieFragment
import com.github.af2905.movieland.presentation.feature.home.upcoming.UpcomingMovieFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun popularMovieFragment(): PopularMovieFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun upcomingMovieFragment(): UpcomingMovieFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun topMovieFragment(): TopRatedMovieFragment

}