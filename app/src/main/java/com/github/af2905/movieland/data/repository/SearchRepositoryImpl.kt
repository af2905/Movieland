package com.github.af2905.movieland.data.repository

import com.github.af2905.movieland.data.api.SearchApi
import com.github.af2905.movieland.data.datastore.ResourceDatastore
import com.github.af2905.movieland.data.dto.movie.MoviesResponseDto
import com.github.af2905.movieland.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi,
    private val resourceDatastore: ResourceDatastore
) : SearchRepository {

    override suspend fun getSearchMovie(
        query: String,
        language: String?,
        page: Int?,
        adult: String?,
        region: String?,
        year: Int?
    ): MoviesResponseDto {
        return searchApi.searchMovie(
            query = query,
            language = language ?: resourceDatastore.getLanguage(),
            page = page,
            adult = adult,
            region = region,
            year = year
        )
    }
}