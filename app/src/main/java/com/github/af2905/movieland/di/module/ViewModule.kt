package com.github.af2905.movieland.di.module

import com.github.af2905.movieland.di.scope.ActivityScope
import com.github.af2905.movieland.presentation.MainActivity
import com.github.af2905.movieland.presentation.feature.home.HomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class,
            HomeModule::class
        ]
    )
    abstract fun mainActivity(): MainActivity
}