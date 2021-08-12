package com.github.af2905.movieland.domain.repository

import com.github.af2905.movieland.data.dto.MovieDetailsDto
import com.github.af2905.movieland.presentation.model.item.MoviesResponse

interface IMoviesRepository {
    suspend fun getNowPlayingMovies(
        language: String?, page: Int?, region: String?
    ): MoviesResponse

    suspend fun getPopularMovies(language: String?, page: Int?, region: String?): MoviesResponse

    suspend fun getTopRatedMovies(language: String?, page: Int?, region: String?): MoviesResponse

    suspend fun getUpcomingMovies(language: String?, page: Int?, region: String?): MoviesResponse

    suspend fun getMovieDetails(movieId: Int, language: String?): MovieDetailsDto

    suspend fun getRecommendedMovies(movieId: Int, language: String?, page: Int?): MoviesResponse

    suspend fun getSimilarMovies(movieId: Int, language: String?, page: Int?): MoviesResponse

}