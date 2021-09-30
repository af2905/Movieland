package com.github.af2905.movieland.domain.repository

import com.github.af2905.movieland.data.dto.MoviesResponseDto

interface ISearchRepository {

    suspend fun getSearchMovie(
        query: String,
        language: String? = null,
        page: Int? = null,
        adult: String? = null,
        region: String? = null,
        year: Int? = null
    ): MoviesResponseDto
}