package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.MoviesApi
import com.github.af2905.movieland.core.data.database.dao.MovieDao
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.mapper.MovieMapper
import com.github.af2905.movieland.core.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val moviesApi: MoviesApi,
    private val mapper: MovieMapper
) : MoviesRepository {

    override fun getMovies(
        movieType: String,
        language: String?,
        page: Int?
    ): Flow<List<Movie>> = flow {
        // Retrieve cached movies
        val cachedMovies = movieDao.getMoviesByType(movieType).firstOrNull()
        val lastUpdated = cachedMovies?.firstOrNull()?.timeStamp ?: 0L
        val isCacheStale = System.currentTimeMillis() - lastUpdated > 4 * 60 * 60 * 1000 // 4 hours

        // Fetch fresh data if cache is stale
        if (cachedMovies.isNullOrEmpty() || isCacheStale) {
            try {
                val response = when (movieType) {
                    "NOW_PLAYING" -> moviesApi.getNowPlayingMovies(language = language, page = page)
                    "POPULAR" -> moviesApi.getPopularMovies(language = language, page = page)
                    "TOP_RATED" -> moviesApi.getTopRatedMovies(language = language, page = page)
                    "UPCOMING" -> moviesApi.getUpcomingMovies(language = language, page = page)
                    else -> null
                }

                val movies = response?.movies?.let {
                    mapper.map(it).map { movie ->
                        movie.copy(movieType = movieType, timeStamp = System.currentTimeMillis())
                    }
                }

                if (!movies.isNullOrEmpty()) {
                    movieDao.deleteMoviesByType(movieType)
                    movieDao.insertMovies(movies)
                }
            } catch (e: Exception) {
                // Handle API errors (e.g., log or fallback)
            }
        }

        // Emit cached movies, including any updates from the API
        emitAll(movieDao.getMoviesByType(movieType))
    }.catch { emit(emptyList()) } // Fallback to empty list on errors
}