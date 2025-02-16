package com.github.af2905.movieland.core.repository.impl

import com.github.af2905.movieland.core.data.api.TrendingApi
import com.github.af2905.movieland.core.data.database.dao.MovieDao
import com.github.af2905.movieland.core.data.database.dao.PersonDao
import com.github.af2905.movieland.core.data.database.dao.TvShowDao
import com.github.af2905.movieland.core.data.database.entity.Movie
import com.github.af2905.movieland.core.data.database.entity.MovieType
import com.github.af2905.movieland.core.data.database.entity.Person
import com.github.af2905.movieland.core.data.database.entity.PersonType
import com.github.af2905.movieland.core.data.database.entity.TvShow
import com.github.af2905.movieland.core.data.database.entity.TvShowType
import com.github.af2905.movieland.core.data.mapper.MovieMapper
import com.github.af2905.movieland.core.data.mapper.PersonMapper
import com.github.af2905.movieland.core.data.mapper.TvShowMapper
import com.github.af2905.movieland.core.repository.TrendingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TrendingRepositoryImpl @Inject constructor(
    private val trendingApi: TrendingApi,
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao,
    private val personDao: PersonDao,
    private val tvShowMapper: TvShowMapper,
    private val personMapper: PersonMapper,
    private val movieMapper: MovieMapper
) : TrendingRepository {

    override fun getTrendingMovies(
        movieType: MovieType,
        language: String?,
        page: Int?
    ): Flow<List<Movie>> = flow {
        val cachedItems = movieDao.getMoviesByType(movieType).firstOrNull()
        val lastUpdated = cachedItems?.firstOrNull()?.timeStamp ?: 0L
        val isCacheStale = System.currentTimeMillis() - lastUpdated > TimeUnit.HOURS.toMillis(8)

        if (cachedItems.isNullOrEmpty() || isCacheStale) {
            try {
                val timeWindow = when (movieType) {
                    MovieType.TRENDING_WEEK -> "week"
                    else -> "day"
                }

                val response =
                    trendingApi.getTrendingMovies(language = language, timeWindow = timeWindow)
                val items = movieMapper.map(response.movies)
                    .map {
                        it.copy(movieType = movieType, timeStamp = System.currentTimeMillis())
                    }
                    .filter { movie -> !movie.backdropPath.isNullOrEmpty() && !movie.posterPath.isNullOrEmpty() }
                if (items.isNotEmpty()) {
                    movieDao.deleteMoviesByType(movieType)
                    movieDao.insertMovies(items)
                }
            } catch (e: Exception) {
                // Handle API errors (e.g., log or fallback)
            }
        }
        emitAll(movieDao.getMoviesByType(movieType))
    }.catch {
        emit(emptyList())
    }

    override fun getTrendingTvShows(
        tvShowType: TvShowType,
        language: String?,
        page: Int?
    ): Flow<List<TvShow>> {
        return flow {
            val cachedItems = tvShowDao.getTvShowsByType(tvShowType).firstOrNull()
            val lastUpdated = cachedItems?.firstOrNull()?.timeStamp ?: 0L
            val isCacheStale = System.currentTimeMillis() - lastUpdated > TimeUnit.HOURS.toMillis(8)

            if (cachedItems.isNullOrEmpty() || isCacheStale) {
                try {
                    val timeWindow = when (tvShowType) {
                        TvShowType.TRENDING_WEEK -> "week"
                        else -> "day"
                    }

                    val response =
                        trendingApi.getTrendingTvShows(language = language, timeWindow = timeWindow)
                    val items = tvShowMapper.map(response.tvShows)
                        .map {
                            it.copy(tvShowType = tvShowType, timeStamp = System.currentTimeMillis())
                        }
                        .filter { tvShow -> !tvShow.backdropPath.isNullOrEmpty() && !tvShow.posterPath.isNullOrEmpty() }
                    if (items.isNotEmpty()) {
                        tvShowDao.deleteTvShowsByType(tvShowType)
                        tvShowDao.insertTvShows(items)
                    }
                } catch (e: Exception) {
                    // Handle API errors (e.g., log or fallback)
                }
            }
            emitAll(tvShowDao.getTvShowsByType(tvShowType))
        }.catch {
            emit(emptyList())
        }
    }

    override fun getTrendingPeople(
        personType: PersonType,
        language: String?,
        page: Int?
    ): Flow<List<Person>> {
        return flow {
            val cachedItems = personDao.getPeopleByType(personType).firstOrNull()
            val lastUpdated = cachedItems?.firstOrNull()?.timeStamp ?: 0L
            val isCacheStale = System.currentTimeMillis() - lastUpdated > TimeUnit.HOURS.toMillis(8)

            if (cachedItems.isNullOrEmpty() || isCacheStale) {
                try {
                    val timeWindow = when (personType) {
                        PersonType.TRENDING_WEEK -> "week"
                        else -> "day"
                    }
                    val response =
                        trendingApi.getTrendingPeople(language = language, timeWindow = timeWindow)
                    val items = personMapper.map(response.results)
                        .map {
                            it.copy(personType = personType, timeStamp = System.currentTimeMillis())
                        }
                        .filter { person -> !person.profilePath.isNullOrEmpty() }
                    if (items.isNotEmpty()) {
                        personDao.deletePeopleByType(personType)
                        personDao.insertPeople(items)
                    }
                } catch (e: Exception) {
                    // Handle API errors (e.g., log or fallback)
                }
            }
            emitAll(personDao.getPeopleByType(personType))
        }.catch {
            emit(emptyList())
        }
    }
}