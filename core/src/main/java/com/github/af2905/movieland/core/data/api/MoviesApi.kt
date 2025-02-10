package com.github.af2905.movieland.core.data.api

import com.github.af2905.movieland.core.data.dto.credits.CreditsDto
import com.github.af2905.movieland.core.data.dto.movie.MovieDetailDto
import com.github.af2905.movieland.core.data.dto.movie.MovieExternalIds
import com.github.af2905.movieland.core.data.dto.movie.MovieVideosDto
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

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = null
    ): MovieDetailDto

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendedMovies(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): MoviesDto

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): MoviesDto

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = null
    ): CreditsDto

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = null
    ): MovieVideosDto

    @GET("movie/{movie_id}/external_ids")
    suspend fun getMovieExternalIds(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = null
    ): MovieExternalIds
}