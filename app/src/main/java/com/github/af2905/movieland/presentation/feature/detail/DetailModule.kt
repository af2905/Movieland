package com.github.af2905.movieland.presentation.feature.detail

import com.github.af2905.movieland.di.scope.FragmentScope
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.MovieDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun movieDetailFragment(): MovieDetailFragment
}