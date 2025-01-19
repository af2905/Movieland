package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {
    fun getTvShows(
        tvShowType: TvShowType,
        language: String?,
        page: Int?,
    ): Flow<List<TvShow>>
}