package com.github.af2905.movieland.data.api

import com.github.af2905.movieland.data.ApiParams
import com.github.af2905.movieland.data.dto.MoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/movie")
    suspend fun searchMovie(
        @Query(ApiParams.LANGUAGE) language: String? = null,
        @Query(ApiParams.QUERY) query: String,
        @Query(ApiParams.PAGE) page: Int? = null,
        @Query(ApiParams.INCLUDE_ADULT) adult: String? = null,
        @Query(ApiParams.REGION) region: String? = null,
        @Query(ApiParams.YEAR) year: Int? = null
    ): MoviesResponseDto
}