package com.github.af2905.movieland.core.repository

import androidx.paging.PagingData
import com.github.af2905.movieland.core.common.network.ResultWrapper
import com.github.af2905.movieland.core.data.database.entity.CreditsCast
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.database.entity.Video
import com.github.af2905.movieland.core.data.dto.movie.MovieExternalIds
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getCachedFirstMovies(
        movieType: MovieType,
        language: String?,
        page: Int?
    ): Flow<ResultWrapper<List<Movie>>>

    fun getMovies(
        movieType: MovieType,
        language: String?,
        page: Int?
    ): Flow<ResultWrapper<List<Movie>>>

    fun getMoviesPaginated(
        movieType: MovieType,
        language: String?
    ): Flow<PagingData<Movie>>

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

    fun getSimilarOrRecommendedPaginated(
        movieId: Int,
        movieType: MovieType,
        language: String?
    ): Flow<PagingData<Movie>>

    fun getMovieCredits(movieId: Int, language: String?): Flow<ResultWrapper<List<CreditsCast>>>

    fun getMovieVideos(movieId: Int, language: String?): Flow<ResultWrapper<List<Video>>>

    suspend fun getMovieExternalIds(movieId: Int, language: String?): ResultWrapper<MovieExternalIds?>
}