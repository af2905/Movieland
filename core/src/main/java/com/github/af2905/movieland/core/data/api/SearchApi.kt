package com.github.af2905.movieland.core.data.api

import com.github.af2905.movieland.core.data.ApiParams
import com.github.af2905.movieland.core.data.dto.movie.MoviesDto
import com.github.af2905.movieland.core.data.dto.search.SearchMultiDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/movie")
    suspend fun searchMovie(
        @Query(ApiParams.QUERY) query: String,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
        @Query(ApiParams.INCLUDE_ADULT) adult: String? = null,
        @Query(ApiParams.REGION) region: String? = null,
        @Query(ApiParams.YEAR) year: Int? = null
    ): MoviesDto

    @GET("search/multi")
    suspend fun searchMulti(
        @Query(ApiParams.QUERY) query: String,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
        @Query(ApiParams.INCLUDE_ADULT) adult: String? = null,
        @Query(ApiParams.REGION) region: String? = null
    ): SearchMultiDto
}