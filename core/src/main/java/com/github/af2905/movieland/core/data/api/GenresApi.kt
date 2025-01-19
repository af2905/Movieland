package com.github.af2905.movieland.core.data.api

import com.github.af2905.movieland.core.data.dto.genres.GenresResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresApi {

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("language") language: String? = null
    ): GenresResponseDto

    @GET("genre/tv/list")
    suspend fun getTvShowGenres(
        @Query("language") language: String? = null
    ): GenresResponseDto
}