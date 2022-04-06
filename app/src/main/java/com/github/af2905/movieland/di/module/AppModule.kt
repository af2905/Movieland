package com.github.af2905.movieland.di.module

import android.content.Context
import com.github.af2905.movieland.di.qualifier.AppContext
import com.github.af2905.movieland.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @AppScope
    @Provides
    @AppContext
    fun provideAppContext() = context
}