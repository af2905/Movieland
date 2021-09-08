package com.github.af2905.movieland.domain.repository

import com.github.af2905.movieland.data.database.entity.MovieDetailsEntity
import com.github.af2905.movieland.data.database.entity.ResponseWithMovies
import com.github.af2905.movieland.data.dto.MovieActorsResponseDto
import com.github.af2905.movieland.data.dto.MoviesResponseDto

interface IMoviesRepository {
    suspend fun getNowPlayingMovies(language: String?, page: Int?, region: String?)
            : ResponseWithMovies

    suspend fun getPopularMovies(language: String?, page: Int?, region: String?)
            : ResponseWithMovies

    suspend fun getTopRatedMovies(language: String?, page: Int?, region: String?)
            : ResponseWithMovies

    suspend fun getUpcomingMovies(language: String?, page: Int?, region: String?)
            : ResponseWithMovies

    suspend fun getMovieDetails(movieId: Int, language: String?): MovieDetailsEntity

    suspend fun getRecommendedMovies(movieId: Int, language: String?, page: Int?)
            : ResponseWithMovies

    suspend fun getSimilarMovies(movieId: Int, language: String?, page: Int?)
            : MoviesResponseDto

    suspend fun getMovieActors(movieId: Int, language: String?) : MovieActorsResponseDto

}