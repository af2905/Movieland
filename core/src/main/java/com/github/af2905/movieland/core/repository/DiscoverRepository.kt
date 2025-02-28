package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.TvShow
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {
    fun discoverMovies(
        language: String?,
        page: Int?,
        withGenres: Int?
    ): Flow<ResultWrapper<List<Movie>>>

    fun discoverTvShows(
        language: String?,
        page: Int?,
        withGenres: Int?
    ): Flow<ResultWrapper<List<TvShow>>>
}
