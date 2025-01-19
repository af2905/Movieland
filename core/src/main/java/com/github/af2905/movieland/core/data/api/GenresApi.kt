package com.github.af2905.movieland.core.data.api

import com.github.af2905.movieland.core.data.dto.GenresResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresApi {

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("language") language: String? = null
    ): GenresResponseDto
}