package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.dto.movie.MovieCreditsCastDto
import com.github.af2905.movieland.core.data.dto.movie.MovieDetailDto
import com.github.af2905.movieland.core.data.dto.movie.MovieDto

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

    suspend fun getRecommendedMovies(movieId: Int, language: String?, page: Int?): List<MovieDto>

    suspend fun getSimilarMovies(movieId: Int, language: String?, page: Int?): List<MovieDto>

    suspend fun getMovieDetail(movieId: Int, language: String?): MovieDetailDto
    suspend fun getMovieCredits(movieId: Int, language: String?): List<MovieCreditsCastDto>

    suspend fun saveMovieDetail(movieDetail: MovieDetail): Boolean
    suspend fun removeMovieDetail(movieDetail: MovieDetail): Boolean
    suspend fun getMovieDetailById(id: Int): MovieDetail?
    suspend fun getAllSavedMovieDetail(): List<MovieDetail>

    suspend fun getCachedMoviesByType(type: MovieType): List<Movie>
}