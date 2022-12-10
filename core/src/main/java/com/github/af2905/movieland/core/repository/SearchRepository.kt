package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.Movie

interface SearchRepository {

    suspend fun getSearchMovie(
        query: String,
        language: String? = null,
        page: Int? = null,
        adult: String? = null,
        region: String? = null,
        year: Int? = null
    ): List<Movie>
}