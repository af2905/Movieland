package com.github.af2905.movieland.core.di.module

import android.content.Context
import com.github.af2905.movieland.core.data.datastore.ResourceDatastore
import com.github.af2905.movieland.core.di.scope.AppScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ResourceModule {

    @AppScope
    @Provides
    fun provideResourceDatastore(context: Context) = ResourceDatastore(context)
}