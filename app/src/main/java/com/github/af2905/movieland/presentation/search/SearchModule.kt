package com.github.af2905.movieland.presentation.search

import com.github.af2905.movieland.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun searchFragment(): SearchFragment
}