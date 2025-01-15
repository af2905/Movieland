package com.github.af2905.movieland.core.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.core.data.database.entity.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie WHERE movieType = :movieType ORDER BY popularity DESC")
    fun getMoviesByType(movieType: String): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("DELETE FROM Movie WHERE movieType = :movieType")
    suspend fun deleteMoviesByType(movieType: String)

    @Query("SELECT MAX(timeStamp) FROM Movie WHERE movieType = :movieType")
    suspend fun getLastUpdated(movieType: String): Long?

}