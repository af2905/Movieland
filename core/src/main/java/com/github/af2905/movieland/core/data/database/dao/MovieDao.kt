package com.github.af2905.movieland.core.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.core.data.database.entity.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie WHERE movieType =:movieType")
    suspend fun getByType(movieType: String): List<Movie>?

    @Query("SELECT COUNT(*) FROM Movie WHERE movieType =:movieType")
    suspend fun getCountByType(movieType: String): Int?

    @Query("SELECT timeStamp FROM Movie WHERE movieType =:movieType LIMIT 1")
    suspend fun getTimeStampByType(movieType: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("DELETE FROM Movie")
    suspend fun deleteAll()

}