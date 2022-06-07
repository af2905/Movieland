package com.github.af2905.movieland.presentation.feature.home.di.module

import com.github.af2905.movieland.di.scope.HomeScope
import com.github.af2905.movieland.presentation.feature.home.HomeRepository
import com.github.af2905.movieland.presentation.feature.home.HomeRepositoryImpl
import dagger.Binds
import dagger.Module


@Module
abstract class HomeFragmentModule {

    @HomeScope
    @Binds
    abstract fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository
}