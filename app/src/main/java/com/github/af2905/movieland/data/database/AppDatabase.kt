package com.github.af2905.movieland.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.af2905.movieland.data.database.converter.GenreConverter
import com.github.af2905.movieland.data.database.converter.ListIntConverter
import com.github.af2905.movieland.data.database.converter.ProductionCompanyConverter
import com.github.af2905.movieland.data.database.converter.ProductionCountryConverter
import com.github.af2905.movieland.data.database.dao.MovieDao
import com.github.af2905.movieland.data.database.dao.MovieDetailsDao
import com.github.af2905.movieland.data.database.dao.MovieResponseDao
import com.github.af2905.movieland.data.database.entity.MovieDetailsEntity
import com.github.af2905.movieland.data.database.entity.MovieEntity
import com.github.af2905.movieland.data.database.entity.MoviesResponseEntity

@Database(
    entities = [MoviesResponseEntity::class, MovieEntity::class, MovieDetailsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        ListIntConverter::class,
        GenreConverter::class,
        ProductionCompanyConverter::class,
        ProductionCountryConverter::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieResponseDao(): MovieResponseDao
    abstract fun movieDao(): MovieDao
    abstract fun movieDetailsDao(): MovieDetailsDao

}