package com.github.af2905.movieland.core.di.module

import android.content.Context
import androidx.room.Room
import com.github.af2905.movieland.core.data.database.AppDatabase
import com.github.af2905.movieland.core.data.database.converter.*
import com.github.af2905.movieland.core.data.database.dao.MovieDao
import com.github.af2905.movieland.core.data.database.dao.TvShowDao
import com.github.af2905.movieland.core.data.database.dao.TvShowDetailDao
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
        fun provideTvShowDetailDao(database: AppDatabase): TvShowDetailDao =
            database.tvShowDetailDao()

        @AppScope
        @Provides
        fun provideTvShowDao(database: AppDatabase): TvShowDao = database.tvShowDao()

        @AppScope
        @Provides
        fun provideRoomDatabase(
            context: Context,
            listIntConverter: ListIntConverter,
            listStringConverter: ListStringConverter,
            genreConverter: GenreConverter,
            productionCompanyConverter: ProductionCompanyConverter,
            productionCountryConverter: ProductionCountryConverter,
            personMovieCreditsCastConverter: PersonMovieCreditsCastConverter,
            movieCreditsCastConverter: MovieCreditsCastConverter,
            movieConverter: MovieConverter,
            //tvShowConverter: TvShowConverter,
            createdByConverter: CreatedByConverter,
            networkConverter: NetworkConverter,
            seasonConverter: SeasonConverter
        ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .addTypeConverter(listIntConverter)
            .addTypeConverter(listStringConverter)
            .addTypeConverter(genreConverter)
            .addTypeConverter(productionCompanyConverter)
            .addTypeConverter(productionCountryConverter)
            .addTypeConverter(personMovieCreditsCastConverter)
            .addTypeConverter(movieCreditsCastConverter)
            .addTypeConverter(movieConverter)
            //.addTypeConverter(tvShowConverter)
            .addTypeConverter(createdByConverter)
            .addTypeConverter(networkConverter)
            .addTypeConverter(seasonConverter)
            .build()
    }
}