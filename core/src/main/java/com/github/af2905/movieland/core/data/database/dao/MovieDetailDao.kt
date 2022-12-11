package com.github.af2905.movieland.core.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.core.data.database.entity.MovieDetail

@Dao
interface MovieDetailDao {

    @Query("SELECT * FROM MovieDetail")
    suspend fun getAll(): List<MovieDetail>?

    @Query("SELECT * FROM MovieDetail WHERE id =:id")
    suspend fun getById(id: Int): MovieDetail?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movieDetail: MovieDetail): Long?

    @Delete
    suspend fun delete(movieDetail: MovieDetail): Int?
}