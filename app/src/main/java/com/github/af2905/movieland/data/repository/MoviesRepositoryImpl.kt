package com.github.af2905.movieland.data.repository

import com.github.af2905.movieland.data.api.MoviesApi
import com.github.af2905.movieland.data.database.dao.MovieDao
import com.github.af2905.movieland.data.database.entity.MovieDetailsEntity
import com.github.af2905.movieland.data.database.entity.MovieEntity
import com.github.af2905.movieland.data.database.entity.MovieType
import com.github.af2905.movieland.data.datastore.ResourceDatastore
import com.github.af2905.movieland.data.mapper.MovieDetailsDtoToEntityMapper
import com.github.af2905.movieland.data.mapper.MovieDtoToEntityListMapper
import com.github.af2905.movieland.domain.repository.MoviesRepository
import com.github.af2905.movieland.helper.extension.isNullOrEmpty
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val DEFAULT_UPDATE_MOVIE_HOURS = 24L

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDtoMapper: MovieDtoToEntityListMapper,
    private val movieDetailsDtoMapper: MovieDetailsDtoToEntityMapper,
    private val movieDao: MovieDao,
    private val resourceDatastore: ResourceDatastore
) : MoviesRepository {

    override suspend fun getNowPlayingMovies(
        language: String?, page: Int?, region: String?, forceUpdate: Boolean
    ): List<MovieEntity> =
        loadMovies(
            type = MovieType.NOW_PLAYING.name,
            language = language ?: resourceDatastore.getLanguage(),
            page = page,
            region = region,
            forceUpdate = forceUpdate
        )

    override suspend fun getPopularMovies(
        language: String?, page: Int?, region: String?, forceUpdate: Boolean
    ): List<MovieEntity> =
        loadMovies(
            MovieType.POPULAR.name,
            language = language ?: resourceDatastore.getLanguage(),
            page = page,
            region = region,
            forceUpdate = forceUpdate
        )

    override suspend fun getTopRatedMovies(
        language: String?, page: Int?, region: String?, forceUpdate: Boolean
    ): List<MovieEntity> =
        loadMovies(
            MovieType.TOP_RATED.name,
            language = language ?: resourceDatastore.getLanguage(),
            page = page,
            region = region,
            forceUpdate = forceUpdate
        )

    override suspend fun getUpcomingMovies(
        language: String?, page: Int?, region: String?, forceUpdate: Boolean
    ): List<MovieEntity> =
        loadMovies(
            MovieType.UPCOMING.name,
            language = language ?: resourceDatastore.getLanguage(),
            page = page,
            region = region,
            forceUpdate = forceUpdate
        )

    override suspend fun getRecommendedMovies(
        movieId: Int, language: String?, page: Int?
    ): List<MovieEntity> {
        val response = moviesApi.getRecommendedMovies(
            movieId = movieId,
            language = language ?: resourceDatastore.getLanguage(),
            page = page
        )
        return movieDtoMapper.map(response.movies, MovieType.RECOMMENDED.name, 0)
    }

    override suspend fun getSimilarMovies(
        movieId: Int, language: String?, page: Int?
    ): List<MovieEntity> {
        val response = moviesApi.getSimilarMovies(
            movieId = movieId,
            language = language ?: resourceDatastore.getLanguage(),
            page = page
        )
        return movieDtoMapper.map(response.movies, MovieType.SIMILAR.name, 0)
    }

    override suspend fun getMovieActors(movieId: Int, language: String?) =
        moviesApi.getMovieActors(
            movieId = movieId,
            language = language ?: resourceDatastore.getLanguage()
        )

    override suspend fun getMovieDetails(movieId: Int, language: String?): MovieDetailsEntity =
        movieDetailsDtoMapper.map(
            moviesApi.getMovieDetails(
                movieId = movieId,
                language = language ?: resourceDatastore.getLanguage()
            )
        )

    private suspend fun loadMovies(
        type: String,
        language: String?,
        page: Int?,
        region: String? = null,
        movieId: Int? = null,
        forceUpdate: Boolean
    ): List<MovieEntity> {

        val count = movieDao.getCountByType(type)

        val timeStamp = count?.let { movieDao.getTimeStampByType(type) }

        val currentTime = Calendar.getInstance().timeInMillis

        val timeDiff = timeStamp?.let {
            periodOfTimeInHours(
                timeStamp = it,
                currentTime = currentTime
            )
        }

        val needToUpdate = timeDiff?.let {
            it > TimeUnit.HOURS.toMillis(DEFAULT_UPDATE_MOVIE_HOURS)
        }

        if (count.isNullOrEmpty() || needToUpdate == true || forceUpdate) {
            val dto = when (type) {
                MovieType.NOW_PLAYING.name -> moviesApi.getNowPlayingMovies(language, page, region)
                MovieType.POPULAR.name -> moviesApi.getPopularMovies(language, page, region)
                MovieType.TOP_RATED.name -> moviesApi.getTopRatedMovies(language, page, region)
                MovieType.UPCOMING.name -> moviesApi.getUpcomingMovies(language, page, region)
                MovieType.RECOMMENDED.name -> {
                    moviesApi.getRecommendedMovies(movieId!!, language, page)
                }
                else -> moviesApi.getSimilarMovies(movieId!!, language, page)
            }
            movieDtoMapper.map(dto.movies, type, currentTime).map { movieDao.save(it) }
        }
        return movieDao.getByType(type).orEmpty()
    }

    private fun periodOfTimeInHours(timeStamp: Long, currentTime: Long) =
        TimeUnit.MILLISECONDS.toHours(currentTime - timeStamp)
}