package com.github.af2905.movieland.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.af2905.movieland.core.data.database.entity.Genres
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<Genres>)

    @Query("SELECT * FROM Genres ORDER BY name ASC")
    fun getGenres(): Flow<List<Genres>>

    @Query("SELECT * FROM Genres WHERE id = :genreId")
    suspend fun getGenreById(genreId: Int): Genres?

    @Query("DELETE FROM Genres")
    suspend fun deleteAllGenres()
}