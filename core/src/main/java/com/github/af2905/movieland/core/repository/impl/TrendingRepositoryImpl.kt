package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.TrendingApi
import com.github.af2905.movieland.core.data.database.dao.MovieDao
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.mapper.MovieMapper
import com.github.af2905.movieland.core.repository.TrendingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrendingRepositoryImpl @Inject constructor(
    private val trendingApi: TrendingApi,
    private val movieDao: MovieDao,
    private val mapper: MovieMapper
) : TrendingRepository {

    companion object {
        private const val CACHE_VALIDITY_PERIOD = 4 * 60 * 60 * 1000 // 4 hours in milliseconds
    }

    override fun getTrendingMovies(timeWindow: String, language: String?): Flow<List<Movie>> = flow {
        val movieType = "TRENDING_$timeWindow"

        // Check cached movies
        val cachedMovies = movieDao.getMoviesByType(movieType).firstOrNull()
        val lastUpdated = cachedMovies?.firstOrNull()?.timeStamp ?: 0L
        val isCacheStale = System.currentTimeMillis() - lastUpdated > CACHE_VALIDITY_PERIOD

        // Fetch fresh data if cache is stale or not available
        if (cachedMovies.isNullOrEmpty() || isCacheStale) {
            try {
                val response = trendingApi.getTrendingMovies(language = language, timeWindow = timeWindow)
                val movies = mapper.map(response.movies).map {
                    it.copy(movieType = movieType, timeStamp = System.currentTimeMillis())
                }

                if (movies.isNotEmpty()) {
                    movieDao.deleteMoviesByType(movieType)
                    movieDao.insertMovies(movies)
                }
            } catch (e: Exception) {
                emit(emptyList()) // Emit empty list if API fails and no cache is available
                return@flow
            }
        }

        // Emit cached movies (updated or existing)
        emitAll(movieDao.getMoviesByType(movieType))
    }.catch {
        emit(emptyList()) // Fallback to empty list in case of any errors
    }
}