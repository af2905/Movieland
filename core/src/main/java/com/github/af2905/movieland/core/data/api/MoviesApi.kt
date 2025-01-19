package com.github.af2905.movieland.core.data.api

import com.github.af2905.movieland.core.data.ApiParams.LANGUAGE
import com.github.af2905.movieland.core.data.ApiParams.MOVIE_ID
import com.github.af2905.movieland.core.data.ApiParams.PAGE
import com.github.af2905.movieland.core.data.dto.CreditsDto
import com.github.af2905.movieland.core.data.dto.movie.MovieDetailDto
import com.github.af2905.movieland.core.data.dto.movie.MoviesDto

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
    ): MoviesDto

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
    ): MoviesDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
    ): MoviesDto

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
    ): MoviesDto

    @GET("movie/{$MOVIE_ID}")
    suspend fun getMovieDetails(
        @Path(MOVIE_ID) movieId: Int,
        @Query("language") language: String? = null
    ): MovieDetailDto

    @GET("movie/{$MOVIE_ID}/recommendations")
    suspend fun getRecommendedMovies(
        @Path(MOVIE_ID) movieId: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): MoviesDto

    @GET("movie/{$MOVIE_ID}/similar")
    suspend fun getSimilarMovies(
        @Path(MOVIE_ID) movieId: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): MoviesDto

    @GET("movie/{$MOVIE_ID}/credits")
    suspend fun getMovieCredits(
        @Path(MOVIE_ID) movieId: Int,
        @Query("language") language: String? = null
    ): CreditsDto
}