package com.github.af2905.movieland.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.af2905.movieland.core.data.database.converter.*
import com.github.af2905.movieland.core.data.database.dao.*
import com.github.af2905.movieland.core.data.database.entity.*

@Database(
    entities = [
        Movie::class,
        TvShow::class,
        TvShowDetail::class,
        MovieDetail::class,
        PersonDetail::class,
        Person::class,
        Genre::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    value = [
        ListIntConverter::class,
        ListStringConverter::class,
        GenreConverter::class,
        ProductionCompanyConverter::class,
        ProductionCountryConverter::class,
        PersonMovieCreditsCastConverter::class,
        MovieCreditsCastConverter::class,
        KnownForConverter::class,
        MovieConverter::class,
        TvShowConverter::class,
        CreatedByConverter::class,
        NetworkConverter::class,
        SeasonConverter::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun movieDetailDao(): MovieDetailDao
    abstract fun personDao(): PersonDao
    abstract fun personDetailDao(): PersonDetailDao
    abstract fun tvShowDao(): TvShowDao
    abstract fun tvShowDetailDao(): TvShowDetailDao
    abstract fun genresDao(): GenresDao
}