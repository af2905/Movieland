package com.github.af2905.movieland.home.di.module

import com.github.af2905.movieland.home.HomeRepository
import com.github.af2905.movieland.home.HomeRepositoryImpl
import com.github.af2905.movieland.home.di.component.HomeScope
import dagger.Binds
import dagger.Module

@Module
abstract class HomeFragmentModule {

    @HomeScope
    @Binds
    abstract fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository
}