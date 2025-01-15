package com.github.af2905.movieland.core.data.api

import com.github.af2905.movieland.core.data.dto.movie.MoviesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrendingApi {

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String,
        @Query("language") language: String? = null
    ): MoviesDto
}