package com.github.af2905.movieland.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.af2905.movieland.data.database.dao.MovieDao
import com.github.af2905.movieland.data.database.dao.MovieResponseDao
import com.github.af2905.movieland.data.database.entity.MovieEntity
import com.github.af2905.movieland.data.database.entity.MoviesResponseEntity

@Database(
    //entities = [MoviesResponseEntity::class, MovieDetailsEntity::class],
    entities = [MoviesResponseEntity::class, MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieResponseDao(): MovieResponseDao
    abstract fun movieDao(): MovieDao
    // abstract fun movieDetailsDao(): MovieDetailsDao
}