package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.SearchApi
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.datastore.ResourceDatastore
import com.github.af2905.movieland.core.data.mapper.MovieDtoToMovieListMapper
import com.github.af2905.movieland.core.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi,
    private val resourceDatastore: ResourceDatastore,
    private val mapper: MovieDtoToMovieListMapper
) : SearchRepository {

    override suspend fun getSearchMovie(
        query: String,
        language: String?,
        page: Int?,
        adult: String?,
        region: String?,
        year: Int?
    ): List<Movie> {
        return mapper.map(
            searchApi.searchMovie(
                query = query,
                language = language ?: resourceDatastore.getLanguage(),
                page = page,
                adult = adult,
                region = region,
                year = year
            ).movies
        )
    }
}