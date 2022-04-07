package com.github.af2905.movieland.presentation.feature.detail

import com.github.af2905.movieland.di.scope.FragmentScope
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.MovieDetailFragment
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.MovieDetailModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class DetailModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [MovieDetailModule::class])
    abstract fun movieDetailsFragment(): MovieDetailFragment
}