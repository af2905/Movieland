package com.github.af2905.movieland.core.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.core.data.database.entity.TvShowDetail

@Dao
interface TvShowDetailDao {
    @Query("SELECT * FROM TvShowDetail")
    suspend fun getAll(): List<TvShowDetail>?

    @Query("SELECT * FROM TvShowDetail WHERE id =:id")
    suspend fun getById(id: Int): TvShowDetail?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(tvShowDetail: TvShowDetail): Long?

    @Delete
    suspend fun delete(tvShowDetail: TvShowDetail): Int?
}