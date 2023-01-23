package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowDetail
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.core.data.dto.CreditsCastDto
import com.github.af2905.movieland.core.data.dto.tv.TvShowDetailDto
import com.github.af2905.movieland.core.data.dto.tv.TvShowDto

interface TvShowsRepository {
    suspend fun getPopularTvShows(
        language: String?,
        page: Int?,
        forceUpdate: Boolean
    ): List<TvShow>

    suspend fun getTopRatedTvShows(
        language: String?,
        page: Int?,
        forceUpdate: Boolean
    ): List<TvShow>

    suspend fun getTvShowDetail(tvShowId: Int, language: String?): TvShowDetailDto
    suspend fun getTvShowCredits(tvShowId: Int, language: String?): List<CreditsCastDto>
    suspend fun getSimilarTvShows(tvShowId: Int, language: String?): List<TvShowDto>

    suspend fun getCachedTvShowsByType(type: TvShowType): List<TvShow>

    suspend fun saveTvShowDetail(tvShowDetail: TvShowDetail): Boolean
    suspend fun removeTvShowDetail(tvShowDetail: TvShowDetail): Boolean
    suspend fun getTvShowDetailById(id: Int): TvShowDetail?
    suspend fun getAllSavedTvShowDetail(): List<TvShowDetail>
}