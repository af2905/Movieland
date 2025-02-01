package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.MoviesApi
import com.github.af2905.movieland.core.data.database.dao.MovieDao
import com.github.af2905.movieland.core.data.database.entity.CreditsCast
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.database.entity.Video
import com.github.af2905.movieland.core.data.mapper.CreditsCastMapper
import com.github.af2905.movieland.core.data.mapper.MovieDetailMapper
import com.github.af2905.movieland.core.data.mapper.MovieMapper
import com.github.af2905.movieland.core.data.mapper.VideoMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val moviesApi: MoviesApi,
    private val movieMapper: MovieMapper,
    private val creditsMapper: CreditsCastMapper,
    private val movieDetailMapper: MovieDetailMapper,
    private val videoMapper: VideoMapper
) : MoviesRepository {

    override fun getMovies(
        movieType: MovieType,
        language: String?,
        page: Int?
    ): Flow<List<Movie>> = flow {
        val cachedMovies = movieDao.getMoviesByType(movieType).firstOrNull()
        val lastUpdated = cachedMovies?.firstOrNull()?.timeStamp ?: 0L
        val isCacheStale = System.currentTimeMillis() - lastUpdated > TimeUnit.HOURS.toMillis(8)

        if (cachedMovies.isNullOrEmpty() || isCacheStale) {
            try {
                val response = when (movieType) {
                    MovieType.NOW_PLAYING -> moviesApi.getNowPlayingMovies(
                        language = language,
                        page = page
                    )

                    MovieType.POPULAR -> moviesApi.getPopularMovies(
                        language = language,
                        page = page
                    )

                    MovieType.TOP_RATED -> moviesApi.getTopRatedMovies(
                        language = language,
                        page = page
                    )

                    MovieType.UPCOMING -> moviesApi.getUpcomingMovies(
                        language = language,
                        page = page
                    )

                    else -> null
                }

                val movies = response?.movies?.let {
                    movieMapper.map(it).map { movie ->
                        movie.copy(movieType = movieType, timeStamp = System.currentTimeMillis())
                    }
                }

                if (movies != null) {
                    movieDao.deleteMoviesByType(movieType)
                    movieDao.insertMovies(movies)
                }
            } catch (e: Exception) {
                // Handle API errors (e.g., log or fallback)
            }
        }
        emitAll(movieDao.getMoviesByType(movieType))
    }.catch { emit(emptyList()) }

    override suspend fun getMovieDetails(
        movieId: Int,
        language: String?
    ): MovieDetail {
        return try {
            val movieDetailDto = moviesApi.getMovieDetails(movieId, language)
            movieDetailMapper.map(movieDetailDto)
        } catch (e: Exception) {
            // Handle errors, e.g., log them or rethrow as a custom exception
            throw RuntimeException("Failed to fetch movie details: ${e.message}", e)
        }
    }

    override fun getRecommendedMovies(
        movieId: Int,
        language: String?,
        page: Int?
    ): Flow<List<Movie>> = flow {
        try {
            val response = moviesApi.getRecommendedMovies(movieId, language, page)
            val movies = response.movies.let { movieMapper.map(it) }
            emit(movies)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override fun getSimilarMovies(
        movieId: Int,
        language: String?,
        page: Int?
    ): Flow<List<Movie>> = flow {
        try {
            val response = moviesApi.getSimilarMovies(movieId, language, page)
            val movies = response.movies.let { movieMapper.map(it) }
            emit(movies)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override fun getMovieCredits(movieId: Int, language: String?): Flow<List<CreditsCast>> = flow {
        try {
            val response = moviesApi.getMovieCredits(movieId, language)
            val cast = response.cast?.map {
                creditsMapper.map(it, movieId)
            }
            emit(cast ?: emptyList())
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override fun getMovieVideos(movieId: Int, language: String?): Flow<List<Video>> = flow {
        try {
            val response = moviesApi.getMovieVideos(movieId, language)
            val videos = response.results.map { videoMapper.map(it) }
            emit(videos)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}