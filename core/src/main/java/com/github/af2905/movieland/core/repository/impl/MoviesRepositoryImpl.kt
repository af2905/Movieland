package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.MoviesApi
import com.github.af2905.movieland.core.data.database.dao.MovieDao
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.mapper.MovieMapper
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
    private val mapper: MovieMapper
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
                    mapper.map(it).map { movie ->
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
}