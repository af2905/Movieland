package com.github.af2905.movieland.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.data.database.entity.MoviesResponseEntity
import com.github.af2905.movieland.data.database.entity.ResponseWithMovies

@Dao
interface MovieResponseDao {

    @Query("SELECT * FROM MoviesResponseEntity WHERE movieType =:movieType")
    suspend fun getByType(movieType: String): ResponseWithMovies?

    @Query("SELECT totalResults FROM MoviesResponseEntity WHERE movieType =:movieType")
    suspend fun getTotalResultsByType(movieType: String): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movieResponse: MoviesResponseEntity)

    @Delete
    suspend fun delete(movieResponse: MoviesResponseEntity)

    @Query("DELETE FROM MoviesResponseEntity")
    suspend fun clearAll()

}