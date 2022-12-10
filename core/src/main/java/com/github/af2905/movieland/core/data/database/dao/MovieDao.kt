package com.github.af2905.movieland.core.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.core.data.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity WHERE movieType =:movieType")
    suspend fun getByType(movieType: String): List<MovieEntity>?

    @Query("SELECT COUNT(*) FROM MovieEntity WHERE movieType =:movieType")
    suspend fun getCountByType(movieType: String): Int?

    @Query("SELECT timeStamp FROM MovieEntity WHERE movieType =:movieType LIMIT 1")
    suspend fun getTimeStampByType(movieType: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movie: MovieEntity)

    @Delete
    suspend fun delete(movie: MovieEntity)

}