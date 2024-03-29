package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.SearchApi
import com.github.af2905.movieland.core.data.datastore.ResourceDatastore
import com.github.af2905.movieland.core.data.dto.movie.MovieDto
import com.github.af2905.movieland.core.data.dto.search.SearchMultiResultDto
import com.github.af2905.movieland.core.repository.SearchRepository
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
    ): List<MovieDto> {
        return searchApi.searchMovie(
            query = query,
            language = language ?: resourceDatastore.getLanguage(),
            page = page,
            adult = adult,
            region = region,
            year = year
        ).movies
    }

    override suspend fun getSearchMulti(
        query: String,
        language: String?,
        page: Int?,
        adult: String?,
        region: String?
    ): List<SearchMultiResultDto> {
        return searchApi.searchMulti(
            query = query,
            language = language ?: resourceDatastore.getLanguage(),
            page = page,
            adult = adult,
            region = region
        ).results
    }
}