package com.github.af2905.movieland.data.api

import com.github.af2905.movieland.BuildConfig
import com.github.af2905.movieland.data.ApiParams.API_KEY
import com.github.af2905.movieland.data.ApiParams.LANGUAGE
import com.github.af2905.movieland.data.ApiParams.PAGE
import com.github.af2905.movieland.data.ApiParams.REGION
import com.github.af2905.movieland.data.entities.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query(API_KEY) apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API_KEY,
        @Query(LANGUAGE) language: String? = null,
        @Query(PAGE) page: Int? = null,
        @Query(REGION) region: String? = null
    ): MovieResponseDto

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query(API_KEY) apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API_KEY,
        @Query(LANGUAGE) language: String? = null,
        @Query(PAGE) page: Int? = null,
        @Query(REGION) region: String? = null
    ): MovieResponseDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query(API_KEY) apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API_KEY,
        @Query(LANGUAGE) language: String? = null,
        @Query(PAGE) page: Int? = null,
        @Query(REGION) region: String? = null
    ): MovieResponseDto

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query(API_KEY) apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API_KEY,
        @Query(LANGUAGE) language: String? = null,
        @Query(PAGE) page: Int? = null,
        @Query(REGION) region: String? = null
    ): MovieResponseDto

}