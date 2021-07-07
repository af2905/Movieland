package com.github.af2905.movieland.di.module

import com.github.af2905.movieland.MainActivity
import com.github.af2905.movieland.di.scope.AppScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @AppScope
    @ContributesAndroidInjector(
        modules = [MainActivityModule::class]
    )
    abstract fun mainActivity(): MainActivity
}