package com.github.af2905.movieland.presentation.feature.home

import com.github.af2905.movieland.di.module.home.HomeFragmentModule
import com.github.af2905.movieland.di.scope.HomeScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

    @HomeScope
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun homeFragment(): HomeFragment

}