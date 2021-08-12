package com.github.af2905.movieland.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.data.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Delete
    suspend fun delete(movie: MovieEntity)

    @Update
    suspend fun update(movie: MovieEntity)

}