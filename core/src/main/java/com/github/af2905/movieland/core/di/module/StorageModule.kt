package com.github.af2905.movieland.core.di.module

import android.content.Context
import androidx.room.Room
import com.github.af2905.movieland.core.data.database.AppDatabase
import com.github.af2905.movieland.core.data.database.converter.GenreConverter
import com.github.af2905.movieland.core.data.database.converter.ListIntConverter
import com.github.af2905.movieland.core.data.database.converter.ProductionCompanyConverter
import com.github.af2905.movieland.core.data.database.converter.ProductionCountryConverter
import com.github.af2905.movieland.core.data.database.dao.MovieDao
import com.github.af2905.movieland.core.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class StorageModule {

    companion object {
        private const val DATABASE_NAME = "database"

        @AppScope
        @Provides
        fun providePersonDetailDao(database: AppDatabase) = database.personDetailDao()

        @AppScope
        @Provides
        fun provideMovieDetailDao(database: AppDatabase) = database.movieDetailDao()

        @AppScope
        @Provides
        fun provideMovieDao(database: AppDatabase): MovieDao = database.movieDao()

        @AppScope
        @Provides
        fun provideRoomDatabase(
            context: Context,
            listIntConverter: ListIntConverter,
            genreConverter: GenreConverter,
            productionCompanyConverter: ProductionCompanyConverter,
            productionCountryConverter: ProductionCountryConverter
        ): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addTypeConverter(listIntConverter)
                .addTypeConverter(genreConverter)
                .addTypeConverter(productionCompanyConverter)
                .addTypeConverter(productionCountryConverter)
                .build()
    }
}