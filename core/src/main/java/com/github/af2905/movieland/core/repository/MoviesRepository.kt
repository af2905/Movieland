package com.github.af2905.movieland.core.repository

import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.data.database.entity.CreditsCast
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.database.entity.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MoviesRepository {
    fun getMovies(
        movieType: MovieType,
        language: String?,
        page: Int?
    ): Flow<ResultWrapper<List<Movie>>>

    suspend fun getMovieDetails(
        movieId: Int,
        language: String?
    ): ResultWrapper<MovieDetail>

    fun getRecommendedMovies(
        movieId: Int,
        language: String?,
        page: Int?
    ): Flow<ResultWrapper<List<Movie>>>

    fun getSimilarMovies(
        movieId: Int,
        language: String?,
        page: Int?
    ): Flow<ResultWrapper<List<Movie>>>

    fun getMovieCredits(movieId: Int, language: String?): Flow<ResultWrapper<List<CreditsCast>>>

    fun getMovieVideos(movieId: Int, language: String?): Flow<ResultWrapper<List<Video>>>
}