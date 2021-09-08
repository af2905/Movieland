package com.github.af2905.movieland.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.data.database.entity.MovieDetailsEntity

@Dao
interface MovieDetailsDao {

    @Query("SELECT * FROM MovieDetailsEntity")
    suspend fun getAll(): List<MovieDetailsEntity>

    @Query("SELECT * FROM MovieDetailsEntity WHERE id =:id")
    suspend fun getById(id: Int): MovieDetailsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movieDetailsEntity: MovieDetailsEntity)

    @Delete
    suspend fun delete(movieDetailsEntity: MovieDetailsEntity)
}