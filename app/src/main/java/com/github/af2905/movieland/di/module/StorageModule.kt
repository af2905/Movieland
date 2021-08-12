package com.github.af2905.movieland.di.module

import android.content.Context
import androidx.room.Room
import com.github.af2905.movieland.data.database.AppDatabase
import com.github.af2905.movieland.data.database.dao.MovieDao
import com.github.af2905.movieland.data.database.dao.MovieResponseDao
import com.github.af2905.movieland.di.qualifier.AppContext
import com.github.af2905.movieland.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class StorageModule {

/*    @AppScope
    @Provides
    fun provideMovieDetailsDao(database: AppDatabase) = database.movieDetailsDao()*/

    companion object {
        @AppScope
        @Provides
        fun provideMovieResponseDao(database: AppDatabase): MovieResponseDao =
            database.movieResponseDao()

        @AppScope
        @Provides
        fun provideMovieDao(database: AppDatabase): MovieDao = database.movieDao()

        @AppScope
        @Provides
        fun provideRoomDatabase(@AppContext context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    }
}