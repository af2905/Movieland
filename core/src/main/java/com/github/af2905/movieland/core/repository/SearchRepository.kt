package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.SearchMultiResult
import com.github.af2905.movieland.core.data.dto.movie.MovieDto

interface SearchRepository {

    suspend fun getSearchMovie(
        query: String,
        language: String? = null,
        page: Int? = null,
        adult: String? = null,
        region: String? = null,
        year: Int? = null
    ): List<MovieDto>

    suspend fun getSearchMulti(
        query: String,
        language: String? = null,
        page: Int? = null,
        adult: String? = null,
        region: String? = null,
    ): List<SearchMultiResult>
}