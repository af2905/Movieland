package com.github.af2905.movieland.presentation.feature.home

import com.github.af2905.movieland.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment
}