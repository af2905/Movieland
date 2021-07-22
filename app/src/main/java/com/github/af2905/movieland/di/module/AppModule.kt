package com.github.af2905.movieland.di.module

import com.github.af2905.movieland.di.scope.ActivityScope
import com.github.af2905.movieland.presentation.MainActivity
import com.github.af2905.movieland.presentation.detail.DetailModule
import com.github.af2905.movieland.presentation.home.HomeModule
import com.github.af2905.movieland.presentation.profile.ProfileModule
import com.github.af2905.movieland.presentation.search.SearchModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class,
            DetailModule::class,
            HomeModule::class,
            ProfileModule::class,
            SearchModule::class
        ]
    )
    abstract fun mainActivity(): MainActivity
}