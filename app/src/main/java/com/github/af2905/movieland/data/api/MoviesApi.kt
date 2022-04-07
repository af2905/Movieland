package com.github.af2905.movieland.data.api

import com.github.af2905.movieland.data.ApiParams.LANGUAGE
import com.github.af2905.movieland.data.ApiParams.MOVIE_ID
import com.github.af2905.movieland.data.ApiParams.PAGE
import com.github.af2905.movieland.data.ApiParams.REGION
import com.github.af2905.movieland.data.dto.movie.MovieActorsResponseDto
import com.github.af2905.movieland.data.dto.movie.MovieDetailsDto
import com.github.af2905.movieland.data.dto.movie.MoviesResponseDto

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query(LANGUAGE) language: String? = null,
        @Query(PAGE) page: Int? = null,
        @Query(REGION) region: String? = null
    ): MoviesResponseDto

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query(LANGUAGE) language: String? = null,
        @Query(PAGE) page: Int? = null,
        @Query(REGION) region: String? = null
    ): MoviesResponseDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query(LANGUAGE) language: String? = null,
        @Query(PAGE) page: Int? = null,
        @Query(REGION) region: String? = null
    ): MoviesResponseDto

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query(LANGUAGE) language: String? = null,
        @Query(PAGE) page: Int? = null,
        @Query(REGION) region: String? = null
    ): MoviesResponseDto

    @GET("movie/{$MOVIE_ID}")
    suspend fun getMovieDetails(
        @Path(MOVIE_ID) movieId: Int,
        @Query(LANGUAGE) language: String? = null
    ): MovieDetailsDto

    @GET("movie/{$MOVIE_ID}/recommendations")
    suspend fun getRecommendedMovies(
        @Path(MOVIE_ID) movieId: Int,
        @Query(LANGUAGE) language: String? = null,
        @Query(PAGE) page: Int? = null
    ): MoviesResponseDto

    @GET("movie/{$MOVIE_ID}/similar")
    suspend fun getSimilarMovies(
        @Path(MOVIE_ID) movieId: Int,
        @Query(LANGUAGE) language: String? = null,
        @Query(PAGE) page: Int? = null
    ): MoviesResponseDto

    @GET("movie/{$MOVIE_ID}/credits")
    suspend fun getMovieActors(
        @Path(MOVIE_ID) movieId: Int,
        @Query(LANGUAGE) language: String? = null
    ): MovieActorsResponseDto
}