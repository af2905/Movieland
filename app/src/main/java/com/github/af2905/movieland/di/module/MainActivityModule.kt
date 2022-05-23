package com.github.af2905.movieland.di.module

import android.content.Context
import com.github.af2905.movieland.di.qualifier.MainActivityContext
import com.github.af2905.movieland.presentation.MainActivity
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @Binds
    @MainActivityContext
    abstract fun provideContext(mainActivity: MainActivity): Context
}
