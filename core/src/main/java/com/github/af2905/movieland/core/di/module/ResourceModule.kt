package com.github.af2905.movieland.core.di.module

import android.content.Context
import com.github.af2905.movieland.core.data.datastore.ResourceDatastore
import com.github.af2905.movieland.core.di.scope.AppScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
//TODO should be deleted
@Module
@InstallIn(SingletonComponent::class)
class ResourceModule {

    @Singleton
    @Provides
    fun provideResourceDatastore(@ApplicationContext context: Context): ResourceDatastore {
        return ResourceDatastore(context)
    }
}