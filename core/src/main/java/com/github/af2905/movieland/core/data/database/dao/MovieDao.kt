package com.github.af2905.movieland.core.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieType
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie WHERE movieType = :movieType ORDER BY popularity DESC")
    fun getMoviesByType(movieType: MovieType): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("DELETE FROM Movie WHERE movieType = :movieType")
    suspend fun deleteMoviesByType(movieType: MovieType)

    @Query("SELECT MAX(timeStamp) FROM Movie WHERE movieType = :movieType")
    suspend fun getLastUpdated(movieType: MovieType): Long?

}