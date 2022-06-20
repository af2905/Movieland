package com.github.af2905.movieland.di.module

import android.content.Context
import androidx.room.Room
import com.github.af2905.movieland.data.database.AppDatabase
import com.github.af2905.movieland.data.database.converter.GenreConverter
import com.github.af2905.movieland.data.database.converter.ListIntConverter
import com.github.af2905.movieland.data.database.converter.ProductionCompanyConverter
import com.github.af2905.movieland.data.database.converter.ProductionCountryConverter
import com.github.af2905.movieland.data.database.dao.MovieDao
import com.github.af2905.movieland.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class StorageModule {

    @AppScope
    @Provides
    fun provideMovieDetailsDao(database: AppDatabase) = database.movieDetailsDao()

    companion object {

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
            Room.databaseBuilder(context, AppDatabase::class.java, "database")
                .addTypeConverter(listIntConverter)
                .addTypeConverter(genreConverter)
                .addTypeConverter(productionCompanyConverter)
                .addTypeConverter(productionCountryConverter)
                .build()
    }
}