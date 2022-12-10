package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.core.data.database.entity.plain.MovieActor

interface MoviesRepository {
    suspend fun getNowPlayingMovies(
        language: String?,
        page: Int?,
        region: String?,
        forceUpdate: Boolean
    ): List<Movie>

    suspend fun getPopularMovies(
        language: String?,
        page: Int?,
        region: String?,
        forceUpdate: Boolean
    ): List<Movie>

    suspend fun getTopRatedMovies(
        language: String?,
        page: Int?,
        region: String?,
        forceUpdate: Boolean
    ): List<Movie>

    suspend fun getUpcomingMovies(
        language: String?,
        page: Int?,
        region: String?,
        forceUpdate: Boolean
    ): List<Movie>

    suspend fun getRecommendedMovies(movieId: Int, language: String?, page: Int?): List<Movie>

    suspend fun getSimilarMovies(movieId: Int, language: String?, page: Int?): List<Movie>

    suspend fun getMovieDetail(movieId: Int, language: String?): MovieDetail
    suspend fun getMovieActors(movieId: Int, language: String?): List<MovieActor>
}