package com.github.af2905.movieland.core.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Query("SELECT * FROM TvShow WHERE tvShowType = :tvShowType ORDER BY popularity DESC")
    fun getTvShowsByType(tvShowType: TvShowType): Flow<List<TvShow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tvShows: List<TvShow>)

    @Query("DELETE FROM TvShow WHERE tvShowType = :tvShowType")
    suspend fun deleteTvShowsByType(tvShowType: TvShowType)

    @Query("SELECT MAX(timeStamp) FROM TvShow WHERE tvShowType = :tvShowType")
    suspend fun getLastUpdated(tvShowType: TvShowType): Long?

    @Transaction
    suspend fun replaceTvShows(tvShowType: TvShowType, tvShows: List<TvShow>) {
        deleteTvShowsByType(tvShowType)
        insertTvShows(tvShows)
    }
}