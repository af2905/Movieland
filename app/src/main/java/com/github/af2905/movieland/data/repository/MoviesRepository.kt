package com.github.af2905.movieland.data.repository

import com.github.af2905.movieland.data.api.MoviesApi
import com.github.af2905.movieland.domain.repository.IMoviesRepository
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi
) : IMoviesRepository {

    override suspend fun getNowPlayingMovies(
        language: String?, page: Int?, region: String?
    ) = moviesApi.getNowPlayingMovies(language = language, page = page, region = region)

    override suspend fun getPopularMovies(
        language: String?, page: Int?, region: String?
    ) = moviesApi.getPopularMovies(language = language, page = page, region = region)

    override suspend fun getTopRatedMovies(
        language: String?, page: Int?, region: String?
    ) = moviesApi.getTopRatedMovies(language = language, page = page, region = region)

    override suspend fun getUpcomingMovies(
        language: String?, page: Int?, region: String?
    ) = moviesApi.getUpcomingMovies(language = language, page = page, region = region)

    override suspend fun getMovieDetails(movieId: Int, language: String?) =
        moviesApi.getMovieDetails(movieId = movieId, language = language)
}