package com.github.af2905.movieland.di.module

import com.github.af2905.movieland.MainActivity
import com.github.af2905.movieland.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [MainActivityModule::class]
    )
    abstract fun mainActivity(): MainActivity
}