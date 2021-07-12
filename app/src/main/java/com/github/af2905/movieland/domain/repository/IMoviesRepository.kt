package com.github.af2905.movieland.domain.repository

import com.github.af2905.movieland.data.dto.MovieDetailsDto
import com.github.af2905.movieland.data.dto.MoviesResponseDto

interface IMoviesRepository {
    suspend fun getNowPlayingMovies(
        language: String?, page: Int?, region: String?
    ): MoviesResponseDto

    suspend fun getPopularMovies(language: String?, page: Int?, region: String?): MoviesResponseDto

    suspend fun getTopRatedMovies(language: String?, page: Int?, region: String?): MoviesResponseDto

    suspend fun getUpcomingMovies(language: String?, page: Int?, region: String?): MoviesResponseDto

    suspend fun getMovieDetails(movieId: Int, language: String?): MovieDetailsDto
}