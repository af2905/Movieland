package com.github.af2905.movieland.presentation.feature.detail

import com.github.af2905.movieland.di.scope.FragmentScope
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.MovieDetailsFragment
import com.github.af2905.movieland.presentation.feature.detail.moviedetail.MovieDetailsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class DetailModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [MovieDetailsModule::class])
    abstract fun movieDetailsFragment(): MovieDetailsFragment
}