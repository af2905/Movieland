package com.github.af2905.movieland.core.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.core.data.database.entity.TvShow

@Dao
interface TvShowDao {

    @Query("SELECT * FROM TvShow WHERE tvShowType =:tvShowType")
    suspend fun getByType(tvShowType: String): List<TvShow>?

    @Query("SELECT COUNT(*) FROM TvShow WHERE tvShowType =:tvShowType")
    suspend fun getCountByType(tvShowType: String): Int?

    @Query("SELECT timeStamp FROM TvShow WHERE tvShowType =:tvShowType LIMIT 1")
    suspend fun getTimeStampByType(tvShowType: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(tvShow: TvShow)

    @Delete
    suspend fun delete(tvShow: TvShow)
}