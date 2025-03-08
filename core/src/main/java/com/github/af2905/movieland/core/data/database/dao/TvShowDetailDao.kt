package com.github.af2905.movieland.core.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.core.data.database.entity.TvShowDetail

@Dao
interface TvShowDetailDao {
    @Query("SELECT * FROM TvShowDetail WHERE id = :id")
    suspend fun getById(id: Int): TvShowDetail?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(tvShowDetail: TvShowDetail)

    @Delete
    suspend fun delete(tvShowDetail: TvShowDetail)

    @Transaction
    suspend fun toggleTvShowLike(tvShow: TvShowDetail) {
        val existing = getById(tvShow.id)
        if (existing == null) {
            save(tvShow.copy(liked = true))
        } else {
            delete(tvShow)
        }
    }

    @Query("SELECT EXISTS(SELECT * FROM TvShowDetail WHERE id = :id)")
    suspend fun isTvShowSaved(id: Int): Boolean
}
