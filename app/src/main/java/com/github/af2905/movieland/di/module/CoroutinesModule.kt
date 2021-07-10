package com.github.af2905.movieland.di.module

import com.github.af2905.movieland.di.qualifier.DefaultDispatcher
import com.github.af2905.movieland.di.qualifier.IoDispatcher
import com.github.af2905.movieland.di.qualifier.MainDispatcher
import com.github.af2905.movieland.di.qualifier.MainImmediateDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object CoroutinesModule {

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @MainImmediateDispatcher
    @Provides
    fun provideMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}