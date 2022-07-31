package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.MovieDetailsEntity
import com.github.af2905.movieland.core.data.database.entity.MovieEntity
import com.github.af2905.movieland.core.data.dto.movie.MovieActorsResponseDto

interface MoviesRepository {
    suspend fun getNowPlayingMovies(
        language: String?,
        page: Int?,
        region: String?,
        forceUpdate: Boolean
    ): List<MovieEntity>

    suspend fun getPopularMovies(
        language: String?,
        page: Int?,
        region: String?,
        forceUpdate: Boolean
    ): List<MovieEntity>

    suspend fun getTopRatedMovies(
        language: String?,
        page: Int?,
        region: String?,
        forceUpdate: Boolean
    ): List<MovieEntity>

    suspend fun getUpcomingMovies(
        language: String?,
        page: Int?,
        region: String?,
        forceUpdate: Boolean
    ): List<MovieEntity>

    suspend fun getRecommendedMovies(movieId: Int, language: String?, page: Int?): List<MovieEntity>

    suspend fun getSimilarMovies(movieId: Int, language: String?, page: Int?): List<MovieEntity>

    suspend fun getMovieDetails(movieId: Int, language: String?): MovieDetailsEntity
    suspend fun getMovieActors(movieId: Int, language: String?): MovieActorsResponseDto
}