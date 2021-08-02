package com.github.af2905.movieland.di.module

import android.content.Context
import com.github.af2905.movieland.presentation.MainActivity
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun provideContext(mainActivity: MainActivity): Context
}
