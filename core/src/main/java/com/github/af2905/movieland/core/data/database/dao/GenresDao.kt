package com.github.af2905.movieland.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.af2905.movieland.core.data.database.entity.Genre
import com.github.af2905.movieland.core.data.database.entity.GenreType
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresDao {

    @Query("SELECT * FROM Genre WHERE genreType = :genreType")
    fun getGenresByType(genreType: GenreType): Flow<List<Genre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<Genre>)

    @Query("DELETE FROM Genre WHERE genreType = :genreType")
    suspend fun deleteGenresByType(genreType: GenreType)

    @Query("SELECT MAX(timeStamp) FROM Genre WHERE genreType = :genreType")
    suspend fun getLastUpdated(genreType: GenreType): Long?
}