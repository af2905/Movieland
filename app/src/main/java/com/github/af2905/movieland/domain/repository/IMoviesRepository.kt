package com.github.af2905.movieland.domain.repository

import com.github.af2905.movieland.data.dto.MoviesResponseDto

interface IMoviesRepository {
    suspend fun getNowPlayingMovies(
        language: String? = null, page: Int? = null, region: String? = null
    ): MoviesResponseDto

    suspend fun getPopularMovies(
        language: String? = null, page: Int? = null, region: String? = null
    ): MoviesResponseDto

    suspend fun getTopRatedMovies(
        language: String? = null, page: Int? = null, region: String? = null
    ): MoviesResponseDto

    suspend fun getUpcomingMovies(
        language: String? = null, page: Int? = null, region: String? = null
    ): MoviesResponseDto
}