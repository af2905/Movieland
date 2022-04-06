package com.github.af2905.movieland.di.module

import android.content.Context
import com.github.af2905.movieland.data.datastore.ResourceDatastore
import com.github.af2905.movieland.di.qualifier.AppContext
import com.github.af2905.movieland.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class ResourceModule {

    @AppScope
    @Provides
    fun provideResourceDatastore(@AppContext context: Context) = ResourceDatastore(context)
}