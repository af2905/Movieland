package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.SearchApi
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.plain.SearchMulti
import com.github.af2905.movieland.core.data.datastore.ResourceDatastore
import com.github.af2905.movieland.core.data.mapper.MovieDtoToMovieListMapper
import com.github.af2905.movieland.core.data.mapper.SearchMultiDtoToSearchMultiListMapper
import com.github.af2905.movieland.core.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi,
    private val resourceDatastore: ResourceDatastore,
    private val movieDtoMapper: MovieDtoToMovieListMapper,
    private val searchMultiDtoMapper: SearchMultiDtoToSearchMultiListMapper
) : SearchRepository {

    override suspend fun getSearchMovie(
        query: String,
        language: String?,
        page: Int?,
        adult: String?,
        region: String?,
        year: Int?
    ): List<Movie> {
        return movieDtoMapper.map(
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

    override suspend fun getSearchMulti(
        query: String,
        language: String?,
        page: Int?,
        adult: String?,
        region: String?
    ): List<SearchMulti> {
        return searchMultiDtoMapper.map(
            searchApi.searchMulti(
                query = query,
                language = language ?: resourceDatastore.getLanguage(),
                page = page,
                adult = adult,
                region = region
            ).results
        )
    }
}