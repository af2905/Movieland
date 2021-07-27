package com.github.af2905.movieland.di.module

import com.github.af2905.movieland.di.scope.ActivityScope
import com.github.af2905.movieland.presentation.MainActivity
import com.github.af2905.movieland.presentation.feature.detail.DetailModule
import com.github.af2905.movieland.presentation.feature.home.HomeModule
import com.github.af2905.movieland.presentation.feature.profile.ProfileModule
import com.github.af2905.movieland.presentation.feature.search.SearchModule
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
            SearchModule::class,
            //NavigationModule::class
        ]
    )
    abstract fun mainActivity(): MainActivity
}