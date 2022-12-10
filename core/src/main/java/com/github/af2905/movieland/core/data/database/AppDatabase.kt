package com.github.af2905.movieland.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.af2905.movieland.core.data.database.converter.GenreConverter
import com.github.af2905.movieland.core.data.database.converter.ListIntConverter
import com.github.af2905.movieland.core.data.database.converter.ProductionCompanyConverter
import com.github.af2905.movieland.core.data.database.converter.ProductionCountryConverter
import com.github.af2905.movieland.core.data.database.dao.MovieDao
import com.github.af2905.movieland.core.data.database.dao.MovieDetailsDao
import com.github.af2905.movieland.core.data.database.entity.MovieDetailsEntity
import com.github.af2905.movieland.core.data.database.entity.MovieEntity

@Database(
    entities = [MovieEntity::class, MovieDetailsEntity::class],
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

    abstract fun movieDao(): MovieDao
    abstract fun movieDetailsDao(): MovieDetailsDao
}