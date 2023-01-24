package com.github.af2905.movieland.core.di.module

import android.content.Context
import com.github.af2905.movieland.core.data.datastore.ResourceDatastore
import com.github.af2905.movieland.core.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class ResourceModule {

    @AppScope
    @Provides
    fun provideResourceDatastore(context: Context) = ResourceDatastore(context)
}